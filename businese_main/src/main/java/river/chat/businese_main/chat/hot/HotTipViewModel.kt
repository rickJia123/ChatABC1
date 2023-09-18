package river.chat.businese_main.chat.hot

import androidx.databinding.ObservableArrayList
import river.chat.businese_common.report.ChatTracker
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.utils.onReport
import river.chat.businese_main.chat.ChatRequest
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageHelper
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel

class HotTipViewModel : BaseViewModel() {


    val request = ChatRequest(this)

    val data = ObservableArrayList<HotTipItemBean>()


    fun onItemClick(item: Any?) {
        item?.let {
            it as HotTipItemBean
            ReportManager.reportEvent(
                TrackerEventName.CLICK_CHAT,
                mutableMapOf(
                    ChatTracker.CLICK_ACTION to "热门问题点击：" + it.hotQuestion,
                )
            )
            "".toast()
            MessageCenter.postReceiveMsg(MessageHelper.buildSelfMsg(it.hotQuestion ?: ""))
        }

    }

    fun onItemLongClick(item: HotTipItemBean): Boolean {
        item?.let {
            "复制成功".toast()
        }
        return false
    }


}

