package river.chat.lib_core.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import river.chat.lib_core.utils.longan.log

/**
 * Created by beiyongChao on 2023/8/2
 * Description:
 */
object EventCenter {
    var eventReceiver = MutableSharedFlow<BaseActionEvent>()


    init {

    }


    /**
     * 分发事件
     */
      fun postEvent(event: BaseActionEvent) {
        GlobalScope.launch {
            ("EventCenter distributeMsg:" + event).log()
            eventReceiver.emit(event)
        }
    }

    /**
     * 注册监听收到分发过来的事件，目标页面注册即可接收
     */
    fun registerReceiveEvent(
        scope: CoroutineScope,
        msgCallback: (BaseActionEvent) -> Unit = {}
    ) {
        scope.launch {
            eventReceiver.filter {
                true //过滤条件
            }.flowOn(Dispatchers.Default)
                .collect {
                    ("EventCenter receiver:" + it).log()
                    it.apply(msgCallback)
                }
        }
    }


}




