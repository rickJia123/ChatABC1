package river.chat.businese_main.config

import android.app.Application
import android.content.Context
import river.chat.lib_core.app.AppLifeCycles
import river.chat.lib_core.utils.exts.isMainProcess

class MainLifeCyclesImpl : AppLifeCycles {
    override fun attachBaseContext(application: Application, base: Context) {}
    override fun onAppRequired(application: Application) {}
    override fun onCreate(application: Application) {
        if (application.isMainProcess()) {

        }
    }

    override fun onTerminate(application: Application) {

    }
}