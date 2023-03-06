package river.chat.lib_core.config

import android.content.Context
import android.content.pm.PackageManager

/**
 * ================================================
 * 用于解析 AndroidManifest 中的 Meta 属性，用于组件化初始化的分发策略
 * 配合 [ConfigModule] 使用
 *
 * @author Scott
 * ================================================
 */
internal class ManifestParser(private val context: Context) {
    fun parse(): List<ConfigModule> {
        val modules: MutableList<ConfigModule> = ArrayList()
        try {
            val appInfo = context.packageManager
                    .getApplicationInfo(
                            context.packageName, PackageManager.GET_META_DATA)
            if (appInfo.metaData != null) {
                for (key in appInfo.metaData.keySet()) {
                    if (MODULE_VALUE == appInfo.metaData[key]) {
                        modules.add(parseModule(key))
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Unable to find metadata to parse ConfigModule", e)
        }
        return modules
    }

    /**
     * 解析module
     *
     * @param className class路径
     *
     * @return [ConfigModule]
     */
    private fun parseModule(className: String): ConfigModule {
        val clazz: Class<*> = try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("Unable to find ConfigModule implementation", e)
        }
        val module: Any = try {
            clazz.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException("Unable to instantiate ConfigModule implementation for $clazz",
                    e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Unable to instantiate ConfigModule implementation for $clazz",
                    e)
        }
        if (module !is ConfigModule) {
            throw RuntimeException("Expected instance of ConfigModule, but found: $module")
        }
        return module
    }

    companion object {
        private const val MODULE_VALUE = "ConfigModule"
    }
}