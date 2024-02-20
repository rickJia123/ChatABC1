package river.chat.businese_main.home

import river.chat.businese_main.api.MainCommonRequest
import river.chat.lib_core.view.main.BaseViewModel

class HomeViewModel : BaseViewModel() {

    /**
     * vip接口预加载
     */
    val vipRequest = MainCommonRequest(this)

}