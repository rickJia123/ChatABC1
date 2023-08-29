package river.chat.businese_common.constants

object TrackerEventName {
    //页面加载
    const val PAGE_LOAD = "PAGE_LOAD"
}

object TrackerKeys {
    const val ACTIVITY_PAGE_NAME = "加载页面(Activity)"
    const val FRAGMENT_PAGE_NAME = "加载页面(Fragment)"
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