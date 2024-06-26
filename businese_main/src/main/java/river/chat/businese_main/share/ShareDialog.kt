package river.chat.businese_main.share


import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import coil.load
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.report.ShareTracker
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.utils.exts.loadAvatar
import river.chat.business_main.databinding.DialogShareBinding
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.share.SharePlatformBean
import river.chat.lib_core.utils.common.QRCodeUtils
import river.chat.lib_core.utils.exts.ifEmptyOrBlank
import river.chat.lib_core.utils.exts.view.captureView
import river.chat.lib_core.utils.longan.copyToClipboard
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment
import river.chat.lib_resource.model.database.MessageBean
import river.chat.lib_umeng.ShareManager
import river.chat.lib_umeng.common.RIVER_SHARE_MEDIA
import river.chat.lib_umeng.common.RiverShareContent

/**
 * Created by beiyongChao on 2023/6/9
 * Description:
 */
class ShareDialog(var dialogActivity: AppCompatActivity) :
    BaseBindingDialogFragment<DialogShareBinding>() {

    private var questionMsg: MessageBean? = null
    private var answerMsg: MessageBean? = null
    private var mBinding: DialogShareBinding? = null

    //测试地址
//    private var mTestUrl = "https://www.baidu.com/"

    companion object {
        @JvmStatic
        fun builder(activity: AppCompatActivity): ShareDialog = ShareDialog(activity)
    }

    override fun onStart() {
        super.onStart()

        ReportManager.reportEvent(
            TrackerEventName.LOAD_SHARE,
            mutableMapOf(
                ShareTracker.KEY_QUESTION to (questionMsg?.content
                    ?: ""), ShareTracker.KEY_ANSWER to ((answerMsg?.content
                    ?: "")
                        )
            )
        )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun initDataBinding(binding: DialogShareBinding) {

        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        mBinding = binding
        binding.includeContent.tvQuestion.text = questionMsg?.content ?: ""
        answerMsg?.content?.let { text ->
            binding.includeContent.tvAnswer.text = text
            if (text.length > 200) {
                binding.includeContent.tvAnswer.textSize = 12f
            } else if (text.length > 400) {
                binding.includeContent.tvAnswer.textSize = 10f
            } else if (text.length > 1000) {
                binding.includeContent.tvAnswer.textSize = 8f
            } else {
                binding.includeContent.tvAnswer.textSize = 16f
            }
        }

        binding.viewPlatform.mOnPlatformClick = {
            ReportManager.reportEvent(
                TrackerEventName.CLICK_SHARE,
                mutableMapOf(
                    ShareTracker.KEY_PLATFORM to (it.platform?.name ?: ""),
                    ShareTracker.KEY_QUESTION to (questionMsg?.content
                        ?: ""),
                    ShareTracker.KEY_ANSWER to ((answerMsg?.content
                        ?: ""))
                )
            )
            if (it.platform == RIVER_SHARE_MEDIA.MORE) {
                (answerMsg?.content ?: "").copyToClipboard()
                "回答已复制".toastSystem()
            } else {
                share(it)
            }
        }
        binding.viewPlatform.mOnCancelClick = {
            ReportManager.reportEvent(
                TrackerEventName.CLICK_SHARE,
                mutableMapOf(
                    ShareTracker.KEY_PLATFORM to ("取消"))
                )

            closeDialog()
        }
        binding.includeContent.ivCode.setImageBitmap(QRCodeUtils.createQRCode(ServiceConfigBox.getConfig().appDownUrl))
        binding.includeContent.ivAvatar.loadAvatar(user.headImg)
        binding.includeContent.tvName.text = user.nickName.ifEmptyOrBlank("GptEvery用户")
        binding.includeContent.ivBg.load(ServiceConfigBox.getConfig().appShareBg ?: "")

        updateView()
    }


    private fun closeDialog() {
        dismiss()
    }

    fun show(questionMsg: MessageBean? = null, answerMsg: MessageBean? = null) {
        this.questionMsg = questionMsg
        this.answerMsg = answerMsg
        dialogActivity.supportFragmentManager.let {
            show(it, "ShareDialog")
        }
    }


    protected override fun onSetDialogWidth(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }

    protected override fun onSetDialogHeight(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }

    private fun share(platformBean: SharePlatformBean) {
        mBinding?.includeContent?.clContent?.captureView(dialog?.window) {
            ShareManager.update(
                RiverShareContent().apply {
                    mTitle = "快来提问吧"
                    mText = "快来提问吧"
//                mBitmap = mBinding?.includeContent?.clContent?.toBitmap()
                    mBitmap = it
                }, platformBean.platform ?: RIVER_SHARE_MEDIA.WEIXIN
            ).shareImageLocal(topActivity)
        }

//        ShareManager.update(
//            RiverShareContent().apply {
//                mTitle = "快来提问吧"
//                mText = "快来提问吧"
//                mBitmap = mBinding?.includeContent?.clContent?.toBitmap()
////                mBitmap = it
//            }, platformBean.platform ?: SHARE_MEDIA.WEIXIN
//        ).shareImageLocal(topActivity)
    }


    private fun updateView() {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
    }
}
