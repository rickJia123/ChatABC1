package river.chat.businese_common.net

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import river.chat.businese_common.net.ApiStorage.getBasedBody
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.net.common.ApiConfig
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.common.GsonKits.toJson
import river.chat.lib_core.utils.exts.safeToLong
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.appVersionName
import river.chat.lib_resource.model.database.MessageBean
import river.chat.lib_resource.model.database.MessageBean_
import river.chat.lib_resource.model.database.MessageStatus
import java.util.concurrent.TimeUnit
import kotlin.collections.set


/**
 * Created by beiyongChao on 2024/1/10
 * Description:
 */
object SSEClient {
    private var STATUS_SUCCESS = "ok"
    private var userPlugin = getPlugin<UserPlugin>()
    private var homePlugin = getPlugin<HomePlugin>()


    var flowBean = MessageBean()
    var mFlowBeanMap = mutableMapOf<String, MessageBean>()

    //失败的消息id集合，敏感信息拿到后，本次不再继续
    private var failMsgIdList = mutableListOf<String>()

    fun start(content: String?, msgId: String) {
        val url = ApiConfig.getHost() + "chat/prompt/v101"
        val okHttpClient: OkHttpClient =
            OkHttpClient.Builder().connectTimeout(1, TimeUnit.DAYS).readTimeout(1, TimeUnit.DAYS)
                .build()
        var requestBody = getBasedBody().apply {
            this["content"] = content ?: ""
            this["id"] = msgId
        }

        val body: RequestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(), toJson(requestBody) ?: ""
        )
        val token: String = userPlugin.getUser().token
        val request: Request =
            Request.Builder().url(url).header("header_token", token).header("header_lng", "")
                .header("header_lat", "").header("header_source", AppLocalConfigKey.PLATFORM + "")
                .header("header_version", appVersionName).addHeader("Accept", "text/event-stream")
                .post(body).build()
        val createFactory = EventSources.createFactory(okHttpClient)

        clearMsg()
        failMsgIdList.remove(msgId)

        val eventSourceListener = object : EventSourceListener() {
            override fun onEvent(
                eventSource: EventSource, id: String?, type: String?, data: String
            ) {
                //事件接收
                LogUtil.e("sse 接收...", "建立sse连接..." + data + ":::" + id + ":::" + type)
                onReceiveFlowMsg(content, data, type ?: "", id ?: "")
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                super.onFailure(eventSource, t, response)
                LogUtil.e("sse 失败...", "建立sse连接..." + t?.message)
            }
        }
        createFactory.newEventSource(request, eventSourceListener)
    }


    private fun onReceiveFlowMsg(question: String?, str: String, type: String, id: String) {
        try {
            fun handleStatus() {
                if (STATUS_SUCCESS == type) {
                    flowBean.status = MessageStatus.COMPLETE
                }
            }

            /**
             * 处理回复内容
             */
            fun handleStrMsg() {
                //失败消息
                if (str.contains("failFlag")) {
                    var jsonStr = str.substring(2)
//                    jsonStr = jsonStr.substring(0, jsonStr.length - 1)
//                    jsonStr = jsonStr.trim().replace("\"", "")

                    var failMsg = GsonKits.fromJson(str, MessageBean::class.java)
                    LogUtil.i("receiveEventStream 失败消息:" + str + "::" + toJson(failMsg))
                    homePlugin.onMsgReceive(failMsg ?: MessageBean())
                    failMsgIdList.add(failMsg?.id.safeToString())
                    mFlowBeanMap[id] = MessageBean()
                }
                //成功消息
                else {
                    val jsonStr = str.trim().replace("\"", "").replace("\\\\n", "\n")
//                                    val jsonStr = str.substring(5).trim()
                    LogUtil.i("receiveEventStream 成功消息:" + str + MessageBean_.failMsg.id)
                    flowBean.content = jsonStr

                    if (jsonStr.contains("[COMPLETE]")) {
                        ReportManager.reportEvent(
                            TrackerEventName.REQUEST_CHAT_SUCCESS, mutableMapOf(
                                TrackerKeys.REQUEST_CONTENT to "GPT接口成功:" + question,
                            )
                        )
                        clearMsg()
                        mFlowBeanMap[id] = MessageBean()
                        homePlugin.onComplete()

                    }
                    //处理数据
                }
            }


            LogUtil.i("onReceiveFlowMsg allStr:$str:$type")
            flowBean.id = id.safeToLong()

            handleStatus()
            handleStrMsg()
            if (failMsgIdList.contains(flowBean.id.safeToString())) {
                LogUtil.i("onReceiveFlowMsg failMsgIdList contains:" + flowBean.id)
                return
            }
            if (!flowBean.content.isNullOrEmpty() && flowBean.id > 0) {
                var postBean = GsonKits.fromJson(
                    toJson(flowBean), MessageBean::class.java
                ) ?: MessageBean()

                var oriContent = postBean.content
                postBean.content =
                    (mFlowBeanMap[postBean.id.safeToString()]?.content ?: "") + oriContent
//                }
                LogUtil.i(
                    "receiveEventStream data 结束拼接:" + postBean.content
                )
                homePlugin.onMsgReceive(postBean)
                mFlowBeanMap[postBean.id.safeToString()] = postBean
            }
        } catch (e: Exception) {
            LogUtil.e("sse 接收异常", e.message)
            ReportManager.reportEvent(
                TrackerEventName.REQUEST_CHAT_ERROR, mutableMapOf(
                    TrackerKeys.REQUEST_CONTENT to "GPT接口错误--接收异常：" + (e.message ?: "")
                )
            )
        }
    }

    private fun clearMsg() {
        flowBean = MessageBean()
//        mFlowBeanMap = MessageBean()
    }


    private var eventSourceListener: EventSourceListener? = null


    //建立监听回调
    private var mSseConnectSucccesListener: SSEConnectSucccesListener? = null

    fun getSSEMessage(sseConnectSucccesListener: SSEConnectSucccesListener?) {
        mSseConnectSucccesListener = sseConnectSucccesListener
    }

    interface SSEConnectSucccesListener {
        fun getData(data: String?)
    }
}
