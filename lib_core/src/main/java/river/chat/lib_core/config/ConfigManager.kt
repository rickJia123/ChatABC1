package river.chat.lib_core.config

/**
 * Created by beiyongChao on 2023/3/6
 * Description: 配置管理器
 */
object ConfigManager {

    const val FLAG_TRUE = "FLAG_TRUE"
    const val FLAG_FALSE = "FLAG_FALSE"

    fun getAppConfig(key: String, default: Any): String {
        return if (default is String) {
            ConfigBox.getConfig(key, default)
        } else if (default is Boolean) {
            ConfigBox.getConfig(key, if (default) FLAG_TRUE else FLAG_FALSE)
        } else {
            ConfigBox.getConfig(key, default.toString())
        }

    }

    fun putAppConfig(key: String, value: Any): Long {
     if (value is String) {
            return ConfigBox.putConfig(key, value)
        } else if (value is Boolean) {
            return ConfigBox.putConfig(key, if (value) FLAG_TRUE else FLAG_FALSE)
        } else {
            return ConfigBox.putConfig(key, value.toString())
        }
    }


}