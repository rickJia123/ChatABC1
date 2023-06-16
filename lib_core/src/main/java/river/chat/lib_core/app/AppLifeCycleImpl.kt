package river.chat.lib_core.app

import android.app.Application
import android.content.Context
import river.chat.lib_core.config.ConfigModule
import river.chat.lib_core.config.ManifestParser
import river.chat.lib_core.utils.exts.isMainProcess
import river.chat.lib_core.utils.longan.log


/**
 * ================================================
 * Copyright (c) 2021 All rights reserved
 * 描述：AppDelegate 可以代理 Application 的生命周期,在对应的生命周期,执行对应的逻辑,因为 Java 只能单继承
 * 所以当遇到某些三方库需要继承于它的 Application 的时候,就只有自定义 Application 并继承于三方库的 Application
 * 这时就不用再继承 BaseApplication,只用在自定义Application中对应的生命周期调用AppDelegate对应的方法
 * (Application一定要实现APP接口),框架就能照常运行
 * Author: Scott
 * ================================================
 */


class AppLifeCycleImpl(context: Context) : AppLifeCycles {
    companion object {

        private val mAppLifeCycles: MutableList<AppLifeCycles> = ArrayList()
        fun executeInit(): Boolean {
            return try {
                //进程处理
                if (!BaseApplication.getInstance().isMainProcess()) {
                    return true
                }

                mAppLifeCycles.forEach { it.onCreate(BaseApplication.getInstance()) }
                true
            } catch (e: Throwable) {
                e
                false
            }
        }
    }


    private val modules: List<ConfigModule> = ManifestParser(context).parse()

    init {
        //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法
        //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifeCycles) 存入 mAppLifeCycles 集合 (此时还未注册回调)
        modules.forEach {
            it.injectAppLifecycle(context, mAppLifeCycles)
        }
    }

    override fun attachBaseContext(application: Application, base: Context) {
        //遍历 mAppLifeCycles, 执行所有已注册的 AppLifeCycles 的 attachBaseContext() 方法 (框架外部, 开发者扩展的逻辑)
        mAppLifeCycles.forEach { it.attachBaseContext(application, base) }
    }

    override fun onCreate(application: Application) {

        //需要立即初始化的组件
        mAppLifeCycles.forEach { it.onAppRequired(application) }

        //执行框架外部, 开发者扩展的 ConfigModule#applyOptions 配置逻辑
        modules.forEach { it.applyOptions(application) }.run {
            executeInit()
        }
    }

    override fun onTerminate(application: Application) {
        mAppLifeCycles.forEach { it.onTerminate(application) }
    }
}