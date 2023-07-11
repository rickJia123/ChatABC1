package river.chat.businese_common.net

import androidx.lifecycle.MutableLiveData
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.view.main.BaseViewModel

/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
class CommonRequest(viewModel: BaseViewModel) : BaseRequest(viewModel) {


    val configRequest = MutableLiveData<RequestResult<String>>()

    /**
     * 获取验证码
     */
    fun requestConfig(key: String,resultCallBack: (RequestResult<String>) -> Unit) {
        launchFlow(
            request = {
                CommonApiService.requestConfig(key)
            },
            dataResp = {
                resultCallBack.invoke(RequestResult(isSuccess = true, data = it ?: ""))
//                configRequest.value = RequestResult(isSuccess = true, data = it ?: "")
            },
            error = {
                resultCallBack.invoke(RequestResult(errorMsg = it.message ?: ""))
//                configRequest.value = RequestResult(errorMsg = it.message ?: "")
            }
        )
    }

}