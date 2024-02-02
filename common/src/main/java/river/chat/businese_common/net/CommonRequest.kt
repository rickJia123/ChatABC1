package river.chat.businese_common.net

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import river.chat.businese_common.config.ServiceConfigManager
import river.chat.lib_core.wx.WxManager
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.CreateOrderRequestBean
import river.chat.lib_resource.model.CreateOrderResBean
import river.chat.lib_resource.model.QueryOrderRequestBean
import river.chat.lib_resource.model.QueryOrderResBean
import river.chat.lib_resource.model.database.AppUpdateConfigResBean
import river.chat.lib_resource.model.database.ConfigServiceBean
import river.chat.lib_resource.model.database.ServiceConfigBean
import river.chat.lib_resource.model.database.ServiceConfigServiceBean

/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
class CommonRequest(viewModel: BaseViewModel) : BaseRequest(viewModel) {


    val configRequest = MutableLiveData<RequestResult<String>>()

    /**
     * 根据入参获取配置
     */
    fun requestConfig(key: String, resultCallBack: (RequestResult<ServiceConfigBean>) -> Unit) {
        launchFlow(
            request = {
                CommonApiService.requestConfig(key)
            },
            dataResp = { data, time ->
                data?.let {
                    resultCallBack.invoke(
                        RequestResult(
                            isSuccess = true,
                            ServiceConfigBean().apply {
                                this.id = data.id
                                this.appDownUrl = data.appDownUrl
                                this.appDownLink = data.appDownLink
                                this.appPolicyUrl = data.appPolicyUrl
                                this.appPrivacyPolicy = data.appPrivacyPolicy
                                this.value = data.value
                                this.appShareBg = data.appShareBg
                                this.updateTime = System.currentTimeMillis()
                                this.closeModulesStr =
                                    ServiceConfigManager.getCloseModulesStr(data.closeModules)
                            }

                        )
                    )
                }

            },
            error = {
                resultCallBack.invoke(RequestResult(errorMsg = it.message ?: ""))
            }
        )
    }

    /**
     * 获取基本配置信息
     */
    fun requestDefaultConfig(resultCallBack: (RequestResult<AppUpdateConfigResBean>) -> Unit) {
        launchFlow(
            request = {
                CommonApiService.requestDefaultConfig()
            },
            dataResp = { data, time ->
                resultCallBack.invoke(
                    RequestResult(
                        isSuccess = true,
                        data = data ?: AppUpdateConfigResBean()
                    )
                )
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
            flow {
                emit(
                    CommonApiService.requestWechatAccessToken(
                        WxManager.APP_ID,
                        WxManager.SECRET, code
                    )
                )
            }
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

    }

    /**
     * 创建订单
     */
    fun createPayOrder(
        requestBean: CreateOrderRequestBean,
        resultCallBack: (RequestResult<CreateOrderResBean>) -> Unit
    ) {
        launchFlow(
            request = {
                CommonApiService.createPayOrder(requestBean)
            },
            dataResp = { data, time ->
                resultCallBack.invoke(
                    RequestResult(
                        isSuccess = true,
                        data = data
                    )
                )
            },
            error = {
                resultCallBack.invoke(RequestResult(errorMsg = it.message ?: ""))
            }
        )
    }

    /**
     * 查询订单状态
     */
    fun queryOrder(
        queryOrderRequestBean: QueryOrderRequestBean,
        resultCallBack: (RequestResult<QueryOrderResBean>) -> Unit
    ) {
        launchFlow(
            request = {
                CommonApiService.queryOrder(queryOrderRequestBean)
            },
            dataResp = { data, time ->
                resultCallBack.invoke(
                    RequestResult(
                        isSuccess = true,
                        data = data
                    )
                )
            },
            error = {
                resultCallBack.invoke(RequestResult(errorMsg = it.message ?: ""))
            }
        )
    }

}