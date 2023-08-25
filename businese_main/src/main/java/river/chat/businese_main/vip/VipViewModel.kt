package river.chat.businese_main.vip

import androidx.databinding.ObservableArrayList
import river.chat.businese_main.api.MainCommonRequest
import river.chat.businese_main.chat.ChatRequest
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.vip.VipRightsBeans

class VipViewModel : BaseViewModel() {


    val request = MainCommonRequest(this)

    val normalRightList = ObservableArrayList<String>()
    val vipRightList = ObservableArrayList<String>()



}

