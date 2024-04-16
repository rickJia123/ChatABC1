package river.chat.businese_main.api

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.*
import river.chat.businese_common.net.ApiStorage.getBasedBody
import river.chat.businese_main.creation.CreationBeans
import river.chat.businese_main.creation.CreationItemsBeans
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApiService
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_resource.model.database.AiPictureBean
import river.chat.lib_resource.model.database.MessageBean
import river.chat.lib_resource.model.VipRightsBean
import river.chat.lib_resource.model.VipSkuBean
import java.io.BufferedReader
import java.io.InputStreamReader


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


    // 定义一个函数来处理 text/event-stream 数据
//    fun requestAiByFlow(url: String, callback: (String) -> Unit) {
    fun receiveEventStream(
        content: String,
        msgId: String, listener: (String) -> Unit
    ) {
        var url = " http://124.222.175.43:8000//chat/prompt/v101"
        var requestBody = getBasedBody().apply {
            this["content"] = content
            this["id"] = msgId
        }
        val mediaType = "application/json".toMediaTypeOrNull()
        val body: RequestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            GsonKits.toJson(requestBody) ?: ""
        )

        val request = Request.Builder()
            .url(url)
            .addHeader("Accept", "text/event-stream")
            .post(body)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                LogUtil.i("receiveEventStream fail:" + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val inputStream = response.body?.byteStream()
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    var str: String?
                    while (true) {
                        str = reader.readLine()
                        if (!str.isNullOrEmpty()) {
                            when {
                                str.startsWith("event:") -> {
                                    //处理数据
                                    LogUtil.i("receiveEventStream event:" + str)
                                }

                                str.startsWith("data:") -> {
                                    val jsonStr = str.substring(5).trim()
                                    LogUtil.i("receiveEventStream data:" + jsonStr)
                                    if (jsonStr.equals("[DONE]")) {
                                        //结束处理
                                        break
                                    }
                                    //处理数据
                                }
                            }
                        }
                    }
                } else {
                    response.close()
                }
            }
        })
    }


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
     * AI 绘图
     */
    suspend fun requestPicture(
        content: String,
        id: String
    ): BaseRequestBean<AiPictureBean> =
        mainBusinessApi.requestPicture(
            getBasedBody().apply {
                this["content"] = content
                this["id"] = id
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

    /**
     * 获取充值Sku
     */
    suspend fun getPaySku(
    ): BaseRequestBean<MutableList<VipSkuBean>> =
        mainBusinessApi.getPaySku(
            getBasedBody().apply {
            }
        )

    /**
     * 获取权益列表
     */
    suspend fun getVipRights(
    ): BaseRequestBean<VipRightsBean> =
        mainBusinessApi.getVipRights(
            getBasedBody().apply {
            }
        )

    /**
     * 兑换码兑换vip
     */
    suspend fun exchangeVip(
        code: String
    ): BaseRequestBean<Boolean> =
        mainBusinessApi.exchangeVip(
            getBasedBody().apply {
                this["code"] = code
            }
        )

    /**
     * 模板列表
     */
    suspend fun modelList(
    ): BaseRequestBean<MutableList<CreationBeans>> =
        mainBusinessApi.modelList(
            getBasedBody().apply {
            }
        )
}