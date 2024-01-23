package river.chat.businese_main.shareapp

import androidx.databinding.ObservableArrayList
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.database.MessageBean

class ShareAppViewModel : BaseViewModel() {
    

    val data = ObservableArrayList<MessageBean>()


}