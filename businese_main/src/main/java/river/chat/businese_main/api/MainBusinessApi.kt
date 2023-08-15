package river.chat.businese_main.api

import retrofit2.http.Body
import retrofit2.http.POST
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApi
import river.chat.lib_resource.model.MessageBean

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
     *获取热门问题
     */
    @POST("/chat/hotQuestion")
    suspend fun requestHotQuestion(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<MutableList<String>>

   /**
     *意见反馈
     */
    @POST("/feedback/createFeedback")
    suspend fun confirmFeedback(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<String>

}