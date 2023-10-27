package river.chat.businese_main.vip

import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.router.jump2VipOpen
import river.chat.business_main.R
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.event.BaseActionEvent
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.common.TimeUtils
import river.chat.lib_core.utils.exts.getString
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_resource.model.VipType

/**
 * Created by beiyongChao on 2023/8/2
 * Description:
 */
object VipManager {

    /**
     * 提示没有聊天权益的间隔时间，6小时
     */
    const val UPDATE_SHOW_OPEN_VIP = 3 * 60 * 60 * 1000L

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
        user.trialBalance = Math.max(0, (user.trialBalance ?: 0) - 1)
        userPlugin.updateUser(user)
        EventCenter.postEvent(BaseActionEvent().apply { action = CommonEvent.UPDATE_VIP })
    }

    /**
     * 是否是vip
     */
    fun isVip(): Boolean {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()

        return user.getVipType() == VipType.VIP.value
    }



    /**
     * 是否有聊天权益,没有执行对应动作
     */
    fun check2Vip(): Boolean {
        var user = getPlugin<UserPlugin>().getUser()
        var permission = isVip()

        //当前时间小于上次提示 小时，不提示更新
        if (System.currentTimeMillis() < ((ConfigManager.getAppConfigBean(
                AppLocalConfigKey.UPDATE_CHAT_LIMIT_TIME
            )?.updateTime
                ?: 0) + UPDATE_SHOW_OPEN_VIP)
        ) {
            R.string.vip_limit_tip.getString().toastSystem()
        } else {
            jump2VipPage()
            ConfigManager.putAppConfig(AppLocalConfigKey.UPDATE_CHAT_LIMIT_TIME, true)
        }
        return permission

    }


    fun getVipType() = getPlugin<UserPlugin>().getUser().getVipType()


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
            "剩余体验次数:${user.trialBalance}次"
        }
        return remainStr
    }
}