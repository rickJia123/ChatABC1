package river.chat.businese_main.chat

import androidx.lifecycle.MutableLiveData
import river.chat.businese_common.net.SSEClient
import river.chat.businese_main.api.MainBusinessApiService
import river.chat.businese_main.chat.hot.HotTipItemBean
import river.chat.lib_core.net.request.BaseRequest
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.database.AiPictureBean
import river.chat.lib_resource.model.database.MessageBean


/**
 * Created by beiyongChao on 2023/3/21
 * Description:
 */
class ChatRequest(viewModel: BaseViewModel) : BaseRequest(viewModel) {

    val chatRequestResult = MutableLiveData<RequestResult<MessageBean>>()

    val hotQuestionResult = MutableLiveData<RequestResult<MutableList<HotTipItemBean>>>()
    val pictureResult = MutableLiveData<RequestResult<AiPictureBean>>()



    fun requestByFlow(content: String, msgId: String) {
        SSEClient.start(content, msgId)
    }

    fun requestPicture(content: String,  id: String) {
        launchFlow(
            request = {
                MainBusinessApiService.requestPicture(content,id)
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