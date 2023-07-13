package river.chat.lib_core.share

import androidx.databinding.ObservableArrayList
import river.chat.businese_common.router.jump2Login
import river.chat.lib_core.R
import river.chat.lib_core.storage.database.model.CardMsgBean
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_core.view.recycleview.databingding.BaseBindingAdapter

class ShareViewModel : BaseViewModel() {

    val data = ObservableArrayList<SharePlatformBean>()

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

            return R.layout.item_share_platform
        }

    }

    fun onItemClick(item: Any?) {
        "分享喽".toast()
    }





}

