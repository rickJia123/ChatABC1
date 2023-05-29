package river.chat.lib_core.utils.other

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * Created by beiyongchao on 2021/11/2.
 * Description:
 */
object CutdownUtils {

    /**
     * 基于flow的倒计时封装
     */
    @JvmStatic
    fun countDownCoroutines(
        total: Int,
        scope: CoroutineScope,
        onTick: (Int) -> Unit,
        onStart: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
        onCatch: (() -> Unit)? = null,
        delayTime: Long = 1000
    ): Job {
        return flow {
            for (i in total downTo 0) {
                emit(i)
                delay(delayTime)
            }
        }.flowOn(Dispatchers.Main)
            .onStart { onStart?.invoke() }
            .onCompletion { onFinish?.invoke() }
            .onCompletion { cause ->
//                if (cause != null)
                onCatch?.invoke()
//                else
//                    onFinish?.invoke()
            }
            .onEach { onTick.invoke(it) }
            .launchIn(scope)
    }

}