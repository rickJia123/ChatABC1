package river.chat.businese_main.feedback

import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import river.chat.businese_common.router.jump2Login
import river.chat.businese_common.ui.recycleview.CustomItemDecoration
import river.chat.businese_main.api.MainCommonRequest
import river.chat.business_main.R
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_core.view.recycleview.databingding.BaseBindingAdapter
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.storage.database.model.MessageSource

class FeedBackViewModel : BaseViewModel() {


    val request = MainCommonRequest(this)
    val data = ObservableArrayList<MessageBean>()



}