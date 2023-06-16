package river.chat.lib_core.privacy

import androidx.appcompat.app.AppCompatActivity
import river.chat.lib_core.config.AppConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.config.ConfigManager.FLAG_FALSE
import river.chat.lib_core.config.ConfigManager.FLAG_TRUE

/**
 * Created by beiyongChao on 2023/6/9
 * Description:
 */
object PrivacyManager {

    /**
     * 是否同意隐私协议
     */
    fun isPrivacyAgree(): Boolean {
        return ConfigManager.getAppConfig(AppConfigKey.PRIVACY_AGREE, FLAG_FALSE) == FLAG_TRUE
    }

    fun updatePrivacyAgree(isAgree: Boolean) {
        ConfigManager.putAppConfig(AppConfigKey.PRIVACY_AGREE, isAgree)
    }

    /**
     * 尝试隐私协议
     */
    fun tryShowPrivacyDialog(activity: AppCompatActivity, privacyAgreeCallback: (Boolean) -> Unit) {
        if (isPrivacyAgree()) {
            privacyAgreeCallback.invoke(true)
            return
        }
        PrivacyAgreeDialog.builder(activity).show(privacyAgreeCallback)
    }
}