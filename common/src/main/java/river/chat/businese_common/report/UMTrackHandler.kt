package river.chat.businese_common.report

import android.content.Context
import android.util.Log
import river.chat.common.BuildConfig
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.tracker.TrackHandler
import river.chat.lib_core.utils.longan.appVersionName
import river.chat.lib_core.utils.longan.deviceModel

/**
 * Created by beiyongChao on 2023/8/29
 * Description:
 */
class UMTrackHandler : TrackHandler {


    override fun onEvent(context: Context, eventId: String, params: MutableMap<String, String>) {

        params.apply {
            putAll(getCommonTrackerMap())
        }
        ReportManager.reportEvent(eventId, params)
    }

    private fun getCommonTrackerMap(): Map<String, String> {
        //rick todo
        var user = getPlugin<UserPlugin>().getUser()
        return mutableMapOf<String, String>().apply {
            "appVersion" to appVersionName
            "userToken" to user.token
            "userToken" to user.token
            //rick todo
//            "userVipType" to user.vipType
            "deviceType" to deviceModel
        }
    }
}