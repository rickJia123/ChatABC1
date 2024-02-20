package river.chat.lib_core.privacy

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.yl.lib.sentry.hook.PrivacySentry
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.utils.exts.ifEmptyOrBlank
import river.chat.lib_core.utils.exts.safeToString
import java.util.UUID

/**
 * Created by beiyongChao on 2023/6/9
 * Description:
 */
object PrivacyManager {


    //默认链接，第一次还没开始请求网络
    private var mDefaultPrivacyUrl = "http://www.bljy.cc/policy/policy.html"
    fun getPrivacyUrl(): String {
        return ServiceConfigBox.getConfig().appPolicyUrl.ifEmptyOrBlank(mDefaultPrivacyUrl)
    }

    /**
     * 是否同意隐私协议
     */
    fun isPrivacyAgree(): Boolean {
        return ConfigManager.getAppConfigBoolean(AppLocalConfigKey.PRIVACY_AGREE, false)
    }

    fun updatePrivacyAgree(isAgree: Boolean) {
        var result = ConfigManager.putAppConfig(AppLocalConfigKey.PRIVACY_AGREE, isAgree)
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

    fun getDeviceId(): String {
        var deviceId = ConfigManager.getAppConfig(AppLocalConfigKey.DEVICE_ID, "0")
        if (deviceId == "0") {
            deviceId = (UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE).safeToString()
            ConfigManager.putAppConfig(AppLocalConfigKey.DEVICE_ID, deviceId)
        }
        return deviceId ?: ""
    }

    fun getDeviceMode(): String {
        var deviceMode = ConfigManager.getAppConfig(AppLocalConfigKey.DEVICE_MODE, "")
        if (deviceMode.isNullOrEmpty()) {
            deviceMode = Build.MODEL
            ConfigManager.putAppConfig(AppLocalConfigKey.DEVICE_MODE, deviceMode)
        }
        return deviceMode ?: ""
    }

}