package river.chat.business_user.api

import androidx.lifecycle.MutableLiveData
import river.chat.business_user.constant.UserConstants
import river.chat.business_user.user.RiverUserManager
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_resource.model.User
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
                    RiverUserManager.onLoginSuccess(it,UserConstants.LOGIN_PLATFORM_PHONE)
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
                    RiverUserManager.onLoginSuccess(it,UserConstants.LOGIN_PLATFORM_WECHAT)
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

    /**
     * 注销账户
     */
    fun refreshUserInfo(resultCallback: (result: RequestResult<User>) -> Unit) {
        launchFlow(
            request = {
                UserApiService.refreshUserInfo()
            },
            dataResp = {
                if (it != null) {
                    var user=it
                    user.token=getPlugin<UserPlugin>().getUser().token
                    RiverUserManager.updateUser(user)
                }
                resultCallback.invoke(RequestResult(isSuccess = true, data = it))
            },
            error = {
                it.message?.toast()
                resultCallback.invoke(RequestResult<User>().apply {
                    errorMsg = it.message ?: ""
                    isSuccess = false
                })
            }
        )
    }
}