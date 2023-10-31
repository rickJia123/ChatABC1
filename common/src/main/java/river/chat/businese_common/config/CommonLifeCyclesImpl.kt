package river.chat.businese_common.config

import android.app.Application
import android.content.Context
import kotlinx.coroutines.GlobalScope
import org.koin.android.ext.android.getKoinScope
import river.chat.lib_core.app.AppLifeCycles
import river.chat.lib_core.utils.exts.isMainProcess
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.workThread
import kotlin.concurrent.thread

class CommonLifeCyclesImpl : AppLifeCycles {
    override fun attachBaseContext(application: Application, base: Context) {}
    override fun onAppRequired(application: Application) {}
    override fun onCreate(application: Application) {
        if (application.isMainProcess()) {
         workThread(
                scope = GlobalScope,
                block = {
                    InitManager.initSdk(application)
                    InitManager.initBusiness(application)
                },
                result = {
                    if (it) {
                        "initSdk success".log()
                    } else {
                        "initSdk failed".log()
                    }
                }
            )
            InitManager.initSdk(application)
            InitManager.initBusiness(application)
        }
    }

    override fun onTerminate(application: Application) {
    }
}