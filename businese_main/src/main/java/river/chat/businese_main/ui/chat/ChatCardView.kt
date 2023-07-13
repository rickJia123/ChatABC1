package river.chat.businese_main.ui.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.businese_main.share.ShareDialog
import river.chat.businese_main.utils.logChat
import river.chat.business_main.databinding.ViewChatCardBinding
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.dp
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.utils.system.DisplayUtil.getScreenWidth
import river.chat.lib_core.view.base.LifecycleView


class ChatCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    private var questionMsg: MessageBean? = null
    private var answerMsg: MessageBean? = null
    private val viewBinding: ViewChatCardBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        river.chat.business_main.R.layout.view_chat_card,
        this,
        true
    )


    init {
        var chatWidth = getScreenWidth() - 40.dp
        viewBinding.tvAnswer.maxWidth = chatWidth.toInt()
        viewBinding.tvQuestion.maxWidth = chatWidth.toInt()
//        commentViewBinding.etWriteReply.postDelayed({
//            showSoftInput()
//        }, 150)
        initClick()
    }

    private fun initClick() {
        viewBinding.viewChatCard.singleClick {
            ShareDialog.builder(topActivity).show(questionMsg, answerMsg)

//            shareText(  "1233", "分享到")

//            var bitmap=viewBinding.clRoot.getViewBitmap()
//            shareTextAndImage("1212",  Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap , null,null));, "分享到")

//            ShareManager.update(
//                RiverShareContent().apply {
//                mTitle = "123"
//                mText = "hahahha1"
//                mBitmap = viewBinding.clRoot.getViewBitmap()
//
//            },SHARE_MEDIA.WEIXIN).shareTextAndImage(topActivity)
//            ShareManager.launchShareBoard(context as Activity).shareText()
        }
        viewBinding.ivCopy.singleClick {

        }
    }

    /**
     * 刷新数据
     */
    fun refresh(questionMsg: MessageBean? = null, answerMsg: MessageBean? = null) {
        this.questionMsg = questionMsg
        this.answerMsg = answerMsg
        questionMsg?.toString()?.logChat()
        if (questionMsg != null) {
            viewBinding.tvQuestion.text = questionMsg.content
        }
        if (answerMsg != null && questionMsg != null) {
            viewBinding.tvAnswer.text = answerMsg.content
            viewBinding.viewChatStatus.refresh(
                questionMsg,
                answerMsg,
                viewBinding.tvAnswer,
                viewBinding.ivReLoad
            )
        }
    }


}