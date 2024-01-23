package river.chat.businese_main.collection

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewCollectionSimpleCardViewBinding
import river.chat.lib_core.utils.longan.dp
import river.chat.lib_core.utils.longan.screenWidth
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.database.MessageBean


class CollectionSimpleCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    private var questionMsg: MessageBean? = null
    private var answerMsg: MessageBean? = null
    private val viewBinding: ViewCollectionSimpleCardViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_collection_simple_card_view,
        this,
        true
    )


    init {
        var chatWidth = screenWidth - 120.dp
        viewBinding.tvAnswer.maxWidth = chatWidth.toInt()
        viewBinding.tvQuestion.maxWidth = chatWidth.toInt()
        initClick()
    }

    private fun initClick() {
//        viewBinding.clAnswer.singleClick {
//            answerMsg?.let {
//                var toastMsg = "回答已复制"
//                if (it.status == MessageStatus.COMPLETE) {
//                    answerMsg?.content?.copyToClipboard()
//                } else {
//                    toastMsg = "该回答状态异常哦"
//                }
//                toastMsg.toastSystem()
//                onReport(
//                    TrackerEventName.CLICK_CHAT,
//                    ChatTracker.CLICK_ACTION to "复制回答：" + answerMsg?.content,
//                )
//            }
//
//
//        }
//        viewBinding.clQuestion.singleClick {
//            "问题已复制".toastSystem()
//            questionMsg?.content?.copyToClipboard()
//            onReport(
//                TrackerEventName.CLICK_CHAT,
//                ChatTracker.CLICK_ACTION to "复制问题：" + questionMsg?.content,
//            )
//        }

    }

    private fun updateCollectionStatus() {

    }

    /**
     * 刷新数据
     */
    fun refresh(questionMsg: MessageBean? = null, answerMsg: MessageBean? = null) {
        this.questionMsg = questionMsg
        this.answerMsg = answerMsg
        if (questionMsg != null) {
            viewBinding.tvQuestion.text = "Q: " + questionMsg.content
        }
        if (answerMsg != null && questionMsg != null) {
            viewBinding.tvAnswer.text = "A: " + answerMsg.content
            updateCollectionStatus()
        }
    }


}