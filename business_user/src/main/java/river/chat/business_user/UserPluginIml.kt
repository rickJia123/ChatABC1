package river.chat.business_user

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import org.greenrobot.eventbus.EventBus
import river.chat.businese_common.pay.PayCenter
import river.chat.businese_common.router.jump2Login
import river.chat.businese_common.ui.view.dialog.SimpleDialog
import river.chat.businese_common.ui.view.dialog.SimpleDialogConfig
import river.chat.business_user.constant.LoginEventAction.ACTION_LOGOUT_SUCCESS
import river.chat.business_user.constant.UserEvent
import river.chat.business_user.login.LoginViewModel
import river.chat.business_user.user.LoginCenter
import river.chat.business_user.user.RiverUserManager
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.router.plugin.module.UserRouterConstants
import river.chat.lib_resource.model.database.User
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.activity.BaseActivity

/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = UserRouterConstants.USER_PLUGIN)
class UserPluginIml : UserPlugin {
    override fun loginByWechat(code: String) {
        LoginViewModel().loginByWechat(code)
    }

    override fun logout() {
        RiverUserManager.onLogoutSuccess()
        jump2Login()
        EventBus.getDefault().post(UserEvent().apply {
            action = ACTION_LOGOUT_SUCCESS
        })
        "退出成功".toastSystem()
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

    override fun destroyAccount() {
        SimpleDialog.builder(topActivity as BaseActivity).config(SimpleDialogConfig()
            .apply {
                title = "注销账户"
                des = "注销账户后，所有数据将被清除\n是否继续？"
                leftButtonStr = "取消"
                rightButtonStr = "确定"
                rightClick = {
                    LoginViewModel().request.destroy { result ->
                        if (result.isSuccess) {
                            logout()
                            "注销成功".toastSystem()
                        }
                    }
                }
            }).show()
    }

    override fun refreshInfo() {
        if (isLogin()) {
            LoginViewModel().request.refreshUserInfo { result ->
                if (result.isSuccess) {
                  PayCenter.postRefreshVipStatus()
                }
            }
        }
    }


    override fun init(context: Context?) {
    }


}