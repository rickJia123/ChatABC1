package river.chat.businese_main.mine

import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import river.chat.businese_common.router.jump2Login
import river.chat.businese_common.ui.recycleview.CustomItemDecoration
import river.chat.business_main.R
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_core.view.recycleview.databingding.BaseBindingAdapter
import river.chat.lib_resource.model.database.MessageBean
import river.chat.lib_resource.model.database.MessageSource

class SettingsViewModel : BaseViewModel() {
    

    val data = ObservableArrayList<MessageBean>()
    val itemDecorations =
        listOf<RecyclerView.ItemDecoration>(CustomItemDecoration(), CustomItemDecoration())

    // 定义多 item 布局类型的创建器
    val itemViewTypes = object : BaseBindingAdapter.ItemViewTypeCreator {

        override fun getItemViewType(position: Int, item: Any?): Int {
            item as MessageBean
            return when (item.source) {
                MessageSource.FROM_AI -> 0
                MessageSource.FRE_SELF -> 1
                else -> 0
            }
        }

        override fun getItemLayout(viewType: Int): Int {
            // 根据不同的布局类型返回不同的布局资源 id
            return if (viewType == 0) {
                R.layout.item_msg_ai
            } else {
                R.layout.item_msg_self
            }
        }

    }

    fun onItemClick(item: Any?) {
        if (item is String) {
            item.toastSystem()
            jump2Login()
        }
    }

    fun onItemTestClick(item: MessageBean) {
        ("测试：${item.content}").toastSystem()
    }
}