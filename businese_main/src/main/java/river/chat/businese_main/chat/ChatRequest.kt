package river.chat.businese_main.chat

import androidx.lifecycle.MutableLiveData
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.net.SSEClient
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys.REQUEST_CONTENT
import river.chat.businese_common.report.TrackerKeys.REQUEST_TIME
import river.chat.businese_main.api.MainBusinessApiService
import river.chat.businese_main.chat.hot.HotTipItemBean
import river.chat.lib_core.event.BaseActionEvent
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.database.AiPictureBean
import river.chat.lib_resource.model.database.MessageBean
import river.chat.lib_resource.model.database.MessageStatus


/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
class ChatRequest(viewModel: BaseViewModel) : BaseRequest(viewModel) {

    val chatRequestResult = MutableLiveData<RequestResult<MessageBean>>()

    val hotQuestionResult = MutableLiveData<RequestResult<MutableList<HotTipItemBean>>>()
    val pictureResult = MutableLiveData<RequestResult<AiPictureBean>>()


    /**
     * 获取GPT 答案
     */
    fun requestAi(content: String, msgId: String) {
        launchFlow(
            request = {
                MainBusinessApiService.requestAi(content, msgId)
            },
            dataResp = { data, time ->

                //rick todo
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_CHAT_SUCCESS,
                    mutableMapOf(
                        REQUEST_CONTENT to "GPT接口成功--问题:" + content + "     回答：" + data?.content,
                        REQUEST_TIME to "GPT接口成功--耗时:${time}ms",
                    )
                )



                chatRequestResult.value =
                    RequestResult(isSuccess = true, data = MessageBean().apply {
                        this.content = data?.content
                        this.parentId = data?.id ?: 0
                        this.time = data?.time ?: 0
                        this.status =
                            if (data?.failFlag == true) MessageStatus.FAIL_COMMON else MessageStatus.COMPLETE
                        this.failFlag = data?.failFlag
                        this.failMsg = data?.failMsg
                    })

                EventCenter.postEvent(BaseActionEvent().apply {
                    action = CommonEvent.SEND_MSG_SUCCESS
                })
            },
            error = {
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST_CHAT_SUCCESS,
                    mutableMapOf(
                        REQUEST_CONTENT to "GPT接口错误---问题:" + content + "    错误信息:${it.message}",
                    )
                )
//                chatRequestResult.value =
//                    RequestResult(isSuccess = false, data = MessageBean().apply {
//                        this.content = CHAT_TIP_FAIL
//                        this.parentId = msgId.toLong() ?: 0
//                        this.time = System.currentTimeMillis()
//                        this.status = MessageStatus.FAIL_COMMON
//                    }, errorMsg = it.message ?: "")

                //rick todo 模拟数据
                var data = MessageBean().apply {
                    this.content = "测试嘿嘿"
                    this.failFlag = true
                    this.parentId = msgId.toLong() ?: 0
                    this.failMsg = "您的对话权益已过期，请您充值后再继续使用哦～"
                }
                chatRequestResult.value =
                    RequestResult(isSuccess = true, data = MessageBean().apply {
                        this.content = data.content
                        this.parentId = data.parentId ?: 0
                        this.time = data.time ?: 0
                        this.status =
                            if (data.failFlag == true) MessageStatus.FAIL_COMMON else MessageStatus.COMPLETE
                        this.failFlag = data.failFlag
                        this.failMsg = data.failMsg
                    })
            }
        )
    }




    fun requestByFlow(content: String, msgId: String) {
        SSEClient.start(content, msgId)
    }

    fun requestPicture(content: String) {
        launchFlow(
            request = {
                MainBusinessApiService.requestPicture(content)
            },
            dataResp = { data, time ->
                pictureResult.value =
                    RequestResult(isSuccess = true, data =data)
            },
            error = {
                LogUtil.e("requestPicture error:${it.message}")
                pictureResult.value =
                    RequestResult(isSuccess = false, data = AiPictureBean())
            }
        )
    }


    /**
     * 获取热门问题
     */
    fun requestHotQuestion() {
        launchFlow(
            request = {
                MainBusinessApiService.requestHotQuestion()
            },
            dataResp = { data, time ->
//                it.toString().toast()
                hotQuestionResult.value =
                    RequestResult(isSuccess = true, data = mutableListOf<HotTipItemBean>().apply {
                        data?.forEach { item ->
                            add(HotTipItemBean().apply {
                                this.hotQuestion = item
                            })
                        }
                    })
            },
            error = {
                hotQuestionResult.value =
                    RequestResult(isSuccess = false, data = mutableListOf())
            }
        )
    }

}