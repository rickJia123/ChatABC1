package river.chat.businese_main.vip

import river.chat.businese_common.constants.CommonEvent
import river.chat.lib_core.event.BaseActionEvent
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin

/**
 * Created by beiyongChao on 2023/8/2
 * Description:
 */
object VipManager {


    /**
     * 试用期消费vip次数
     */
    fun onUseVipTimes() {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        user.remainTryTimes = Math.max(0, (user.remainTryTimes ?: 0) - 1)
        userPlugin.updateUser(user)
        EventCenter.postEvent(BaseActionEvent().apply { action = CommonEvent.UPDATE_VIP })
    }

    /**
     * 是否是vip
     */
    fun isVip(): Boolean {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        return (user.remainTryTimes > 0 && user.vipType == 1) || user.vipType == 2
    }

    private fun checkVipType(type: Int) {
        when (type) {
            0 -> {
                //普通用户
            }
            1 -> {
                //试用期

            }
            2 -> {
                //vip
            }
        }
    }
}