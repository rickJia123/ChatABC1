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

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.startup.Initializer

class AppInitializer : Initializer<Unit> {
    private var started = 0

    override fun create(context: Context) {
        "AppInitializer create".log()
        application = context as Application
        application.doOnActivityLifecycle(
            onActivityCreated = { activity, _ ->
                if (activity.javaClass.simpleName.contains("WXEntryActivity")) {
                    return@doOnActivityLifecycle
                } else
                    if (activity.javaClass.simpleName.contains("XhsShareActivity")) {
                    return@doOnActivityLifecycle
                }
                activityCache.add(activity as AppCompatActivity)
            },
            onActivityStarted = { activity ->
                started++
                if (started == 1) {
                    onAppStatusChangedListener?.onForeground(activity)
                }
            },
            onActivityStopped = { activity ->
                started--
                if (started == 0) {
                    onAppStatusChangedListener?.onBackground(activity)
                }
            },
            onActivityDestroyed = { activity ->
                activityCache.remove(activity)
            }
        )
    }

    override fun dependencies() = emptyList<Class<Initializer<*>>>()

    companion object {
        internal var onAppStatusChangedListener: OnAppStatusChangedListener? = null
    }
}
