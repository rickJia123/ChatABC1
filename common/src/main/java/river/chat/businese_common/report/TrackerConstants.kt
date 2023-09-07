package river.chat.businese_common.report


/**
 * 事件名称
 */
object TrackerEventName {
    //页面加载
    const val PAGE_LOAD = "PAGE_LOAD"

    //页面点击
    const val PAGE_CLICK = "PAGE_CLICK"


    /**
     * 聊天请求
     */
    const val REQUEST = "REQUEST"


    //登录页面点击
    const val CLICK_LOGIN = "CLICK_LOGIN"

    //支付页面点击
    const val CLICK_VIP = "CLICK_VIP"
    const val REQUEST_VIP = "REQUEST_VIP"

    //设置页面点击
    const val CLICK_SETTING = "CLICK_SETTING"

    //分享
    const val LOAD_SHARE = "LOAD_SHARE"
    const val CLICK_SHARE = "CLICK_SHARE"
}


/**
 * vip开通页/兑换页面
 */
object VIPTracker {
    const val KEY_EXCHANGE_RESULT = "兑换结果"
    const val KEY_PAY_RESULT = "支付结果"

    const val KEY_EXCHANGE_CONTENT = "兑换码"
    const val KEY_SKU_CHOOSE = "支付sku选择"

}


/**
 *  分享页
 */
object ShareTracker {
    const val KEY_QUESTION = "分享的问题"
    const val KEY_ANSWER = "分享的答案"
    const val KEY_PLATFORM = "分享的平台"



}

/**
 * 参数key
 */
object TrackerKeys {
    const val LOAD_PAGE = "加载页面："

    /**
     * 点击事件名称
     */
    const val CLICK_TYPE = "点击事件："

    /**
     * 点击事件名称
     */
    const val CLICK_PAGE = "点击所在页面："

    /**
     * 询问内容
     */
    const val REQUEST_CONTENT = "询问内容："

    /**
     * 询问接口返回时间
     */
    const val REQUEST_TIME = "询问接口时间："

    /**
     * 支付成功
     */
    const val PAY_ACTION = "PAY_ACTION"


}


object PageName {
    const val PAGE_HOME = "首页"
    const val PAGE_SETTING = "设置页"
    const val PAGE_PAY = "支付页"
    const val PAGE_LOGIN = "登录页"
    const val PAGE_SHARE = "分享弹窗"
}

object TrackerCommonConstants {
    var loadPages = mutableListOf<String>().apply {
        add(PageName.PAGE_HOME)
        add(PageName.PAGE_SETTING)
        add(PageName.PAGE_PAY)
        add(PageName.PAGE_LOGIN)
        add(PageName.PAGE_SHARE)
    }
}