package river.chat.businese_common.update


import android.app.DownloadManager
import android.net.Uri
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import river.chat.businese_common.ui.view.dialog.SimpleDialog
import river.chat.businese_common.ui.view.dialog.SimpleDialogConfig
import river.chat.common.R
import river.chat.common.databinding.DialogUpdateBinding
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.width
import river.chat.lib_core.utils.longan.download
import river.chat.lib_core.utils.longan.installAPK
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment

/**
 * Created by beiyongChao on 2023/7/22
 * Description:
 */
class UpdateAppDialog(var dialogActivity: AppCompatActivity) :
    BaseBindingDialogFragment<DialogUpdateBinding>() {

    private var mIsForce = false
    private var apkLink =
        "https://cdn-ali.dushu365.com/app/v5.65.1/fandengdushuhui_5.65.1_release.apk"

    private var mProgressWidth = 0

    /**
     * 0未开始 1下载中 2已下完
     */
    private var downloadStatus = 0

    /**
     * 下载完成的apk 存储uri
     */
    private var completeUri: Uri? = null

    companion object {
        @JvmStatic
        fun builder(activity: AppCompatActivity): UpdateAppDialog = UpdateAppDialog(activity)
    }


    override fun initDataBinding(binding: DialogUpdateBinding) {
        binding.viewProgress.post {
            mProgressWidth = binding.viewProgress.width
        }
        binding.tvTips.movementMethod = LinkMovementMethod.getInstance() // 设置了才能点击

        binding.tvLButton.singleClick {

            closeDialog()
        }
        binding.tvRButton.singleClick {
            download(apkLink) {
                this.description = "下载测试哦"
                this.notificationVisibility =
                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                this.requiresCharging = true
                this.title = "正在更新《GPTEvery》到最新版本"
                this.onChange { downloadedSize, totalSize, status ->
                    downloadStatus = 1
                    var progress = downloadedSize / totalSize.toFloat()
                    ("进度" + progress).log()
                    binding.tvRButton.text = "下载中..." + (progress * 100).toInt() + "%"
                    binding.viewProgress.width((progress * mProgressWidth).toInt())
                }
                this.onComplete {
                    binding.tvRButton.setTextColor(R.color.highButtonColor.getColor())
                    binding.tvRButton.text = "安装"
                    binding.tvRButton.isEnabled = true
                    downloadStatus = 2
                    completeUri = it
                }
            }
            when (downloadStatus) {
                0 -> {
                    binding.tvRButton.isEnabled = false
                    binding.tvRButton.text = "下载中..."
                    binding.tvRButton.setTextColor(R.color.defaultTextColor.getColor())
                }

                2 -> {
                    completeUri?.let {
                        installAPK(it)
                    }
                }

            }
        }
        binding.viewRoot.singleClick {
            if (mIsForce) {
                "需要先更新此次重要版本哦".toast()
            } else {
                closeDialog()
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
        dismiss()
    }

    fun show() {
        dialogActivity.supportFragmentManager?.let { show(it, "DialogUpdateBinding") }
    }

}
