package river.chat.businese_common.report

import android.util.Log
import com.umeng.analytics.MobclickAgent
import river.chat.common.BuildConfig
import river.chat.lib_core.utils.longan.application

/**
 * Created by beiyongChao on 2023/7/29
 * Description:
 */
object ReportManager {

    /**
     * 上报自定义事件
     */
    fun reportEvent(eventId: String, params: Map<String, String>) {
        if (BuildConfig.DEBUG) {
            Log.d("Tracker", "onEvent: eventId = $eventId, params = $params")
        }
        MobclickAgent.onEvent(application, eventId, params)
    }

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

    fun reportPageStart(viewName: String) {
        MobclickAgent.onPageStart(viewName)
    }

    fun reportPageEnd(viewName: String) {
        MobclickAgent.onPageEnd(viewName)
    }
}