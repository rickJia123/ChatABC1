package river.chat.businese_main.vip

import androidx.databinding.ObservableArrayList
import river.chat.businese_main.api.MainCommonRequest
import river.chat.lib_core.view.main.BaseViewModel

class VipViewModel : BaseViewModel() {


    val request = MainCommonRequest(this)

    val normalRightList = ObservableArrayList<String>()
    val vipRightList = ObservableArrayList<String>()

    val vipExchangeTips = ObservableArrayList<String>()


}

