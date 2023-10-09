package river.chat.businese_common.config

import river.chat.businese_common.net.CommonRequestViewModel
import river.chat.lib_core.config.AppServerConfigKey
import river.chat.lib_core.config.ConfigManager

/**
 * Created by beiyongChao on 2023/6/9
 * Description:服务端配置管理器
 */
object ServiceConfigManager {


    fun loadAllConfig() {
        loadConfig()
        loadConfigConfig()
    }


    /**
     * 加载配置信息
     */
    fun loadConfig() {
        CommonRequestViewModel().requestConfig(AppServerConfigKey.REQUEST_PRIVACY_VERSION) {
            if (it.isSuccess) {
//                updatePrivacyVersion(it.data.safeToInt())
                onLoadConfig()
            }
        }
    }

    /**
     * 加载默认配置信息
     */
    fun loadConfigConfig() {
        CommonRequestViewModel().requestDefaultConfig() {
            if (it.isSuccess) {
                it.data?.let {
                    if (it.appUrl.isNotEmpty()) {
                        ConfigManager.putAppConfig(
                            AppServerConfigKey.REQUEST_APP_UPDATE_URL,
                            it.appUrl
                        )
                    }

                    ConfigManager.putAppConfig(
                        AppServerConfigKey.REQUEST_APP_UPDATE_TYPE,
                        if (it.isForce == 1) 2 else if (it.isRenew == 1) 1 else 0
                    )
                    ConfigManager.putAppConfig(
                        AppServerConfigKey.REQUEST_APP_UPDATE_CONTENT,
                        it.content
                    )
                }
                onLoadConfig()
            }
        }
    }

    private fun onLoadConfig() {

    }


    fun getPrivacyVersion(): Int {
        return ConfigManager.getAppConfig(AppServerConfigKey.REQUEST_PRIVACY_VERSION, 1)
    }

    fun updatePrivacyVersion(version: Int) {
        ConfigManager.putAppConfig(AppServerConfigKey.REQUEST_PRIVACY_VERSION, version)
    }


}