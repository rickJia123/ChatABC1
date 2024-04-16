package river.chat.businese_main.ui.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.dataBase.MessageBox
import river.chat.businese_common.report.ChatTracker
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.utils.onReport
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.share.ShareDialog
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewChatCardBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.copyToClipboard
import river.chat.lib_core.utils.longan.dp
import river.chat.lib_core.utils.longan.screenWidth
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.database.MessageBean
import river.chat.lib_resource.model.database.MessageStatus


class ChatCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    private val viewBinding: ViewChatCardBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_chat_card,
        this,
        true
    )


    init {
        var chatWidth = screenWidth - 120.dp
        viewBinding.tvAnswer.maxWidth = chatWidth.toInt()
        viewBinding.tvQuestion.maxWidth = chatWidth.toInt()

    }

    private fun initClick(questionMsg: MessageBean? = null, answerMsg: MessageBean? = null) {
        viewBinding.clAnswer.singleClick {
            answerMsg?.let {
                var toastMsg = "回答已复制"
                if (it.status == MessageStatus.COMPLETE) {
                    answerMsg?.content?.copyToClipboard()
                } else {
                    toastMsg = "该回答状态异常哦"
                }
                toastMsg.toastSystem()
                onReport(
                    TrackerEventName.CLICK_CHAT,
                    ChatTracker.CLICK_ACTION to "复制回答：" + answerMsg?.content,
                )
            }


        }
        viewBinding.clQuestion.singleClick {
            "问题已复制".toastSystem()
            questionMsg?.content?.copyToClipboard()
            onReport(
                TrackerEventName.CLICK_CHAT,
                ChatTracker.CLICK_ACTION to "复制问题：" + questionMsg?.content,
            )
        }

        viewBinding.ivShare.singleClick {
            ShareDialog.builder(topActivity).show(questionMsg, answerMsg)
        }
        viewBinding.ivCollection.singleClick {
            MessageCenter.toggleCollectionStatus(questionMsg, false)
            updateCollectionStatus(questionMsg)
        }
    }

    private fun updateCollectionStatus(questionMsg: MessageBean? = null) {
        viewBinding.ivCollection.setImageResource(
            if (questionMsg?.isCollected == true) {
                R.drawable.collection_sel
            } else {
                R.drawable.collection_nosel
            }
        )
    }

    /**
     * 刷新数据
     */
    fun refresh(questionMsg: MessageBean? = null, answerMsg: MessageBean? = null,isLastItem:Boolean=false) {
        var isLastSuccess =
            MessageBox.getMsgById(answerMsg?.id ?: 100).status == MessageStatus.COMPLETE
//        var isLastSuccess = this.answerMsg?.status == MessageStatus.COMPLETE
        var isCurrentSuccess = answerMsg?.status == MessageStatus.COMPLETE


        if (questionMsg != null) {
            viewBinding.tvQuestion.text = questionMsg.content
        }

        //上次成功，本次没权益，不处理状态(重试)
//        if (isLastSuccess && !isCurrentSuccess) {
//            return
//        }

        if (answerMsg?.status == MessageStatus.COMPLETE) {
            viewBinding.llAction.visibility = VISIBLE
        } else {
            viewBinding.llAction.visibility = View.GONE
        }
        if (answerMsg != null && questionMsg != null) {
            viewBinding.tvAnswer.text = answerMsg.content
            viewBinding.viewChatStatus.refresh(
                questionMsg,
                answerMsg,
                viewBinding.clAnswer,
                viewBinding.tvAnswer,
                viewBinding.ivReLoad
            )
            updateCollectionStatus(questionMsg)
        }

        initClick(questionMsg, answerMsg)
    }


}