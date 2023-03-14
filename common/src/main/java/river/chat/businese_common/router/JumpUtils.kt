package river.chat.businese_common.router

import com.alibaba.android.arouter.launcher.ARouter


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