package river.chat.lib_core.utils.exts

import android.annotation.SuppressLint
import android.content.Context
import java.lang.reflect.Method

/**
 * Created by beiyongChao on 2023/3/6
 * Description:
 */
@SuppressLint("DiscouragedPrivateApi", "PrivateApi")
fun Context.isMainProcess(): Boolean {
    return this.packageName.equals(getCurrentProcessName(), ignoreCase = true)
}

@SuppressLint("DiscouragedPrivateApi", "PrivateApi")
fun Context.getCurrentProcessName(): String? {
    return try {
        val clazz = Class.forName("android.app.ActivityThread")
        val currentActivityThreadMethod: Method =
            clazz.getDeclaredMethod("currentActivityThread")
        currentActivityThreadMethod.isAccessible = true
        val tCurrentActivityThread: Any = currentActivityThreadMethod.invoke(null)
        val tGetProcessNameMethod: Method = clazz.getDeclaredMethod("getProcessName")
        tGetProcessNameMethod.isAccessible = true
        tGetProcessNameMethod.invoke(tCurrentActivityThread) as String
    } catch (e: Throwable) {
        null
    }
}