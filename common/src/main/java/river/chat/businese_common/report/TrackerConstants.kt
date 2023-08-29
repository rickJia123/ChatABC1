package river.chat.businese_common.report

/**
 * 埋点常量
 */
object CommonTrackerEventId {

    /**
     * 页面展示
     */
    const val PAGE_LOAD = "ACTIVITY_LOAD"

    /**
     * 页面事件点击
     */
    const val PAGE_CLICK = "PAGE_CLICK"

    /**
     * 聊天请求
     */
    const val CHAT_REQUEST = "CHAT_REQUEST"

}

object CommonTrackerEventKeys {

    /**
     * 页面名称
     */
    const val PAGE_NAME = "PAGE_NAME"

    /**
     * 点击事件名称
     */
    const val CLICK_TYPE = "CLICK_TYPE"

    /**
     * 询问内容
     */
    const val REQUEST_CONTENT = "REQUEST_CONTENT"

    /**
     * 询问接口返回时间
     */
    const val REQUEST_TIME = "REQUEST_TIME"

}