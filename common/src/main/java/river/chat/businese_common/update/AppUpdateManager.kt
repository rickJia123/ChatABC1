package river.chat.businese_common.update

import androidx.appcompat.app.AppCompatActivity
import river.chat.businese_common.dataBase.AppUpdateConfigBox
import river.chat.businese_common.helper.TimeIntervalHelper
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.AppServerConfigKey
import river.chat.lib_core.config.ConfigManager

/**
 * Created by beiyongChao on 2023/6/9
 * Description:
 */
object AppUpdateManager {

    //0不更新 1建议更新 2强制更新
    const val UPDATE_NO = 0
    const val UPDATE_UPDATE = 1
    const val UPDATE_FORCE = 2

    /**
     * 更新弹窗最少间隔时间
     */
    const val UPDATE_SHOW_LIMIT_TIME = 24 * 60 * 60 * 1000L

    /**
     * 是否需要更新app
     */
    fun isNeedUpdate(): Boolean {
        return getUpdateType() == UPDATE_UPDATE || getUpdateType() == UPDATE_FORCE
    }

    /**
     * 是否需要强制更新app
     */
    private fun isNeedForceUpdate(): Boolean {
        return getUpdateType() == UPDATE_FORCE
    }

    /**
     * 尝试隐私协议
     * isFromSetting 是否来自设置页面，设置页面每次都弹，不受每日数量控制
     */
    fun showUpdateAppDialog(activity: AppCompatActivity, isFromSetting: Boolean = false) {
        if (!isNeedUpdate()) {
            return
        }
        if (isNeedForceUpdate()) {
            UpdateAppDialog.builder(activity).config(true).show()
        } else {
            //当前时间小于上次提示更新的时间+24小时，不提示更新
            if (!isFromSetting && TimeIntervalHelper.isUpdateLimit()
            ) {
                return
            }
            UpdateAppDialog.builder(activity).config(false).show()
        }
    }

    /**
     * 获取版本更新文案
     */
    fun getUpdateContent(): String {
//        return ConfigManager.getAppConfig(AppServerConfigKey.REQUEST_APP_UPDATE_CONTENT, "")
        return AppUpdateConfigBox.getConfig().content
    }

    /**
     * 获取版本更新类型
     */
    fun getUpdateType(): Int {
        var config = AppUpdateConfigBox.getConfig()
        return if (config.isForce == 1) {
            UPDATE_FORCE
        } else if (config.isRenew == 1) {
            UPDATE_UPDATE
        } else {
            UPDATE_NO
        }


    }


}

