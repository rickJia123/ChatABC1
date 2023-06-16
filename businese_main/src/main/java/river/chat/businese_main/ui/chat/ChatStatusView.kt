package river.chat.businese_main.ui.chat

import android.content.Context
import android.graphics.Color.red
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.utils.logChat
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewChatStatusBinding
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.storage.database.model.MessageStatus
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.loadSimple
import river.chat.lib_core.view.base.LifecycleView


class ChatStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {


    private val viewBinding: ViewChatStatusBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        river.chat.business_main.R.layout.view_chat_status,
        this,
        true
    )

    init {

    }


    /**
     * 刷新数据
     */
    fun refresh(questionMsg:MessageBean,answerMsg: MessageBean, answerText: AppCompatTextView, reloadView: AppCompatImageView) {
        ("答案状态 statusMsg：" + answerMsg).logChat()
        var status = answerMsg.status
        when (status) {
            MessageStatus.COMPLETE -> {
                answerText.setTextColor(river.chat.lib_resource.R.color.chatATv.getColor())
                answerText.visibility = VISIBLE
                visibility = GONE
                reloadView.visibility = GONE
            }
            MessageStatus.LOADING -> {
                answerText.visibility = GONE
                visibility = VISIBLE
                viewBinding.loadingView.visibility = VISIBLE
//                viewBinding.loadingView.start()
                reloadView.visibility = GONE
            }
            MessageStatus.FAIL -> {
                answerText.text = "加载失败"
                answerText.setTextColor(river.chat.lib_core.R.color.red.getColor())
                answerText.visibility = VISIBLE
                visibility = VISIBLE
                viewBinding.loadingView.visibility = GONE
//                viewBinding.loadingView.stop()
                reloadView.visibility = VISIBLE
                reloadView.singleClick {
                    reloadView.loadSimple(river.chat.lib_resource.R.drawable.chat_reload_disabled)
                    MessageCenter.beginReload(questionMsg) {
                        reloadView.loadSimple(river.chat.lib_resource.R.drawable.chat_reload_usful)
                    }
                }
            }
        }


    }


}