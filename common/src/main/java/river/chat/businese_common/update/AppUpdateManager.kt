package river.chat.businese_common.update

import androidx.appcompat.app.AppCompatActivity
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.AppServerConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.config.ConfigManager.FLAG_FALSE
import river.chat.lib_core.config.ConfigManager.FLAG_TRUE

/**
 * Created by beiyongChao on 2023/6/9
 * Description:
 */
object AppUpdateManager {

    /**
     * 是否需要更新app
     */
    fun isNeedUpdate(): Boolean {
        //rick todo
//        return ConfigManager.getAppConfig(AppServerConfigKey.REQUEST_APP_UPDATE_TYPE, 0) == 1
        return true
    }

    /**
     * 是否需要强制更新app
     */
    fun isNeedForceUpdate(): Boolean {
//        return ConfigManager.getAppConfig(AppServerConfigKey.REQUEST_APP_UPDATE_TYPE, 0) == 2
        //rick todo
        return true
    }

    /**
     * 尝试隐私协议
     */
    fun showUpdateAppDialog(activity: AppCompatActivity) {
        if (isNeedUpdate()) {
            UpdateAppDialog.builder(activity).show()
            return
        }
        if (isNeedForceUpdate()) {
            UpdateAppDialog.builder(activity).config(true).show()
        }
    }
}