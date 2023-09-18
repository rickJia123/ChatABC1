package river.chat.business_user.api

import androidx.lifecycle.MutableLiveData
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.report.ReportManager
import river.chat.business_user.constant.UserConstants
import river.chat.business_user.user.RiverUserManager
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_resource.model.database.User
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
            dataResp = { data, time ->
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_LOGIN,
                    mutableMapOf(
                        TrackerKeys.REQUEST_TIME to "获取验证码接口耗时:${time}ms",
                    )
                )
                requestCode.value = RequestResult(isSuccess = data ?: false, data = true)
            },
            error = {
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_LOGIN,
                    mutableMapOf(
                        TrackerKeys.REQUEST_TIME to "获取验证码报错：" + (it.message ?: "")
                    )
                )
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
            dataResp = { data, time ->
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_LOGIN,
                    mutableMapOf(
                        TrackerKeys.REQUEST_TIME to "手机号登录接口耗时:${time}ms",
                    )
                )
                data?.let {
                    RiverUserManager.onLoginSuccess(it, UserConstants.LOGIN_PLATFORM_PHONE)
                    loginResult.value = RequestResult(isSuccess = true, data = it)
                }
            },
            error = {
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_LOGIN,
                    mutableMapOf(
                        TrackerKeys.REQUEST_TIME to "手机号登录接口报错：" + (it.message ?: "")
                    )
                )
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
            dataResp = { data, time ->
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_LOGIN,
                    mutableMapOf(
                        TrackerKeys.REQUEST_TIME to "微信登录接口耗时:${time}ms",
                    )
                )
                data?.let {
                    RiverUserManager.onLoginSuccess(it, UserConstants.LOGIN_PLATFORM_WECHAT)
                    loginResult.value = RequestResult(isSuccess = true, data = it)
                }
            },
            error = {
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_LOGIN,
                    mutableMapOf(
                        TrackerKeys.REQUEST_TIME to "微信登录接口报错：" + (it.message ?: "")
                    )
                )
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
            dataResp = { data, time ->
                logoutResult.value = RequestResult(isSuccess = data ?: false, data = true)
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
            dataResp = { data, time ->
                resultCallback.invoke(RequestResult(isSuccess = data ?: false, data = true))

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
            dataResp = { data, time ->
                if (data != null) {
                    var user = data
                    user.token = getPlugin<UserPlugin>().getUser().token
                    RiverUserManager.updateUser(user)
                }
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_LOGIN,
                    mutableMapOf(
                        TrackerKeys.REQUEST_TIME to "刷新用户信息耗时:${time}ms",
                    )
                )
                resultCallback.invoke(RequestResult(isSuccess = true, data = data))
            },
            error = {
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_LOGIN,
                    mutableMapOf(
                        TrackerKeys.REQUEST_TIME to "刷新用户信息报错：" + (it.message ?: "")
                    )
                )
                it.message?.toast()
                resultCallback.invoke(RequestResult<User>().apply {
                    errorMsg = it.message ?: ""
                    isSuccess = false
                })
            }
        )
    }
}