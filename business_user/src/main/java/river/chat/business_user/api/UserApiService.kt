package river.chat.business_user.api

import river.chat.businese_common.net.ApiStorage.getBasedBody
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApiService
import river.chat.lib_resource.model.User

/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
object UserApiService : BaseApiService() {

    /**
     * 获取全局网络仓库
     */
    private val userApi: UserApi
        get() = retrofit().create(UserApi::class.java)

    /**
     * 验证码登录
     */
    suspend fun loginByPhone(
        inviteId: String, mobile: String, verifyCode: String
    ): BaseRequestBean<User> = userApi.loginByPhone(getBasedBody().apply {
        this["inviteId"] = inviteId
        this["verifyCode"] = verifyCode
        this["mobile"] = mobile
    })

    /**
     * 微信登录
     */
    suspend fun loginByWechat(
        code: String
    ): BaseRequestBean<User> = userApi.loginByWechat(getBasedBody().apply {
        this["code"] = code
    })

    /**
     * 请求验证码
     */
    suspend fun requestPhoneCode(number: String): BaseRequestBean<Boolean> =
        userApi.requestPhoneCode(getBasedBody().apply {
            this["mobile"] = number
        })


    /**
     * 退出登录
     */
    suspend fun logout(): BaseRequestBean<Boolean> = userApi.logout(
        getBasedBody()
    )

    /**
     * 注销账户
     */
    suspend fun destroy(): BaseRequestBean<Boolean> = userApi.destroy(
        getBasedBody()
    )

}