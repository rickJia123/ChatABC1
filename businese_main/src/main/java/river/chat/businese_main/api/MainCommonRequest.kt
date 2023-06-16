package river.chat.businese_main.api

import androidx.lifecycle.MutableLiveData
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.utils.longan.deviceInfos
import river.chat.lib_core.utils.longan.deviceModel
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel

/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
class MainCommonRequest(viewModel: BaseViewModel) : BaseRequest(viewModel) {

    val feedBackResult = MutableLiveData<RequestResult<String>>()


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
            dataResp = {
//                it.toString().toast()
                feedBackResult.value =
                    RequestResult(isSuccess = true, data = it)
            },
            error = {
                it.message?.toast()
                feedBackResult.value =
                    RequestResult(isSuccess = false, data = "")
            }
        )
    }

}