package river.chat.businese_common.config

import river.chat.businese_common.dataBase.AppUpdateConfigBox
import river.chat.businese_common.net.CommonRequestViewModel
import river.chat.lib_core.app.BaseApplication
import river.chat.lib_core.config.AppServerConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.utils.exts.view.preLoad
import river.chat.lib_resource.model.database.ServiceConfigBean

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
                onLoadConfig(it.data)
            }
        }
    }

    /**
     *  获取版本更新配置信息
     */
    private fun loadConfigConfig() {
        CommonRequestViewModel().requestAppUpdateConfig() {
            if (it.isSuccess) {
                it.data?.let {
                    AppUpdateConfigBox.updateConfig(it)
                }
            }
        }
    }

    private fun onLoadConfig(config: ServiceConfigBean?) {
        config?.let {
            ServiceConfigBox.updateConfig(it)
            preLoad(BaseApplication.getInstance(), it.appShareBg)
        }
    }


}