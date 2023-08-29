package river.chat.businese_common.utils

import river.chat.businese_common.constants.TrackerEventName
import river.chat.businese_common.constants.TrackerKeys
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.tracker.trackNode
import river.chat.lib_core.view.main.activity.BaseActivity
import river.chat.lib_core.view.main.fragment.BaseFragment

/**
 * Created by beiyongChao on 2023/8/25
 * Description:
 */

/**
 *
 */
fun <T : BaseActivity> T.getOfficalName(
): String {
    return (this::class.simpleName ?: "").let {
        if (it.contains("MainActivity")) {
            "首页"
        } else if (it.contains("SettingsActivity")) {
            "设置页"
        } else if (it.contains("PayActivity")) {
            "支付页"
        } else if (it.contains("LoginActivity")) {
            "登录页"
        } else if (it.contains("ShareActivity")) {
            "分享弹窗"
        }
        else {
            it
        }
    }
}
fun <T : BaseFragment> T.getOfficalName(
): String {
    return (this::class.simpleName ?: "").let {
         if (it.contains("ChatFragment"))
            "聊天页"
        else {
            it
        }
    }
}

/**
 * activity 曝光埋点
 */
fun <T : BaseActivity> T.onLoad(
) {
    this.trackNode = TrackNode(
        TrackerKeys.ACTIVITY_PAGE_NAME to this.getOfficalName()
    )
    this.postTrack(TrackerEventName.PAGE_LOAD)
}

/**
 * fragment 曝光埋点
 */
fun <T : BaseFragment> T.onLoad(
) {
    this.trackNode = TrackNode(
        TrackerKeys.FRAGMENT_PAGE_NAME to this.getOfficalName()
    )
    this.postTrack(TrackerEventName.PAGE_LOAD)
}