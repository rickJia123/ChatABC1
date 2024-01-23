package river.chat.businese_main.collection

import androidx.databinding.ObservableArrayList
import river.chat.businese_common.router.jump2Login
import river.chat.business_main.R
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_core.view.recycleview.databingding.BaseBindingAdapter
import river.chat.lib_resource.model.database.CardMsgBean

class CollectionViewModel : BaseViewModel() {


    val data = ObservableArrayList<CardMsgBean>()


    // 定义多 item 布局类型的创建器
    val itemViewTypes = object : BaseBindingAdapter.ItemViewTypeCreator {

        override fun getItemViewType(position: Int, item: Any?): Int {
            return 0
        }

        override fun getItemLayout(viewType: Int): Int {
            // 根据不同的布局类型返回不同的布局资源 id

            return R.layout.item_simple_list
        }

    }

    fun onItemClick(item: Any?) {
        if (item is String) {
            item.toastSystem()
            jump2Login()
        }
    }



}
