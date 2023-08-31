package river.chat.businese_main.api

import androidx.lifecycle.MutableLiveData
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
//                it.toString().toast()
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

}