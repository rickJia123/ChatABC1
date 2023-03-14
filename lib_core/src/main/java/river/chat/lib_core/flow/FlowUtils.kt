package river.chat.lib_core.flow

/**
 * Created by beiyongChao on 2023/3/12
 * Description:
 */
class FlowUtils {
//    private val pollingFlow = flow {
//        while (true) {
//            emit(serverApi.getPollingData())
//            delay(5000)
//        }
//    }.flowOn(Dispatchers.IO).retryWhen { cause, attempt ->
//        Log.d("polling flow ", "retryWhen cause $cause attempt $attempt")
//        delay(5000)
//        true
//    }.onEach {
//        Log.d("polling flow ", "onEach $it")
//    }
//
//
//
//
//    lifecycleScope.launchWhenResumed { pollingFlow.collect() }

}