package river.chat.businese_common.net

import retrofit2.http.Body
import retrofit2.http.POST
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApi
import river.chat.lib_core.storage.database.model.MessageBean

/**
 * Created by beiyongChao on 2023/2/24
 * Description:
 */
interface CommonApi : BaseApi {

    /**
     *获取配置信息
     */
    @POST(CommonHttpUrl.SYSTEM_CONFIG)
    suspend fun requestConfig(@Body body: Map<String, @JvmSuppressWildcards Any?>): BaseRequestBean<String>


}