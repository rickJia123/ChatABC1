package river.chat.businese_main.creation

import river.chat.businese_main.api.MainCommonRequest
import river.chat.lib_core.view.main.BaseViewModel

class CreationViewModel : BaseViewModel() {

    var mModels: MutableList<CreationBeans> = mutableListOf()

    val mainCommonRequest = MainCommonRequest(this)


}

