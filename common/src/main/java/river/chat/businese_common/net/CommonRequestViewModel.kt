package river.chat.businese_common.net

import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.view.main.BaseViewModel

class CommonRequestViewModel : BaseViewModel() {

    private val commonRequest = CommonRequest(this)

    //获取配置信息
    fun requestConfig(key: String, resultCallBack: (RequestResult<String>) -> Unit) {
        commonRequest.requestConfig(key, resultCallBack)
    }

}