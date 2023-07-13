package river.chat.businese_common.config

import river.chat.businese_common.net.CommonRequestViewModel
import river.chat.businese_common.update.AppUpdateManager
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.AppServerConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.utils.exts.safeToInt

/**
 * Created by beiyongChao on 2023/6/9
 * Description:服务端配置管理器
 */
object ServiceConfigManager {



    fun getPrivacyVersion(): Int {
        return ConfigManager.getAppConfig(AppLocalConfigKey.PRIVACY_VERSION, 1)
    }

    fun updatePrivacyVersion(version: Int) {
        ConfigManager.putAppConfig(AppLocalConfigKey.PRIVACY_VERSION, version)
    }

    /**
     * 加载配置信息
     */
    fun loadConfig() {
        CommonRequestViewModel().requestConfig(AppServerConfigKey.REQUEST_PRIVACY_VERSION) {
            if (it.isSuccess) {
                updatePrivacyVersion(it.data.safeToInt())
                onLoadConfig()
            }
        }
    }

    private fun onLoadConfig()
    {

    }


}