package river.chat.businese_common.router

import com.alibaba.android.arouter.launcher.ARouter
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserRouterConstants


/**
 * Created by rick on 2018/11/13.
 */

fun jump2Login() {
    ARouter.getInstance().build(UserRouterConstants.LOGIN_HOME)
        .navigation()
}


///////////////////////////////////////////////////////////////////////////
// home 模块
///////////////////////////////////////////////////////////////////////////
fun jump2Main() {
    ARouter.getInstance().build(HomeRouterConstants.HOME_MAIN)
        .navigation()
}

fun jump2Settings() {
    ARouter.getInstance().build(HomeRouterConstants.HOME_SETTINGS)
        .navigation()
}

fun jump2Feedback() {
    ARouter.getInstance().build(HomeRouterConstants.HOME_FEEDBACK)
        .navigation()
}