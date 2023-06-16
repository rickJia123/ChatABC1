package river.chat.businese_main.ui.chat

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.businese_main.utils.logChat
import river.chat.business_main.databinding.ViewChatCardBinding
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.dp
import river.chat.lib_core.utils.system.DisplayUtil.getScreenWidth
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_umeng.ShareManager


class ChatCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {


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
            ShareManager.launchShareBoard(context as Activity).shareText()
        }
    }

    /**
     * 刷新数据
     */
    fun refresh(questionMsg: MessageBean? = null, answerMsg: MessageBean? = null) {
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