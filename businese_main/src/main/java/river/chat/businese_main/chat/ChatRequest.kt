package river.chat.businese_main.chat

import androidx.lifecycle.MutableLiveData
import river.chat.businese_common.constants.TrackerEventName
import river.chat.businese_common.constants.TrackerKeys.REQUEST_CONTENT
import river.chat.businese_common.constants.TrackerKeys.REQUEST_TIME
import river.chat.businese_common.report.ReportManager
import river.chat.businese_main.api.MainBusinessApiService
import river.chat.businese_main.chat.hot.HotTipItemBean
import river.chat.businese_main.message.MessageHelper.CHAT_TIP_FAIL
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageStatus

/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
class ChatRequest(viewModel: BaseViewModel) : BaseRequest(viewModel) {

    val chatRequestResult = MutableLiveData<RequestResult<MessageBean>>()

    val hotQuestionResult = MutableLiveData<RequestResult<MutableList<HotTipItemBean>>>()


    /**
     * 获取GPT 答案
     */
    fun requestAi(content: String, msgId: String) {
        launchFlow(
            request = {
                MainBusinessApiService.requestAi(content, msgId)
            },
            dataResp = { data, time ->
                ReportManager.reportEvent(
                    TrackerEventName.REQUEST,
                    mapOf(
                        REQUEST_CONTENT to "GPT接口问题:" + content + "  \n回答：" + data?.content,
                        REQUEST_TIME to "GPT接口耗时:${time}ms",
                    )
                )
                chatRequestResult.value =
                    RequestResult(isSuccess = true, data = MessageBean().apply {
                        this.content = data?.content
                        this.parentId = data?.id ?: 0
                        this.time = data?.time ?: 0
                        this.status = MessageStatus.COMPLETE
                    })
            },
            error = {

                ReportManager.reportEvent(
                    TrackerEventName.REQUEST,
                    mapOf(
                        REQUEST_TIME to "GPT接口错误:${it.message}"
                    )
                )
                it.message?.toast()
                chatRequestResult.value =
                    RequestResult(isSuccess = false, data = MessageBean().apply {
                        this.content = CHAT_TIP_FAIL
                        this.parentId = msgId.toLong() ?: 0
                        this.time = System.currentTimeMillis()
                        this.status = MessageStatus.FAIL_COMMON
                    },errorMsg= it.message?:"")
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