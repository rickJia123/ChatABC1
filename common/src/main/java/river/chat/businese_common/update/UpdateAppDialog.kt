package river.chat.businese_common.update


import android.app.Activity
import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import river.chat.businese_common.utils.onLoad
import river.chat.common.R
import river.chat.common.databinding.DialogUpdateBinding
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.utils.exts.dp2px
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.width
import river.chat.lib_core.utils.longan.download
import river.chat.lib_core.utils.longan.installAPK
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.utils.permission.permission.PermissionConstants
import river.chat.lib_core.utils.permission.permission.PermissionHelper
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment

/**
 * Created by beiyongChao on 2023/7/22
 * Description:
 */
class UpdateAppDialog(var dialogActivity: AppCompatActivity) :
    BaseBindingDialogFragment<DialogUpdateBinding>() {

    private var mIsForce = false
    private var apkLink = ""
//        "https://cdn-ali.dushu365.com/app/v5.65.1/fandengdushuhui_5.65.1_release.apk"

    private var mProgressWidth = 0

    /**
     * 0未开始 1下载中 2已下完
     */
    private var downloadStatus = 0

    /**
     * 下载完成的apk 存储uri
     */
    private var completeUri: Uri? = null
    private var mProgress = 0f

    companion object {
        //0未开始 1下载中 2已下完
        const val STATUS_READY = 0
        const val STATUS_DOWNLOADING = 1
        const val STATUS_COMPLETE = 2

        @JvmStatic
        fun builder(activity: AppCompatActivity): UpdateAppDialog = UpdateAppDialog(activity)
    }


    override fun initDataBinding(binding: DialogUpdateBinding) {
        onLoad()
        apkLink = ServiceConfigBox.getConfig().appDownLink
        dialog?.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
                closeDialog()
                return@setOnKeyListener true
            }
            return@setOnKeyListener true
        }

        binding.viewRoot.post {
            mProgressWidth = binding.viewProgress.width
        }
//        apkLink = AppUpdateManager.getUpdateUrl()


        binding.tvDes.text = AppUpdateManager.getUpdateContent()
        binding.tvLButton.singleClick {
            closeDialog()
        }
        changeBtStatus(binding, STATUS_READY)
        binding.tvRButton.singleClick {

            when (downloadStatus) {
                STATUS_READY -> {
                    download(apkLink) {
                        this.description = "正在更新"
                        this.notificationVisibility =
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                        this.requiresCharging = true
                        this.title = "正在更新《GPTEvery》到最新版本"
                        this.allowedNetworkTypes =
                            DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
                        this.onChange { downloadedSize, totalSize, status ->
                            var progress = downloadedSize / totalSize.toFloat()
                            ("进度$downloadedSize" + ":::" + totalSize + "::::" + progress).log()
                            if (progress < 1) {
                                mProgress = progress
                                changeBtStatus(binding, STATUS_DOWNLOADING)
                            } else {
                                changeBtStatus(binding, STATUS_COMPLETE)
                            }

                        }
                        this.onComplete {
                            ("进度 下载完成").log()
                            mProgress = 1f
                            completeUri = it
                            changeBtStatus(binding, STATUS_COMPLETE)
                        }
                    }
                }

                STATUS_DOWNLOADING -> {
                    "正在下载中".toastSystem()
                }

                STATUS_COMPLETE -> {
                    beginInstall()
                }
            }
        }
        binding.viewRoot.singleClick {
            closeDialog()
        }
    }

    private fun changeBtStatus(binding: DialogUpdateBinding, status: Int) {
        downloadStatus = status
        when (status) {
            STATUS_READY -> {
                binding.tvRButton.isEnabled = true
                binding.tvRButton.setTextColor(R.color.highButtonColor.getColor())
                binding.tvRButton.text = "开始更新"
            }

            STATUS_DOWNLOADING -> {
                binding.tvRButton.isEnabled = false
                binding.tvRButton.setTextColor(R.color.themeYellowColor.getColor())
                binding.tvRButton.text = "下载中 " + (mProgress * 100).toInt() + "%"
                binding.viewProgressMask.width(((1 - mProgress) * mProgressWidth).toInt())
            }

            STATUS_COMPLETE -> {
                mProgress = 1f
                binding.tvRButton.setTextColor(R.color.highButtonColor.getColor())
                binding.tvRButton.text = "安装"
                binding.tvRButton.isEnabled = true
                binding.viewProgressMask.visibility = View.GONE
                completeUri?.let {

                    var packageURI = Uri.parse("package:" + context?.packageName)
//                    var intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
//                    startActivityForResult(intent, 0)

                    installAPK(packageURI)
                }
            }
        }
    }


    /**
     * 初始化
     */
    fun config(isForce: Boolean): UpdateAppDialog {
        mIsForce = isForce
        return this
    }

    private fun closeDialog() {
        if (mIsForce) {
            "本次为重要更新，需要先更新此次版本才能进入哦".toastSystem()
        } else {
//            if (downloadStatus == STATUS_READY) {
            dismissAllowingStateLoss()
//            }
        }
    }


    fun show() {
        dialogActivity.supportFragmentManager.let {
            ConfigManager.putAppConfig(AppLocalConfigKey.UPDATE_DIALOG_SHOW_TIME, true)
            show(it, "DialogUpdateBinding")
        }
    }

    fun beginInstall() {
        completeUri?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val pm = context?.getPackageManager()
                //pm.canRequestPackageInstalls() 返回用户是否授予了安装apk的权限
                if (pm?.canRequestPackageInstalls() == true) {
                    installAPK(it)
                } else {
                    PermissionHelper.jump2Setting(
                        context as Activity,
                        PermissionConstants.PERMISSION_INSTALL_APP,
                        it
                    )
                }
            }

        }
    }

}
