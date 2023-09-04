package river.chat.businese_main.ui.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.businese_main.share.ShareDialog
import river.chat.businese_main.utils.logChat
import river.chat.business_main.databinding.ViewChatCardBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.copyToClipboard
import river.chat.lib_core.utils.longan.dp
import river.chat.lib_core.utils.longan.screenWidth
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageStatus


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
        var chatWidth = screenWidth - 40.dp
        viewBinding.tvAnswer.maxWidth = chatWidth.toInt()
        viewBinding.tvQuestion.maxWidth = chatWidth.toInt()
//        commentViewBinding.etWriteReply.postDelayed({
//            showSoftInput()
//        }, 150)
        initClick()
    }

    private fun initClick() {
        viewBinding.clAnswer.singleClick {
            answerMsg?.let {
                if (it.status == MessageStatus.COMPLETE) {
                    "回答已复制".toast()
                    answerMsg?.content?.copyToClipboard()
                } else {
                    "该回答状态异常哦".toast()
                }
            }


        }
        viewBinding.clQuestion.singleClick {
            "问题已复制".toast()
            questionMsg?.content?.copyToClipboard()
        }

        viewBinding.ivShare.singleClick {
            ShareDialog.builder(topActivity).show(questionMsg, answerMsg)
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