package river.chat.businese_common.report

import androidx.appcompat.app.AppCompatActivity
import com.umeng.analytics.MobclickAgent
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.AppServerConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.config.ConfigManager.FLAG_FALSE
import river.chat.lib_core.config.ConfigManager.FLAG_TRUE

/**
 * Created by beiyongChao on 2023/7/29
 * Description:
 */
object ReportManager {


    /**
     * 上报登录注册
     * 上报到友盟
     */
    fun reportLogin(isLogin: Boolean, userId: String, thirdPlatform: String) {
        if (isLogin) {
            if (thirdPlatform.isNullOrEmpty()) {
                //当用户使用自有账号登录时，可以这样统计：
                MobclickAgent.onProfileSignIn(userId)
            } else {
                //当用户使用自有账号登录时，可以这样统计：
                MobclickAgent.onProfileSignIn(thirdPlatform, userId)
            }
        } else {
            //登出
            MobclickAgent.onProfileSignOff()
        }

    }
}