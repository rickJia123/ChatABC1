package river.chat.lib_core.privacy

import androidx.appcompat.app.AppCompatActivity
import com.yl.lib.sentry.hook.PrivacySentry
import river.chat.lib_core.config.AppSystemConfigKey
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
        return ConfigManager.getAppConfig(AppSystemConfigKey.PRIVACY_AGREE, FLAG_FALSE) == FLAG_TRUE
    }

    fun updatePrivacyAgree(isAgree: Boolean) {
        ConfigManager.putAppConfig(AppSystemConfigKey.PRIVACY_AGREE, isAgree)
        if (isAgree) {
            PrivacySentry.Privacy.updatePrivacyShow()
        }
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