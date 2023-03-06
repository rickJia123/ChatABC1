package river.chat.lib_core.utils.common

import android.os.Handler
import android.os.Looper
import io.reactivex.schedulers.Schedulers

/**
 * Created by beiyongChao on 2023/3/6
 * Description:
 */

/**
 * 发送到主线程
 */
fun postMain(runnable: Runnable) {
    Handler(Looper.getMainLooper()).post(runnable)
}

/**
 * 发送到子线程
 */
fun postAsyn(runnable: Runnable) {
    Schedulers.io()
        .createWorker()
        .schedule(runnable)
}