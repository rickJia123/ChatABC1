package river.chat.businese_main.ui.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.dataBase.MessageBox
import river.chat.businese_common.report.ChatTracker
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.utils.onReport
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.share.ShareDialog
import river.chat.businese_main.utils.logChat
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewChatCardBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.copyToClipboard
import river.chat.lib_core.utils.longan.dp
import river.chat.lib_core.utils.longan.screenWidth
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageStatus


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
            if (questionMsg?.isCollected == true) {
                questionMsg?.isCollected = false
                "取消收藏".toastSystem()
            } else {
                questionMsg?.isCollected = true
                "收藏成功".toastSystem()
            }
            MessageCenter.updateMsg(questionMsg)
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
    fun refresh(questionMsg: MessageBean? = null, answerMsg: MessageBean? = null) {
        var isLastSuccess =
            MessageBox.getMsgById(answerMsg?.id ?: 100).status == MessageStatus.COMPLETE
//        var isLastSuccess = this.answerMsg?.status == MessageStatus.COMPLETE
        var isCurrentSuccess = answerMsg?.status == MessageStatus.COMPLETE
        //上次成功，本次失败，不处理状态(针对同一个问题)

        if (questionMsg != null) {
            viewBinding.tvQuestion.text = questionMsg.content
        }

        if (isLastSuccess && !isCurrentSuccess) {
            return
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