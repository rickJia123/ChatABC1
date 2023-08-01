package river.chat.business_user.constant

import river.chat.lib_core.event.BaseActionEvent


/**
 * Created by beiyongChao on 2023/6/2
 * Description:
 */
class UserEvent : BaseActionEvent() {


}

object LoginEventAction {
    const val ACTION_LOGIN_SUCCESS = "ACTION_LOGIN_SUCCESS"
    const val ACTION_LOGOUT_SUCCESS = "ACTION_LOGOUT_SUCCESS"
}

