package river.chat.businese_main.api

import androidx.lifecycle.MutableLiveData
import retrofit2.http.Body
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_main.creation.CreationBeans
import river.chat.businese_main.creation.CreationHelper
import river.chat.businese_main.creation.CreationItemsBeans
import river.chat.businese_main.manager.MainCommonHelper
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.utils.longan.deviceInfos
import river.chat.lib_core.utils.longan.toastSystem
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
    val modelList = MutableLiveData<RequestResult<MutableList<CreationBeans>>>()


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
                it.message?.toastSystem()
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
                MainCommonHelper.mSkuPayList = data
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
                MainCommonHelper.mVipRightsBean = data
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
                ("兑换失败：" + it.message ?: "").toastSystem()
                exChangeResult.value =
                    RequestResult(isSuccess = false)
            }
        )
    }

    /**
     * 模板列表
     */
    fun getModelList(
    ) {
        launchFlow(
            request = {
                MainBusinessApiService.modelList()
            },
            dataResp = { data, time ->
                if (data.isNullOrEmpty()) {
                    modelList.value =
                        RequestResult(isSuccess = false)
                } else {

//                    CreationHelper.saveSelectedModels(valeue)
                    var list = mutableListOf<CreationBeans>()
                    var templates = mutableListOf<CreationItemsBeans>()
                    var oriTemps = data[0].templates

                    repeat(20)
                    {
                        templates.addAll(oriTemps)
                    }

                    data.forEach {
                        it.apply {
                            this.templates = templates
                        }
                    }
                    list.addAll(data)
                    list.addAll(data)
                    list.addAll(data)
                    modelList.value =
                        RequestResult(isSuccess = true, data = list)
                }
            },
            error = {
                modelList.value =
                    RequestResult(isSuccess = false)
            }
        )
    }

}