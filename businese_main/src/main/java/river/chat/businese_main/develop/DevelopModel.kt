package river.chat.businese_main.develop

import androidx.databinding.ObservableArrayList
import river.chat.businese_main.api.MainCommonRequest
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.database.MessageBean

class DevelopModel : BaseViewModel() {


    val request = MainCommonRequest(this)
    val data = ObservableArrayList<MessageBean>()



}