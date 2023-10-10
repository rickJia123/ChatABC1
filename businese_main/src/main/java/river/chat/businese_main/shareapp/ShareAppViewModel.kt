package river.chat.businese_main.shareapp

import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import river.chat.businese_common.router.jump2Login
import river.chat.businese_common.ui.recycleview.CustomItemDecoration
import river.chat.business_main.R
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_core.view.recycleview.databingding.BaseBindingAdapter
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageSource

class ShareAppViewModel : BaseViewModel() {
    

    val data = ObservableArrayList<MessageBean>()


}