package river.chat.business_user.api

import androidx.lifecycle.MutableLiveData
import river.chat.business_user.user.RiverUserManager
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.storage.database.model.User
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel

/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
class UserRequest(viewModel: BaseViewModel) : BaseRequest(viewModel) {

    val loginResult = MutableLiveData<RequestResult<User>>()

    val requestCode = MutableLiveData<RequestResult<Boolean>>()

    val logoutResult = MutableLiveData<RequestResult<Boolean>>()

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

    fun loginByPhone(inviteId: String, number: String, verifyCode: String) {
        launchFlow(
            request = {
                UserApiService.loginByPhone(inviteId, number, verifyCode)
            },
            dataResp = {
                it?.let {
                    RiverUserManager.onLoginSuccess(it)
                    loginResult.value = RequestResult(isSuccess = true, data = it)
                }
            },
            error = {
                it.message?.toast()
                loginResult.value = RequestResult(errorMsg = it.message ?: "")
            }
        )
    }

    /**
     * 退出登录
     */
    fun logout() {
        launchFlow(
            request = {
                UserApiService.logout()
            },
            dataResp = {
                logoutResult.value = RequestResult(isSuccess = it ?: false, data = true)
            },
            error = {
                it.message?.toast()
                logoutResult.value = RequestResult(errorMsg = it.message ?: "")
            }
        )

    }
}