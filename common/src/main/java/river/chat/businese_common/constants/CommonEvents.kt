package river.chat.businese_common.constants

object CommonEvent {

    /**
     * 登录状态改变
     */
    const val LOGIN_CHANGE = "LOGIN_CHANGE"

    /**
     * 全局更新VIP状态
     */
    const val UPDATE_USER = "UPDATE_VIP"

    //消息发送成功
    const val SEND_MSG_SUCCESS = "SEND_MSG_SUCCESS"

    //收起软键盘
    const val HIDE_SOFT_WINDOW = "HIDE_SOFT_WINDOW"

    //收藏状态变化
    const val COLLECTION_TOGGLE = "COLLECTION_TOGGLE"

    //输入框接受消息
    const val RECEIVE_MSG_INPUT = "RECEIVE_MSG_INPUT"


}


object ModelEvent {

    const val EVENT_SOFT_OPEN = "EVENT_SOFT_OPEN"

    const val EVENT_SOFT_CLOSE = "EVENT_SOFT_CLOSE"

}