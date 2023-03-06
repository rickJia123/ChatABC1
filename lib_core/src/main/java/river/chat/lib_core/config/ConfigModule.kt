package river.chat.lib_core.config

import android.content.Context
import river.chat.lib_core.app.AppLifeCycles


/**
 * ================================================
 * [ConfigModule] 可以给框架配置一些参数,需要实现 [ConfigModule] 后,在 AndroidManifest 中声明该实现类
 * ================================================
 */
interface ConfigModule {
    /**
     * 使用[GlobalConfigModule.Builder]给框架配置一些配置参数
     */
    fun applyOptions(context: Context)


    /**
     * 使用[AppLifeCycles]在Application的生命周期中注入一些操作
     */
    fun injectAppLifecycle(
        context: Context,
        lifeCycles: MutableList<AppLifeCycles>
    )

}