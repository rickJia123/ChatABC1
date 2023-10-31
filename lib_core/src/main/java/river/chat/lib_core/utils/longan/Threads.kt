/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package river.chat.lib_core.utils.longan

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.threadName
import river.chat.lib_core.utils.log.LogUtil
import java.util.concurrent.Flow

val mainThreadHandler by lazy { Handler(Looper.getMainLooper()) }

val isMainThread: Boolean get() = Looper.myLooper() == Looper.getMainLooper()

fun mainThread(block: () -> Unit) {
    if (isMainThread) block() else mainThreadHandler.post(block)
}

fun mainThread(delayMillis: Long, block: () -> Unit) =
    mainThreadHandler.postDelayed(block, delayMillis)



/**
 * 如果在 onCompletion 之前调用 catch，只有 catch 中能收到异常信息，onCompletion 中将收不到异常信息
 * 如果在 onCompletion 之后调用 catch，onCompletion 和 catch 中都可以收到异常信息
 * 单独使用 onCompletion 时，如果出现异常，onCompletion 能捕获到异常。但它并不会 catch 这个异常，而是处理完后把异常抛出去。
 */

fun workThread(
    scope: CoroutineScope,
    block: () -> Unit,
    result: (Boolean) -> Unit,
    delayTime: Long = 0,
) {
    scope.launch(Dispatchers.Main) {
        flow {
            emit(1)
            delay(delayTime)
        }
            .onStart {
                ("workThread onStart" + Thread.currentThread().name).log()
                block.invoke()
            }
            .flowOn(Dispatchers.IO)
            .onCompletion { error ->
                if (error == null) {
                    result.invoke(true)
                }
                ("workThread onCompletion:" + error + "::" + Thread.currentThread().name).log()
            }
            .catch { error ->
                ("workThread catch:" + error + Thread.currentThread().name).log()
                result.invoke(false)
            }
            .collect()
    }
}

