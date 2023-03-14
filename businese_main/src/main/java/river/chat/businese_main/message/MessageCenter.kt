package river.chat.businese_main.message

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import river.chat.businese_common.router.jump2Login
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageReceiveBean
import river.chat.lib_resource.model.MessageSource

/**
 * Created by beiyongChao on 2023/3/12
 * Description:
 */
object MessageCenter {
    var msgReceiver = MutableSharedFlow<MessageBean>()


    init {

    }

    /**
     * 发射收到的消息
     */
    fun postReceiveMsg(msg: MessageBean) {
        if (checkPermission(msg)) {
            GlobalScope.launch()
            {
                ("MessageCenter postReceiveMsg:" + msg.msg).log()
                msgReceiver
                    .emit(msg)
            }
        }
    }

    /**
     * 注册监听收到的消息
     */
    fun registerReceiveMsg(scope: CoroutineScope, msgCallback: (MessageReceiveBean) -> Unit = {}) {
        scope.launch {
            msgReceiver
                .filter { !it.msg.isNullOrEmpty() }
                .onEach {
                    onReceiveMsg(scope, it)
                }
                .onEach { }
                .flowOn(Dispatchers.Default)
                .collect {
                    ("MessageCenter registerReceiveMsg").log()
                    var msg = MessageReceiveBean().apply {
                        this.msg = it
                    }
                    msgCallback.invoke(msg)
                }
        }
    }

    /**
     * 收到消息后处理
     */
    private fun onReceiveMsg(scope: CoroutineScope, msg: MessageBean) {
        if (msg.source == MessageSource.FRE_SELF) {
            scope.launch {
                flow<MessageBean> {
                    delay(1000)
                    emit(msg)
                }
                    .collect {
                        postReceiveMsg(MessageHelper.buildAiReceiveMsg())
                    }


            }
        }
    }

    /**
     * 检查消息发送 权限
     */
    private fun checkPermission(msg: MessageBean): Boolean {
//        "先充钱啊 啊sir".toast()
//        jump2Login()
        return true
    }

}