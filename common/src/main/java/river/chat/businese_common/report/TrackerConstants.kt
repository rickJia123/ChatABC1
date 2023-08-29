package river.chat.businese_common.constants


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
    const val CHAT_REQUEST = "CHAT_REQUEST"
}

/**
 * 参数key
 */
object TrackerKeys {
    const val LOAD_PAGE= "加载页面："

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