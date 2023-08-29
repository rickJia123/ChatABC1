package river.chat.businese_common.router

import com.alibaba.android.arouter.launcher.ARouter
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserRouterConstants
import river.chat.lib_core.tracker.putReferrerTrackNode
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.activity.BaseActivity


/**
 * Created by rick on 2018/11/13.
 */

fun jump2Login() {
    ARouter.getInstance().build(UserRouterConstants.LOGIN_HOME)
        .putReferrerTrackNode(topActivity)
        .navigation()
}


///////////////////////////////////////////////////////////////////////////
// home 模块
///////////////////////////////////////////////////////////////////////////
fun jump2Main() {
    ARouter.getInstance().build(HomeRouterConstants.HOME_MAIN)
        .putReferrerTrackNode(topActivity)
        .navigation()
}

fun jump2Settings() {
    ARouter.getInstance().build(HomeRouterConstants.HOME_SETTINGS)
        .putReferrerTrackNode(topActivity)
        .navigation()
}

/**
 * 意见反馈页
 */
fun jump2Feedback() {
    ARouter.getInstance().build(HomeRouterConstants.HOME_FEEDBACK)
        .putReferrerTrackNode(topActivity)
        .navigation()
}

/**
 * vip 开通页
 */
fun jump2VipOpen() {
    ARouter.getInstance().build(HomeRouterConstants.VIP_OPEN)
        .putReferrerTrackNode(topActivity)
        .navigation()
}