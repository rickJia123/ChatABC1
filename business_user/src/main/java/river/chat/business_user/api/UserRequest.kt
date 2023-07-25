package river.chat.business_user.api

import android.service.carrier.CarrierMessagingService.ResultCallback
import androidx.lifecycle.MutableLiveData
import river.chat.business_user.user.RiverUserManager
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.storage.database.model.User
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.utils.longan.toastSystem
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

    //微信登录
    fun loginByWechat(
        code: String
    ) {
        launchFlow(
            request = {
                UserApiService.loginByWechat(code)
            },
            dataResp = {
                it?.let {
                    RiverUserManager.onLoginSuccess(it)
                    loginResult.value = RequestResult(isSuccess = true, data = it)
                }
            },
            error = {
                it.message?.toastSystem()
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

    /**
     * 注销账户
     */
    fun destroy(resultCallback: (result: RequestResult<Boolean>) -> Unit) {
        launchFlow(
            request = {
                UserApiService.destroy()
            },
            dataResp = {
                resultCallback.invoke(RequestResult(isSuccess = it ?: false, data = true))

            },
            error = {
                it.message?.toast()
                resultCallback.invoke(RequestResult<Boolean>().apply {
                    errorMsg = it.message ?: ""
                    isSuccess = false
                })
            }
        )

    }
}