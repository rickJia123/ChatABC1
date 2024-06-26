package river.chat.businese_main.api

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import river.chat.businese_main.creation.CreationBeans
import river.chat.businese_main.creation.CreationItemsBeans
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApi
import river.chat.lib_resource.model.database.AiPictureBean
import river.chat.lib_resource.model.database.MessageBean
import river.chat.lib_resource.model.database.MessageBean2
import river.chat.lib_resource.model.VipRightsBean
import river.chat.lib_resource.model.VipSkuBean

/**
 * Created by beiyongChao on 2023/2/20
 * Description:
 */
interface MainBusinessApi : BaseApi {

    /**
     * AI 对话
     */
    @POST("/chat/prompt")
    suspend fun requestAi(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<MessageBean>

    /**
     * AI 对话
     */
    @POST("/chat/prompt/v101")
    @Headers("Accept: text/event-stream")
    suspend fun requestAiByFlow(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<MessageBean2>


    /**
     *获取热门问题
     */
//    @Headers(NetCacheKey.Key_Cache_Type, NetCacheType.CACHE_NORMAL)
    @POST("/chat/hotQuestion")
    suspend fun requestHotQuestion(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<MutableList<String>>

    /**
     * AI 绘图
     */
    @POST("/image/prompt")
    suspend fun requestPicture(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<AiPictureBean>

    /**
     *意见反馈
     */
    @POST("/feedback/createFeedback")
    suspend fun confirmFeedback(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<String>

    /**
     *获取充值Sku
     */
    @POST("/recharge/energySkus")
    suspend fun getPaySku(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<MutableList<VipSkuBean>>


    /**
     *获取权益列表
     */
    @POST("/recharge/rightsDescr")
    suspend fun getVipRights(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<VipRightsBean>

    /**
     *兑换码兑换vip
     */
    @POST("/redeemCode/useCode")
    suspend fun exchangeVip(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<Boolean>

    /**
     * 模板列表
     */
    @POST("/template/list")
    suspend fun modelList(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<MutableList<CreationBeans>>

}