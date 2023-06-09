package river.chat.businese_main.api

import river.chat.businese_common.net.ApiStorage.getBasedBody
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApiService
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.storage.database.model.User

/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
object MainBusinessApiService : BaseApiService() {

    /**
     * 获取主业务网络仓库
     */
    private val mainBusinessApi: MainBusinessApi
        get() = retrofit().create(MainBusinessApi::class.java)

    /**
     * 获取GPT 回答
     */
    suspend fun requestAi(
        content: String,
        msgId: String
    ): BaseRequestBean<MessageBean> =
        mainBusinessApi.requestAi(
            getBasedBody().apply {
                this["content"] = content
                this["id"] = msgId
            }
        )

    /**
     * 获取热门问题
     */
    suspend fun requestHotQuestion(
    ): BaseRequestBean<MutableList<String>> =
        mainBusinessApi.requestHotQuestion(
            getBasedBody().apply {
            }
        )

    /**
     * 意见反馈
     */
    suspend fun confirmFeedback(
        content: String,
        contact: String,
        deviceInfo: String
    ): BaseRequestBean<String> =
        mainBusinessApi.confirmFeedback(
            getBasedBody().apply {
                this["contact"] = contact
                this["content"] = content
                this["deviceInfo"] = deviceInfo
            }
        )

}