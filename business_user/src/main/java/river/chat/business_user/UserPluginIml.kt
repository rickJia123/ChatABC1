package river.chat.business_user

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import org.greenrobot.eventbus.EventBus
import river.chat.businese_common.dataBase.UserBox
import river.chat.businese_common.router.jump2Login
import river.chat.business_user.constant.LoginEventAction.ACTION_LOGOUT_SUCCESS
import river.chat.business_user.constant.UserEvent
import river.chat.business_user.login.LoginViewModel
import river.chat.business_user.user.LoginCenter
import river.chat.business_user.user.RiverUserManager
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.router.plugin.module.UserRouterConstants
import river.chat.lib_core.storage.database.model.User
import river.chat.lib_core.utils.longan.toast

/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = UserRouterConstants.USER_PLUGIN)
class UserPluginIml : UserPlugin {

    override fun logout() {
        RiverUserManager.onLogoutSuccess()
        jump2Login()
        EventBus.getDefault().post(UserEvent().apply {
            action = ACTION_LOGOUT_SUCCESS
        })
        "退出成功".toast()
    }

    override fun launchLoginDialog(activity: AppCompatActivity) {
        LoginCenter.launchLoginDialog(activity)
    }


    override fun isLogin() = RiverUserManager.isLogin()

    override fun check2Login(isLogin: (Boolean) -> Unit) {
        if (RiverUserManager.isLogin()) {
            isLogin.invoke(true)
        } else {
            isLogin.invoke(false)
            jump2Login()
        }
    }

    override fun getUser() = RiverUserManager.getCurrentUser()
    override fun updateUser(user: User) {
        RiverUserManager.updateUser(user)
    }


    override fun init(context: Context?) {
    }


}