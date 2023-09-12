package river.chat.businese_main.vip

import android.text.format.DateUtils
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.router.jump2VipExchange
import river.chat.businese_common.router.jump2VipOpen
import river.chat.lib_core.event.BaseActionEvent
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.common.TimeUtils
import river.chat.lib_resource.model.VipType

/**
 * Created by beiyongChao on 2023/8/2
 * Description:
 */
object VipManager {

    /**
     * 跳转开通/兑换页
     */
    fun jump2VipPage() {
//        jump2VipExchange()
        jump2VipOpen()
    }

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

        return user.vipType == VipType.VIP.value
    }

    fun getVipType() = getPlugin<UserPlugin>().getUser().vipType


    /**
     * 获取剩余权益：到期时间/免费体验次数
     */
    fun getRemainTimeStr(): String {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        var remainStr = ""
        remainStr += if (isVip()) {
            TimeUtils.getTimeStamp() + " 到期"
        } else {
            "剩余体验次数:${user.remainTryTimes}次"
        }
        return remainStr
    }
}