package river.chat.chatevery

import com.alibaba.android.arouter.launcher.ARouter
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import river.chat.businese_common.base.dataBaseModule
import river.chat.lib_core.app.AppManager
import river.chat.lib_core.app.BaseApplication
import river.chat.lib_core.storage.file.StorageUtil
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.application
import river.chat.lib_core.utils.longan.isAppDebug

/**
 * Created by beiyongChao on 2023/3/1
 * Description:
 */
class App : BaseApplication() {

    val appModule = module {

    }

    override fun onCreate() {
        super.onCreate()
        application=this


        // 启动 koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            // 添加 appModule
            modules(dataBaseModule, appModule)
        }

    }


    /**
     * 退出程序
     */
    override fun exitApp() {
        try {
            AppManager.getInstance()
                .finishAllActivity()
            System.exit(0)
        } catch (e: Exception) {
        }
        super.exitApp()
    }
}