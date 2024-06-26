package river.chat.businese_common.report


/**
 * 事件名称
 */
object TrackerEventName {
    //页面加载
    const val PAGE_LOAD = "PAGE_LOAD"


    /**
     * 聊天页面
     */
    const val REQUEST_CHAT_SUCCESS = "REQUEST_CHAT_SUCCESS"
    const val REQUEST_CHAT_ERROR = "REQUEST_CHAT_ERROR"
    const val CLICK_CHAT = "CLICK_CHAT"


    //登录页面点击
    const val CLICK_LOGIN = "CLICK_LOGIN"
    const val REQUEST_LOGIN = "REQUEST_LOGIN"

    //支付页面点击
    const val CLICK_VIP = "CLICK_VIP"
    const val REQUEST_VIP = "REQUEST_VIP"
    const val REQUEST_EXCHANGE = "REQUEST_EXCHANGE"

    //设置页面点击
    const val CLICK_SETTING = "CLICK_SETTING"

    //分享
    const val LOAD_SHARE = "LOAD_SHARE"
    const val CLICK_SHARE = "CLICK_SHARE"
    /**
     * crash 上报
     */
    const val CRASH = "CRASH"

    //升级弹窗
    const val DIALOG_UPDATE = "DIALOG_UPDATE"

    //客服弹窗
    const val DIALOG_SERVICE = "DIALOG_SERVICE"

    //活动弹窗
    const val DIALOG_ACTIVITY = "DIALOG_ACTIVITY"

}


/**
 * vip开通页/兑换页面
 */
object VIPTracker {
    //兑换结果
    const val KEY_EXCHANGE_RESULT = "KEY_EXCHANGE_RESULT"

    //支付结果
    const val KEY_PAY_RESULT = "KEY_PAY_RESULT"

    //兑换码
    const val KEY_EXCHANGE_CONTENT = "KEY_EXCHANGE_CONTENT"

    //支付点击/选择的sku
    const val KEY_SKU_CHOOSE = "KEY_SKU_CHOOSE"

}


/**
 *  分享页
 */
object ShareTracker {
    //分享的问题
    const val KEY_ANSWER = "KEY_ANSWER"
    const val KEY_QUESTION = "KEY_QUESTION"

    //分享平台
    const val KEY_PLATFORM = "KEY_PLATFORM"
}


/**
 *  聊天页
 */
object ChatTracker {
    //操作动作
    const val CLICK_ACTION = "CLICK_ACTION"
}

/**
 * 参数key
 */
object TrackerKeys {
    const val LOAD_PAGE = "LOAD_PAGE"

    /**
     * 点击事件名称
     */
    const val CLICK_TYPE = "CLICK_TYPE"

    /**
     * 点击事件名称
     */
    const val CLICK_PAGE = "CLICK_PAGE"

    /**
     * 询问内容
     */
    const val REQUEST_CONTENT = "REQUEST_CONTENT"

    /**
     * 询问接口返回时间
     */
    const val REQUEST_TIME = "REQUEST_TIME"

    /**
     * 支付成功
     */
    const val PAY_ACTION = "PAY_ACTION"

    /**
     * crash
     */
    const val CRASH_MSG = "CRASH_MSG"
    const val CRASH_DEVICE = "CRASH_DEVICE"

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