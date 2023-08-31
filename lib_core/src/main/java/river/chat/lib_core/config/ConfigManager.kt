package river.chat.lib_core.config

import river.chat.lib_core.utils.exts.safeToInt
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_resource.model.database.AppConfigBean

/**
 * Created by beiyongChao on 2023/3/6
 * Description: 配置管理器
 */
object ConfigManager {

    const val FLAG_TRUE = "FLAG_TRUE"
    const val FLAG_FALSE = "FLAG_FALSE"

    fun getAppConfig(key: String, default: String): String {
        return ConfigBox.getConfigValue(key, default)
    }

    fun getAppConfig(key: String, default: Boolean): Boolean {
        return ConfigBox.getConfigValue(key, if (default) FLAG_TRUE else FLAG_FALSE) == FLAG_TRUE
    }

    fun getAppConfig(key: String, default: Int): Int {
        return ConfigBox.getConfigValue(key, default.safeToString()).safeToInt()
    }

    fun getAppConfigBean(key: String): AppConfigBean? {
        return ConfigBox.getConfig(key)
    }

    fun putAppConfig(key: String, value: Any): Long {
        if (value is String) {
            return ConfigBox.putConfig(key, value)
        } else if (value is Boolean) {
            return ConfigBox.putConfig(key, if (value) FLAG_TRUE else FLAG_FALSE)
        } else if (value is Int) {
            return ConfigBox.putConfig(key, value.toString())
        } else if (value is Long) {
            return ConfigBox.putConfig(key, value.toString())
        } else {
            return ConfigBox.putConfig(key, value.toString())
        }
    }


}