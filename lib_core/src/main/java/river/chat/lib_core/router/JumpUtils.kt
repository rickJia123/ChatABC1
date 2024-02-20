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

/**
 * 意见反馈页
 */
fun jump2Feedback() {
    ARouter.getInstance().build(HomeRouterConstants.HOME_FEEDBACK)
        .navigation()
}

/**
 * vip 开通页
 */
fun jump2VipOpen() {
    ARouter.getInstance().build(HomeRouterConstants.VIP_OPEN)
        .navigation()
//    jump2PayResult()
}

/**
 * vip 兑换页
 */
fun jump2VipExchange() {
    ARouter.getInstance().build(HomeRouterConstants.VIP_EXCHANGE)
        .navigation()


}

fun jump2ShareApp() {
    ARouter.getInstance().build(HomeRouterConstants.SHARE_APP)
        .navigation()
}

fun jump2PayResult() {
    ARouter.getInstance().build(HomeRouterConstants.VIP_PAY_RESULT)
        .navigation()
}


/**
 * 跳转收藏
 */
fun jump2Collection() {
    ARouter.getInstance().build(HomeRouterConstants.HOME_COLLECTION)
        .navigation()
}

/**
 * 跳转收藏详情
 */
fun jump2CollectionDetail(questionId: Long) {
    ARouter.getInstance().build(HomeRouterConstants.HOME_COLLECTION_DETAIL)
        .withLong(HomeRouterConstants.Params.KEY_QUESTION_ID, questionId)
        .navigation()
}

/**
 * 跳转图片预览
 */
fun jump2PicturePreView(id: String) {
    ARouter.getInstance().build(HomeRouterConstants.HOME_PICTURE_PREVIEW)
        .withString(HomeRouterConstants.Params.PICTURE_PRELOAD_ID, id)
        .navigation()
}


/**
 * 跳转开发者工具
 */
fun jump2Develop() {
    ARouter.getInstance().build(HomeRouterConstants.HOME_DEVELOP)
        .navigation()
}
