package river.chat.businese_main.chat

import androidx.lifecycle.MutableLiveData
import river.chat.businese_main.api.MainBusinessApiService
import river.chat.businese_main.chat.hot.HotTipItemBean
import river.chat.businese_main.message.MessageHelper.CHAT_TIP_FAIL
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.storage.database.model.MessageStatus
import river.chat.lib_core.utils.exts.safeToInt
import river.chat.lib_core.utils.longan.toast

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
            dataResp = {
//                it.toString().toast()
                chatRequestResult.value =
                    RequestResult(isSuccess = true, data = MessageBean().apply {
                        this.content = it?.content
                        this.parentId = it?.id ?: 0
                        this.time = it?.time ?: 0
                        this.status = MessageStatus.COMPLETE
                    })
            },
            error = {
                it.message?.toast()
                chatRequestResult.value =
                    RequestResult(isSuccess = false, data = MessageBean().apply {
                        this.content = CHAT_TIP_FAIL
                        this.parentId = msgId.toLong() ?: 0
                        this.time = System.currentTimeMillis()
                        this.status = MessageStatus.FAIL
                    })
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
            dataResp = {
//                it.toString().toast()
                hotQuestionResult.value =
                    RequestResult(isSuccess = true, data = mutableListOf<HotTipItemBean>().apply {
                        it?.forEach { item ->
                            add(HotTipItemBean().apply {
                                this.hotQuestion =item
                            })
                        }
                    })
            },
            error = {
                it.message?.toast()
                hotQuestionResult.value =
                    RequestResult(isSuccess = false, data = mutableListOf())
            }
        )
    }

}