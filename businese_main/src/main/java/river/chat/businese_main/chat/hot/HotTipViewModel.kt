package river.chat.businese_main.chat.hot

import androidx.databinding.ObservableArrayList
import river.chat.businese_common.router.jump2Login
import river.chat.lib_core.storage.database.model.CardMsgBean
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.utils.longan.shareText
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel

class HotTipViewModel : BaseViewModel() {


    val data = ObservableArrayList<HotTipBean>()


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


}

