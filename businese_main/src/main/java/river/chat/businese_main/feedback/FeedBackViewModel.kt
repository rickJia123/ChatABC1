package river.chat.businese_main.feedback

import androidx.databinding.ObservableArrayList
import river.chat.businese_main.api.MainCommonRequest
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.database.MessageBean

class FeedBackViewModel : BaseViewModel() {


    val request = MainCommonRequest(this)
    val data = ObservableArrayList<MessageBean>()



}