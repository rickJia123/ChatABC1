package river.chat.businese_common.net

import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel

class CommonRequestViewModel : BaseViewModel() {




    val request = CommonRequest(this)

    /**
     * 获取验证码
     */
    fun requestPhoneCode(number: String) {
        launchFlow(
            request = {
                UserApiService.requestPhoneCode(number)
            },
            dataResp = {
                requestCode.value = RequestResult(isSuccess = it ?: false, data = true)
            },
            error = {
                it.message?.toast()
                loginResult.value = RequestResult(errorMsg = it.message ?: "")
            }
        )

    }


}