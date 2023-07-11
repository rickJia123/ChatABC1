package river.chat.lib_core.config

import river.chat.lib_core.utils.exts.safeToInt
import river.chat.lib_core.utils.exts.safeToString

/**
 * Created by beiyongChao on 2023/3/6
 * Description: 配置管理器
 */
object ConfigManager {

    const val FLAG_TRUE = "FLAG_TRUE"
    const val FLAG_FALSE = "FLAG_FALSE"

    fun getAppConfig(key: String, default: String): String {
        return  ConfigBox.getConfig(key, default)
    }
    fun getAppConfig(key: String, default: Boolean): Boolean {
        return ConfigBox.getConfig(key, if (default) FLAG_TRUE else FLAG_FALSE) == FLAG_TRUE
    }

    fun getAppConfig(key: String, default: Int): Int {
        return ConfigBox.getConfig(key,default.safeToString()).safeToInt()
    }

    fun putAppConfig(key: String, value: Any): Long {
     if (value is String) {
            return ConfigBox.putConfig(key, value)
        } else if (value is Boolean) {
            return ConfigBox.putConfig(key, if (value) FLAG_TRUE else FLAG_FALSE)
        }  else if (value is Int) {
            return ConfigBox.putConfig(key, value.toString())
     } else {
            return ConfigBox.putConfig(key, value.toString())
        }
    }


}