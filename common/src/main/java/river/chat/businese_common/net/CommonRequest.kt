package river.chat.businese_common.net

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import river.chat.businese_common.wx.WxManager
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
     * 根据入参获取配置
     */
    fun requestConfig(key: String, resultCallBack: (RequestResult<ConfigResBean>) -> Unit) {
        launchFlow(
            request = {
                CommonApiService.requestConfig(key)
            },
            dataResp = {
                resultCallBack.invoke(RequestResult(isSuccess = true, data = it ?: ConfigResBean()))
            },
            error = {
                resultCallBack.invoke(RequestResult(errorMsg = it.message ?: ""))
            }
        )
    }

    /**
     * 获取基本配置信息
     */
    fun requestDefaultConfig( resultCallBack: (RequestResult<DefaultConfigResBean>) -> Unit) {
        launchFlow(
            request = {
                CommonApiService.requestDefaultConfig()
            },
            dataResp = {
                resultCallBack.invoke(RequestResult(isSuccess = true, data = it ?: DefaultConfigResBean()))
            },
            error = {
                resultCallBack.invoke(RequestResult(errorMsg = it.message ?: ""))
            }
        )
    }

    /**
     * 获取微信登录信息
     */
    fun requestWechatAccessToken(
        code: String,
        resultCallBack: (RequestResult<String>) -> Unit
    ) {
        viewModel.viewModelScope.launch {
            flow { emit(CommonApiService.requestWechatAccessToken(WxManager.APP_ID,WxManager.SECRET,code)) }
                .flowOn(Dispatchers.IO)
                .catch { t: Throwable ->
                    resultCallBack.invoke(RequestResult(errorMsg = t.message ?: ""))
                }
                .collect {
                    try {
                        resultCallBack.invoke(
                            RequestResult(
                                isSuccess = true,
                                data = it.access_token ?: ""
                            )
                        )
                    } catch (t: Throwable) {
                        resultCallBack.invoke(RequestResult(errorMsg = t.message ?: ""))
                    }

                }
        }
//        launchFlow(
//            request = {
//                CommonApiService.requestWechatAccessToken(code, resultCallBack)
//            },
//            dataResp = {
//                resultCallBack.invoke(RequestResult(isSuccess = true, data = it ?: ""))
//            },
//            error = {
//                resultCallBack.invoke(RequestResult(errorMsg = it.message ?: ""))
//            }
//        )
    }

}