package river.chat.businese_common.net

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import river.chat.lib_core.wx.WxAccessTokenBean
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApi
import river.chat.lib_resource.model.CreateOrderResBean
import river.chat.lib_resource.model.QueryOrderResBean

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
    suspend fun requestWechatAccessToken(
        @Query("appid") appid: String,
        @Query("secret") secret: String,
        @Query("code") code: String
    ): WxAccessTokenBean


    /**
     * 创建订单
     */
    @POST("/order/createEnergyOrder")
    suspend fun createPayOrder(
        @Body body: Map<String, @JvmSuppressWildcards Any?>
    ): BaseRequestBean<CreateOrderResBean>

    /**
     * 查询订单状态
     */
    @POST("/order/queryOrderPayInfo")
    suspend fun queryOrder(
        @Body body: Map<String, @JvmSuppressWildcards Any?>
    ): BaseRequestBean<QueryOrderResBean>


}