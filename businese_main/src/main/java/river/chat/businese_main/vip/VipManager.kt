package river.chat.businese_main.vip

import river.chat.businese_common.config.ServiceConfigManager
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.router.jump2VipExchange
import river.chat.businese_common.router.jump2VipOpen
import river.chat.businese_main.constants.MainConstants
import river.chat.businese_main.manager.MainCommonHelper
import river.chat.lib_core.event.BaseActionEvent
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_resource.model.VipType

/**
 * Created by beiyongChao on 2023/8/2
 * Description:
 */
object VipManager {

    /**
     * 提示没有聊天权益的间隔时间，6小时
     */
    const val UPDATE_SHOW_OPEN_VIP = 0.5 * 60 * 60 * 1000L


    //本次打开app是否已经提示没有会期，第一次会推送到消息列表，第二次会弹窗
    private var mIsTipNoVip = false

    /**
     * 跳转开通/兑换页
     */
    fun jump2VipPage() {
        if (ServiceConfigManager.isNeedHideVip()) {
            if (!ServiceConfigManager.isNeedHideExchange()) {
                jump2VipExchange()
            }
        } else {
            jump2VipOpen()
        }

    }

    /**
     * 试用期消费vip次数
     */
    fun onUseVipTimes() {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        user.trialBalance = 0.coerceAtLeast((user.trialBalance ?: 0) - 1)
        userPlugin.updateUser(user)
        EventCenter.postEvent(BaseActionEvent().apply { action = CommonEvent.UPDATE_USER })
    }

    /**
     * 是否有权益（正式/体验）
     */
    fun hasRights(): Boolean {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()

        return user.getVipType() == VipType.VIP.value || (user.getVipType() == VipType.TRIAL.value)

    }

    /**
     * 是否有聊天权益,没有执行对应动作
     */
    fun checkRightsWithBusiness(callBack: (String) -> Unit): Boolean {
        var hasPermission = false
        var user = getPlugin<UserPlugin>()
        var isLogin = user.isLogin()
        if (!isLogin) {
            hasPermission = false
            getPlugin<UserPlugin>().check2Login { }
        } else {
            //没有权益
            if (!hasRights()) {
                //没有消息格式的提示
                if (!mIsTipNoVip) {
                    callBack.invoke(MainConstants.RIGHTS_MSG_TIP)
                    mIsTipNoVip = true
                } else {
                    callBack.invoke(MainConstants.RIGHTS_JUMP_OPEN)
                }
            } else {
                hasPermission = true
            }

        }
        return hasPermission
    }

    /**
     * 是否是正式vip
     */
    fun isOfficalVip(): Boolean {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()

        return user.getVipType() == VipType.VIP.value
    }


    /**
     * 是否有聊天权益,没有执行对应动作
     */
    fun check2Vip(): Boolean {
        var user = getPlugin<UserPlugin>().getUser()
        var permission = hasRights()
//        //当前时间小于上次提示时间，只提示，超过了就直接跳转开通
//        if (System.currentTimeMillis() < ((ConfigManager.getAppConfigBean(
//                AppLocalConfigKey.UPDATE_CHAT_LIMIT_TIME
//            )?.updateTime
//                ?: 0) + UPDATE_SHOW_OPEN_VIP)
//        ) {
//            R.string.vip_limit_tip.getString().toastSystem()
//        } else {
//            //rick todo 这里改成弹窗
//            jump2VipPage()
//            ConfigManager.putAppConfig(AppLocalConfigKey.UPDATE_CHAT_LIMIT_TIME, true)
//        }


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
        remainStr += when (user.rightsStatus) {
            VipType.VIP.value -> {
                user.rightsExpireTime + " 到期"
            }

            VipType.TRIAL.value -> {
                "体验还剩:${user.trialBalance}次"
            }

            VipType.VIP_TIMEOUT.value -> {
                "会员已过期"
            }

            VipType.TRIAL_END.value -> {
                "体验已用完"
            }

            VipType.TRIAL.value -> {
                "剩余体验次数:${user.trialBalance}次"
            }

            else -> {
                "开通会员畅享AI"
            }
        }
        return remainStr
    }
}