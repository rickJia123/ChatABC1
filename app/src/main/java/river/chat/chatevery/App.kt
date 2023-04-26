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
        initRouter()
        initConfig()

        // 启动 koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            // 添加 appModule
            modules(dataBaseModule, appModule)
        }

    }

    private fun initRouter() {
        if (isAppDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug();  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }


    private fun initConfig() {
        application=this
        LogUtil.init(isAppDebug)
        StorageUtil.init(this)
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