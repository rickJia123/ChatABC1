package river.chat.businese_main.share


import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.umeng.socialize.bean.SHARE_MEDIA
import river.chat.businese_main.api.ChatAnswerBean
import river.chat.business_main.databinding.DialogShareBinding
import river.chat.lib_core.share.SharePlatformBean
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.utils.exts.getViewBitmap
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment
import river.chat.lib_umeng.ShareManager
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

    companion object {
        @JvmStatic
        fun builder(activity: AppCompatActivity): ShareDialog = ShareDialog(activity)
    }


    override fun initDataBinding(binding: DialogShareBinding) {
        mBinding = binding
        binding.tvQuestion.text = questionMsg?.content ?: ""
        binding.tvAnswer.text = answerMsg?.content ?: ""
        binding.viewPlatform.mOnPlatformClick = {
            share(it)
        }
        binding.viewPlatform.mOnCancelClick = {
            closeDialog()
        }
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
        ShareManager.update(
            RiverShareContent().apply {
                mTitle = "快来提问吧"
                mText = "快来提问吧"
                mBitmap = mBinding?.clContent?.getViewBitmap()

            }, platformBean.platform ?: SHARE_MEDIA.WEIXIN
        ).shareTextAndImage(topActivity)
    }

}
