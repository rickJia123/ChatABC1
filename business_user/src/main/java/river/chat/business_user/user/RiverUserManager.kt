package river.chat.business_user.user

import com.tencent.bugly.crashreport.CrashReport
import river.chat.businese_common.constants.CommonConstants
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.dataBase.UserBox
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.router.jump2Main
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.event.BaseActionEvent
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_resource.model.VipType
import river.chat.lib_resource.model.database.ServiceConfigBean
import river.chat.lib_resource.model.database.User

/**
 * Created by beiyongChao on 2023/3/15
 * Description:
 */
object RiverUserManager {

    /**
     * 是否登录
     */
    fun isLogin(): Boolean {
//        getCurrentUser().toString().logConfig()
        return (getCurrentUser().id ?: 0) > 0
    }

    /**
     * 登录成功
     */
    fun onLoginSuccess(user: User, platFrom: String) {
        "登录成功".toastSystem()
        EventCenter.postEvent(BaseActionEvent().apply { action = CommonEvent.LOGIN_CHANGE })
        jump2Main()
        updateUser(user)
        ReportManager.reportLogin(true, user.id.toString(), platFrom)
        getPlugin<UserPlugin>().refreshInfo()
    }

    /**
     * 退出成功
     */
    fun onLogoutSuccess() {
        EventCenter.postEvent(BaseActionEvent().apply { action = CommonEvent.LOGIN_CHANGE })
        UserBox.deleteAll()
    }


    /**
     * 更新用户信息
     */
    fun updateUser(user: User) {
        LogUtil.i("rick RiverUserManager updateUser:"+GsonKits.toJson(user))
        //赋值用户id给bugly
        CrashReport.setUserId(user?.id.safeToString())  //该用户本次启动后的异常日志用户ID都将是9527
        UserBox.deleteAll()
        UserBox.updateUser(user)
    }



    /**
     *
     */
    fun getCurrentUser() = getCurrentUserByDb()

    /**
     * 从数据库读取用户
     */
    private fun getCurrentUserByDb() = UserBox.getCurrentUser()



}