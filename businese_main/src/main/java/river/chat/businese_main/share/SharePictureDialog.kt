package river.chat.businese_main.share


import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import coil.load
import river.chat.businese_common.dataBase.AiPictureBox
import river.chat.businese_common.report.ShareTracker
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.utils.exts.getBitmap
import river.chat.businese_common.utils.exts.loadAvatar
import river.chat.business_main.databinding.DialogSharePictureBinding
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.share.SharePlatformBean
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.tracker.trackNode
import river.chat.lib_core.utils.common.QRCodeUtils
import river.chat.lib_core.utils.exts.ifEmptyOrBlank
import river.chat.lib_core.utils.exts.view.captureView
import river.chat.lib_core.utils.exts.view.saveBitmapToMediaStore
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment
import river.chat.lib_umeng.ShareManager
import river.chat.lib_umeng.common.RIVER_SHARE_MEDIA
import river.chat.lib_umeng.common.RiverShareContent

/**
 * Created by beiyongChao on 2024/2/9
 * Description:
 */
class SharePictureDialog(var dialogActivity: AppCompatActivity) :
    BaseBindingDialogFragment<DialogSharePictureBinding>() {

    private var mBinding: DialogSharePictureBinding? = null
    var mPreviewId: String = ""
    var mQuestion: String = ""

    companion object {
        @JvmStatic
        fun builder(activity: AppCompatActivity): SharePictureDialog = SharePictureDialog(activity)
    }

    override fun onStart() {
        super.onStart()
        this.trackNode = TrackNode(
            ShareTracker.KEY_QUESTION to ("图片分享:"+mQuestion), ShareTracker.KEY_ANSWER to ("图片")
        )
        postTrack(
            TrackerEventName.LOAD_SHARE,
        )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun initDataBinding(binding: DialogSharePictureBinding) {

        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        mBinding = binding

        binding.viewPlatform.mOnPlatformClick = {
            binding.viewPlatform.trackNode = TrackNode(
                ShareTracker.KEY_PLATFORM to (it.platform?.name ?: "")
            )
            binding.viewPlatform.postTrack(
                TrackerEventName.CLICK_SHARE,
            )

            if (it.platform == RIVER_SHARE_MEDIA.MORE) {
                AiPictureBox.getAnswerById(mPreviewId).getBitmap()?.let {
                    saveBitmapToMediaStore(dialogActivity, it)
                }

            } else {
                share(it)
            }

        }
        binding.viewPlatform.setPictureChannels()
        binding.viewPlatform.mOnCancelClick = {
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

    fun show(mPreviewId: String, question: String) {
        this.mPreviewId = mPreviewId
        this.mQuestion = question
        dialogActivity.supportFragmentManager.let {
            show(it, "ShareDialog")
        }
    }


    override fun onSetDialogWidth(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }

    override fun onSetDialogHeight(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }

    private fun share(platformBean: SharePlatformBean) {
        mBinding?.includeContent?.clContent?.captureView(dialog?.window) {
            ShareManager.update(
                RiverShareContent().apply {
                    mTitle = "快来GptEvery绘制吧"
                    mText = "快来GptEvery绘制吧"
//                mBitmap = mBinding?.includeContent?.clContent?.toBitmap()
                    mBitmap = it
                }, platformBean.platform ?: RIVER_SHARE_MEDIA.WEIXIN
            ).shareImageLocal(topActivity)
        }

    }


    private fun updateView() {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        AiPictureBox.getAnswerById(mPreviewId)?.getBitmap()?.let {
            mBinding?.includeContent?.ivPicture?.setImageBitmap(it)
        }
        mBinding?.includeContent?.tvQuestion?.text = mQuestion
//        mBinding?.includeContent?.ivPicture?.setRadius(10f)
    }
}
