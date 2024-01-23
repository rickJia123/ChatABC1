package river.chat.businese_main.chat

import android.view.View
import androidx.databinding.ObservableArrayList
import river.chat.lib_core.utils.longan.copyToClipboard
import river.chat.lib_core.utils.longan.shareText
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.database.CardMsgBean
import river.chat.lib_resource.model.database.MessageBean

class ChatViewModel : BaseViewModel() {

    val data = ObservableArrayList<CardMsgBean>()


    private var morePopWindow: ChatItemMorePopWindow? = null

    /**
     * 展示更多选项弹窗
     */
    fun onItemMoreClick(
        dropView: View,
        contentView: View,
        msg: MessageBean,
    ): Boolean {
        dismissPopWindow()
        morePopWindow = ChatItemMorePopWindow(
            topActivity,
            msg,
            object : OnChatItemMoreClickListener {
                override fun onCopyClick() {
                    "已复制".toastSystem()
                    msg.content?.copyToClipboard()
                }

                override fun onShareClick() {
                    shareText(msg.content ?: "", "分享到")
                }
            })
        morePopWindow?.showAsDropDown(dropView, contentView)
        return false
    }


    private fun dismissPopWindow() {
        if (morePopWindow?.isShowing == true) {
            morePopWindow?.dismiss()
        }
    }


}

