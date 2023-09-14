package river.chat.businese_main.share


import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.umeng.socialize.bean.SHARE_MEDIA
import river.chat.businese_common.report.ShareTracker
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.utils.loadAvatar
import river.chat.business_main.databinding.DialogShareBinding
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.share.SharePlatformBean
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.tracker.trackNode
import river.chat.lib_core.utils.common.QRCodeUtils
import river.chat.lib_core.utils.exts.ifEmptyOrBlank
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_umeng.ShareManager
import river.chat.lib_umeng.common.RiverShareContent
import river.chat.business_main.R
import river.chat.lib_core.utils.exts.height
import river.chat.lib_core.utils.exts.view.toBitmap
import river.chat.lib_core.utils.longan.screenHeight

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
    private var mTestUrl = "https://www.baidu.com/"

    companion object {
        @JvmStatic
        fun builder(activity: AppCompatActivity): ShareDialog = ShareDialog(activity)
    }

    override fun onStart() {
        super.onStart()
        this.trackNode = TrackNode(
            ShareTracker.KEY_QUESTION to (questionMsg?.content ?: ""),
            ShareTracker.KEY_ANSWER to (answerMsg?.content ?: ""),
        )
        postTrack(
            TrackerEventName.LOAD_SHARE,
        )
    }

    override fun initDataBinding(binding: DialogShareBinding) {

        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        mBinding = binding
        binding.includeContent.tvQuestion.text = questionMsg?.content ?: ""
        binding.includeContent.tvAnswer.text = answerMsg?.content ?: ""
        binding.viewPlatform.mOnPlatformClick = {
            binding.viewPlatform.trackNode = TrackNode(
                ShareTracker.KEY_PLATFORM to (it.platform?.name ?: "")
            )
            binding.viewPlatform.postTrack(
                TrackerEventName.CLICK_SHARE,
            )
            share(it)
        }
        binding.viewPlatform.mOnCancelClick = {
            closeDialog()
        }
        binding.includeContent.ivCode.setImageBitmap(QRCodeUtils.createQRCode(mTestUrl))
        binding.includeContent.ivAvatar.loadAvatar(user.headImg)
        binding.includeContent.tvName.text = user.nickName.ifEmptyOrBlank("GptEvery用户")


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
        shareScroll()
        ShareManager.update(
            RiverShareContent().apply {
                mTitle = "快来提问吧"
                mText = "快来提问吧"
                mBitmap = mBinding?.includeContentOutput?.scrollView?.toBitmap()
//                mBitmap = getScrollScreenShot(mBinding?.includeContent?.scrollView)
//                mBitmap = shareScroll()

            }, platformBean.platform ?: SHARE_MEDIA.WEIXIN
        ).shareImageLocal(topActivity)
    }


    fun shareScroll(): Bitmap? {
        mBinding?.flParent?.visibility = View.GONE
        var mScrollScreenShot: Bitmap? = null



//        val scrollView: NestedScrollView = inflate.findViewById(R.id.scrollView)
//        inflate.post(Runnable {
//            mScrollScreenShot = getScrollScreenShot(scrollView)
//        })
        mScrollScreenShot = mBinding?.includeContentOutput?.llRoot?.toBitmap()
        return mScrollScreenShot
    }

    private fun updateView() {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        mBinding?.let {
            it.includeContentOutput.tvQuestion.text = questionMsg?.content ?: ""
            it.includeContentOutput.tvAnswer.text = answerMsg?.content ?: ""
            it.includeContentOutput.ivCode
                .setImageBitmap(QRCodeUtils.createQRCode(mTestUrl))
            it.includeContentOutput.ivAnswer.loadAvatar(user.headImg)
            it.includeContentOutput.tvName.text =
                user.nickName.ifEmptyOrBlank("GptEvery用户")
            it.includeContentOutput.ivOutputBg.height(screenHeight)
        }

    }


    private fun getScrollScreenShot(view: NestedScrollView?): Bitmap? {
        var bitmap: Bitmap? = null
        if (null != view) {
            var height = 0
            //正确获取ScrollView
            for (i in 0 until view.childCount) {
                height += view.getChildAt(i).height
            }
            if (height > 0) {
                //创建保存缓存的bitmap
                bitmap = Bitmap.createBitmap(view.width, height, Bitmap.Config.ARGB_8888)
                //可以简单的把Canvas理解为一个画板 而bitmap就是块画布
                val canvas = Canvas(bitmap)
                //把view的内容都画到指定的画板Canvas上
                view.draw(canvas)

            }
        }
        return bitmap
    }
}
