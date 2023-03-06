package river.chat.lib_core.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import java.lang.reflect.Method

interface AppLifeCycles {
    /**
     * @param application 最初设计上没有这个参数，为了给插件化调用。如何插件化可参考360RePlugin
     * @param base        Context
     */
    fun attachBaseContext(application: Application, base: Context) {}

    //立即初始化
    fun onAppRequired(application: Application) {}

    //延迟初始化
    fun onCreate(application: Application) {}
    fun onTerminate(application: Application) {}
}

