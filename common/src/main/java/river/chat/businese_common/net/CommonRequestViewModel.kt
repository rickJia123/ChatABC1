package river.chat.businese_common.net

import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.CreateOrderRequestBean
import river.chat.lib_resource.model.CreateOrderResBean
import river.chat.lib_resource.model.QueryOrderRequestBean
import river.chat.lib_resource.model.QueryOrderResBean
import river.chat.lib_resource.model.database.AppUpdateConfigResBean
import river.chat.lib_resource.model.database.ServiceConfigBean

class CommonRequestViewModel : BaseViewModel() {

    private val commonRequest = CommonRequest(this)

    //获取配置信息
    fun requestConfig(key: String, resultCallBack: (RequestResult<ServiceConfigBean>) -> Unit) {
        commonRequest.requestConfig(key, resultCallBack)
    }

    //获取版本更新配置信息
    fun requestAppUpdateConfig(resultCallBack: (RequestResult<AppUpdateConfigResBean>) -> Unit) {
        commonRequest.requestDefaultConfig(resultCallBack)
    }

    // 获取微信登录信息
    fun requestWechatAccessToken(
        code: String, resultCallBack: (RequestResult<String>) -> Unit
    ) {
        commonRequest.requestWechatAccessToken(code, resultCallBack)
    }

    //获取配置信息
    fun createPayOrder(
        requestBean: CreateOrderRequestBean,
        resultCallBack: (RequestResult<CreateOrderResBean>) -> Unit
    ) {
        commonRequest.createPayOrder(requestBean, resultCallBack)
    }

    //查询订单状态
    fun queryOrder(
        queryOrderRequestBean: QueryOrderRequestBean,
        resultCallBack: (RequestResult<QueryOrderResBean>) -> Unit
    ) {
        commonRequest.queryOrder(queryOrderRequestBean, resultCallBack)
    }

}