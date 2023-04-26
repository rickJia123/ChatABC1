package river.chat.business_user.api

import retrofit2.http.Body
import retrofit2.http.POST
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApi
import river.chat.lib_core.storage.database.model.User

/**
 * Created by beiyongChao on 2023/2/20
 * Description:
 */
interface UserApi :BaseApi{

    /**
     * 获取验证码
     */
    @POST("/sms/sendLoginCode")
    suspend fun requestPhoneCode(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<Boolean>

    /**
     * 微信登录
     */
    @POST("/smart-orch/program/card/spearea")
    suspend fun loginByWechat(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<User>

    /**
     * 验证码登录
     */
    @POST("/user/login")
    suspend fun loginByPhone(
        @Body body: Map<String, @JvmSuppressWildcards Any?>
    ): BaseRequestBean<User>

}