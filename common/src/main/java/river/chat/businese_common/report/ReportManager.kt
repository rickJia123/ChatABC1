package river.chat.businese_common.report

import android.util.Log
import com.umeng.analytics.MobclickAgent
import river.chat.common.BuildConfig
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.longan.appVersionName
import river.chat.lib_core.utils.longan.application
import river.chat.lib_core.utils.longan.deviceModel

/**
 * Created by beiyongChao on 2023/7/29
 * Description:
 */
object ReportManager {

    /**
     * 上报自定义事件
     */
    fun reportEvent(eventId: String?, params: MutableMap<String, String>) {
        var commonMap = getCommonTrackerMap()
        params.apply {
            putAll(commonMap)
        }
        if (BuildConfig.DEBUG) {
            Log.d("Tracker", "onEvent: eventId = $eventId, params = $params")
        }

        var limitParams= mutableMapOf<String, String>()
        params.forEach { (t, u) ->
            limitParams[t]=if (u.length>256) u.substring(0,256) else u
        }
        MobclickAgent.onEvent(application, eventId, limitParams)
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


    private fun getCommonTrackerMap(): MutableMap<String, String> {
        //rick todo
        var user = getPlugin<UserPlugin>().getUser()
        var commonMap = mutableMapOf<String, String>()
            .apply {
                put("appVersion", appVersionName)
                put(
                    "userMsg",
                    "昵称:" + user.nickName + "----vip类型:" + user.getVipType() + "----token:" + user.token
                )
                put("deviceType", deviceModel)
            }
        return commonMap
    }


}