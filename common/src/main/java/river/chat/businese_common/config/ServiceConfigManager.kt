package river.chat.businese_common.config

import river.chat.businese_common.net.CommonRequestViewModel
import river.chat.lib_core.config.AppSystemConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.config.ConfigManager.FLAG_FALSE
import river.chat.lib_core.config.ConfigManager.FLAG_TRUE
import river.chat.lib_core.utils.exts.safeToInt

/**
 * Created by beiyongChao on 2023/6/9
 * Description:服务端配置管理器
 */
object ServiceConfigManager {

    /**
     * 隐私协议版本
     */
    const val KEY_PRIVACY_VERSION = "KEY_PRIVACY_VERSION"


    fun getPrivacyVersion(): Int {
        return ConfigManager.getAppConfig(AppSystemConfigKey.PRIVACY_AGREE, 1)
    }

    fun updatePrivacyVersion(version: Int) {
        ConfigManager.putAppConfig(AppSystemConfigKey.PRIVACY_AGREE, version)
    }

    /**
     * 加载配置信息
     */
    fun loadConfig() {
        CommonRequestViewModel().requestConfig(KEY_PRIVACY_VERSION) {
            if (it.isSuccess) {
                updatePrivacyVersion(it.data.safeToInt())
            }
        }
    }


}