package river.chat.businese_main.chat

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import river.chat.businese_common.router.jump2Login
import river.chat.businese_common.ui.recycleview.CustomItemDecoration
import river.chat.business_main.R
import river.chat.lib_core.storage.database.model.CardMsgBean
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.utils.longan.*
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_core.view.recycleview.databingding.BaseBindingAdapter

class ChatViewModel : BaseViewModel() {

//    val request = UserRequest(this)

    val data = ObservableArrayList<CardMsgBean>()
    val itemDecorations =
        listOf<RecyclerView.ItemDecoration>(CustomItemDecoration(), CustomItemDecoration())

    // 定义多 item 布局类型的创建器
    val itemViewTypes = object : BaseBindingAdapter.ItemViewTypeCreator {

        override fun getItemViewType(position: Int, item: Any?): Int {
//            item as MessageBean
//            return when (item.source) {
//                MessageSource.FROM_AI -> 0
//                MessageSource.FRE_SELF -> 1
//                else -> 0
//            }
            return 0
        }

        override fun getItemLayout(viewType: Int): Int {
            // 根据不同的布局类型返回不同的布局资源 id
//            return if (viewType == 0) {
//                R.layout.item_msg_ai
//            } else {
//                R.layout.item_msg_self
//            }
            return R.layout.item_msg_card
        }

    }

    fun onItemClick(item: Any?) {
        if (item is String) {
            item.toast()
            jump2Login()
        }
    }

    fun onItemLongClick(item: MessageBean?): Boolean {
        item?.let {
            if (it.isSelf()) {
                "复制成功".toast()
            } else {
                shareText(it.content ?: "", "分享到")
//                shareText(it.content ?: "", "分享到")
            }
        }
        return false
    }

    fun onCopyitem: MessageBean?) {
    {

    }

    private var morePopWindow: ChatItemMorePopWindow? = null

    /**
     * 展示更多选项弹窗
     */
    fun onItemMoreClick(
        dropView: View,
        contentView: View,
        msg: MessageBean,
    ):Boolean {
        dismissPopWindow()
        morePopWindow = ChatItemMorePopWindow(
            topActivity,
            msg,
            object : OnChatItemMoreClickListener {
                override fun onCopyClick() {
                    "已复制".toast()
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

