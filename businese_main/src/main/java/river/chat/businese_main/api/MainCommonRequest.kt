package river.chat.businese_main.api

import androidx.lifecycle.MutableLiveData
import retrofit2.http.Body
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.utils.longan.deviceInfos
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.VipRightsBean
import river.chat.lib_resource.model.VipSkuBean

/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
class MainCommonRequest(viewModel: BaseViewModel) : BaseRequest(viewModel) {

    val feedBackResult = MutableLiveData<RequestResult<String>>()
    val paySkuResult = MutableLiveData<RequestResult<MutableList<VipSkuBean>>>()
    val vipRightResult = MutableLiveData<RequestResult<VipRightsBean>>()
    val exChangeResult = MutableLiveData<RequestResult<Boolean>>()


    /**
     * 意见反馈
     */
    fun confirmFeedback(
        content: String,
        contact: String,
    ) {
        launchFlow(
            request = {
                MainBusinessApiService.confirmFeedback(content, contact, deviceInfos())
            },
            dataResp = { data, time ->
                feedBackResult.value =
                    RequestResult(isSuccess = true, data = data)
            },
            error = {
                it.message?.toast()
                feedBackResult.value =
                    RequestResult(isSuccess = false, data = "")
            }
        )
    }


    /**
     * 获取充值SkU列表
     */
    fun getPaySku(
    ) {
        launchFlow(
            request = {
                MainBusinessApiService.getPaySku()
            },
            dataResp = { data, time ->
                paySkuResult.value =
                    RequestResult(isSuccess = true, data = data)
            },
            error = {
                paySkuResult.value =
                    RequestResult(isSuccess = false)
            }
        )
    }

    /**
     * 获取权益对比
     */
    fun getVipRights(
    ) {
        launchFlow(
            request = {
                MainBusinessApiService.getVipRights()
            },
            dataResp = { data, time ->
                vipRightResult.value =
                    RequestResult(isSuccess = true, data = data)
            },
            error = {
                vipRightResult.value =
                    RequestResult(isSuccess = false)
            }
        )
    }

    /**
     * 兑换码兑换vip
     */
    fun exchangeVip(
        code: String
    ) {
        launchFlow(
            request = {
                MainBusinessApiService.exchangeVip(code)
            },
            dataResp = { data, time ->
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_EXCHANGE,
                    mutableMapOf(
                        TrackerKeys.REQUEST_CONTENT to "兑换成功:",
                    )
                )
                exChangeResult.value =
                    RequestResult(isSuccess = true, data = data)
            },
            error = {
                ("兑换失败：" + it.message ?: "").toast()
                exChangeResult.value =
                    RequestResult(isSuccess = false)
            }
        )
    }

}