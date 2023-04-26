package river.chat.business_user

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.router.UserPlugin
import river.chat.businese_common.router.UserRouterConstants
import river.chat.businese_common.router.jump2Login
import river.chat.business_user.user.LoginCenter
import river.chat.business_user.user.RiverUserManager
import river.chat.lib_core.storage.database.model.User
import river.chat.lib_core.utils.longan.toast

/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = UserRouterConstants.USER_PLUGIN)
class UserPluginIml : UserPlugin {

    override fun launchLoginDialog(activity: AppCompatActivity) {
        LoginCenter.launchLoginDialog(activity)
    }



    override fun isLogin()=RiverUserManager.isLogin()

    override fun check2Login(isLogin: (Boolean) -> Unit) {
        if (RiverUserManager.isLogin()) {
            isLogin.invoke(true)
        } else {
            isLogin.invoke(false)
            jump2Login()
        }
    }

    override fun getUser()=RiverUserManager.getCurrentUser()


    override fun init(context: Context?) {

    }


}