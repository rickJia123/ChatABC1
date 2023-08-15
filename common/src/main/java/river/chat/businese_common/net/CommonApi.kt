package river.chat.businese_common.net

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import river.chat.businese_common.wx.WxAccessTokenBean
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApi

/**
 * Created by beiyongChao on 2023/2/24
 * Description:
 */
interface CommonApi : BaseApi {

    /**
     *获取配置信息
     */
    @POST(CommonHttpUrl.SYSTEM_CONFIG)
    suspend fun requestConfig(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<ConfigResBean>

    /**
     *获取配置信息
     */
    @POST(CommonHttpUrl.SYSTEM_DEFAULT_CONFIG)
    suspend fun requestDefaultConfig(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<DefaultConfigResBean>

    /**
     *获取配置信息
     */
    @GET("https://api.weixin.qq.com/sns/oauth2/access_token")
    suspend fun requestWechatAccessToken(@Query("appid") appid: String, @Query("secret") secret: String, @Query("code") code: String): WxAccessTokenBean


}