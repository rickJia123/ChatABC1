package river.chat.chatevery.ui.dashboard

import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import river.chat.businese_common.ui.recycleview.CustomItemDecoration
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel

class DashboardViewModel : BaseViewModel() {
    val data = ObservableArrayList<OrderBean>()
    val itemDecorations =
        listOf<RecyclerView.ItemDecoration>(CustomItemDecoration(), CustomItemDecoration())


    fun loadOrderList() {
        for (i in 0..20) {
            data.add(OrderBean().apply {
                price = (i * 10f).toDouble()
                type = i
            })
        }
    }


    fun onItemClick(item: Any?) {
        if (item is String) {
            item.toast()
        }
    }

    fun onItemTestClick(item: OrderBean) {
        ("测试：${item.type}").toast()
    }
}