package river.chat.lib_core.router.plugin.core;

import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter

fun <T> getPlugin(clazz: Class<T>): T {
    var plugin = ARouter.getInstance().navigation(clazz)
    if (plugin == null) {
        plugin = EmptyPluginProxy.newInstance(clazz)
    }
    return plugin
}

inline fun <reified T : IProvider> getPlugin(): T {
    var plugin = ARouter.getInstance().navigation(T::class.java)
    if (plugin == null) {
        plugin = EmptyPluginProxy.newInstance(T::class.java)
    }
    return plugin
}
