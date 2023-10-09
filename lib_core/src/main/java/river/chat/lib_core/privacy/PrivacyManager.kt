package river.chat.lib_core.privacy

import androidx.appcompat.app.AppCompatActivity
import com.yl.lib.sentry.hook.PrivacySentry
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.ConfigManager

/**
 * Created by beiyongChao on 2023/6/9
 * Description:
 */
object PrivacyManager {

    /**
     * 是否同意隐私协议
     */
    fun isPrivacyAgree(): Boolean {
        return ConfigManager.getAppConfigBoolean(AppLocalConfigKey.PRIVACY_AGREE, false)
    }

    fun updatePrivacyAgree(isAgree: Boolean) {
       var result= ConfigManager.putAppConfig(AppLocalConfigKey.PRIVACY_AGREE, isAgree)
        result
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