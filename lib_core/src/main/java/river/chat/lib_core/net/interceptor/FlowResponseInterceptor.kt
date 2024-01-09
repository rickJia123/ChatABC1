package river.chat.lib_core.net.interceptor


import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.ResponseBody
import org.json.JSONObject
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.exts.safeToLong
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageBean2
import river.chat.lib_resource.model.MessageBean_.failMsg
import river.chat.lib_resource.model.MessageFlowBean
import river.chat.lib_resource.model.MessageStatus
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


/**
 * Created by beiyongChao on 2023/11/27
 * Description: 请求头 拦截器
 */


class FlowResponseInterceptor : Interceptor {

    private var STATUS_SUCCESS = "ok"
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            e.message.toString()
            null
        }
        var homePlugin = getPlugin<HomePlugin>()
        response?.let {
            try {
                val inputStream = response.body?.byteStream()
                val reader = BufferedReader(InputStreamReader(inputStream))
                var str: String?
                LogUtil.i("receiveEventStream begin:")
                var msgList = MessageBean2()
                msgList.msgList = mutableListOf()
                var flowBean = MessageBean()
                var lastMsgBean = MessageBean()
                while (true) {
                    str = reader.readLine()
                    if (!str.isNullOrEmpty()) {
                        LogUtil.i("receiveEventStream allStr:" + str + ":" + str.contains("failFlag"))
                        when {
                            str.startsWith("event:") -> {
                                val jsonStr = str.substring(6).trim()
                                //处理数据

                                if (STATUS_SUCCESS == jsonStr) {
                                    flowBean.status = MessageStatus.COMPLETE
                                }
                            }

                            str.startsWith("id:") -> {
                                val jsonStr = str.substring(3).trim()
                                flowBean.id = jsonStr.safeToLong()
                            }

                            //失败就在data里返回老的格式
                            str.startsWith("data:") -> {
                                //失败消息
                                if (str.contains("failFlag")) {
                                    LogUtil.i("receiveEventStream 失败消息:" + str +failMsg.id)
                                    val jsonStr = str.substring(5).trim().replace("\"", "")
                                    var failMsg =
                                        GsonKits.fromJson(jsonStr.trim(), MessageBean::class.java)
                                    homePlugin.onMsgReceive(failMsg ?: MessageBean())
                                    //结束处理
                                    break
                                }
                                //成功消息
                                else {
                                    val jsonStr = str.substring(5).trim().replace("\"", "")
                                    LogUtil.i("receiveEventStream 成功消息:" + str +failMsg.id)
                                    flowBean.content = jsonStr


                                    //rick todo
                                    if (jsonStr.contains("[COMPLETE]")) {

                                        //结束处理
                                        break
                                    }
                                    //处理数据
                                }
                            }
                        }


                        //成功的处理
                        //如果已经获取了所有属性
                        if (flowBean.status != 0 && !flowBean.content.isNullOrEmpty() && flowBean.id > 0) {
                            var postbean = GsonKits.fromJson(
                                GsonKits.toJson(flowBean),
                                MessageBean::class.java
                            ) ?: MessageBean()
                            LogUtil.i(
                                "receiveEventStream data 结束:" + GsonKits.toJson(postbean) + "\n" + GsonKits.toJson(
                                    lastMsgBean
                                )
                            )

                            //如果成功的并且id和上次信息一致
                            if (lastMsgBean.status == MessageStatus.COMPLETE && (postbean.id == lastMsgBean.id)) {
                                postbean.content = lastMsgBean.content + postbean.content
                            }
                            homePlugin.onMsgReceive(postbean)
                            if ((postbean.id == lastMsgBean.id || lastMsgBean.id <= 0)) {
                                lastMsgBean = postbean
                            }
                            flowBean = MessageBean()
                        }
                    } else {

                    }

                }
//                msgList.msgList.add(flowBean)

            } catch (e: Exception) {
                e.printStackTrace()
                LogUtil.i("receiveEventStream 报错:" + e.message)
            }

        }
        return response ?: chain.proceed(request)
    }


}