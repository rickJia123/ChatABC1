package river.chat.businese_common.router

import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.template.IProvider
import river.chat.lib_core.storage.database.model.User

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */
object UserRouterConstants {
    private const val GROUP_USER = "/user"
    const val USER_PLUGIN = "$GROUP_USER/plugin"

    const val LOGIN_HOME = "$GROUP_USER/loginHome"
}


interface UserPlugin : IProvider {

    fun launchLoginDialog(activity: AppCompatActivity)

    fun isLogin(): Boolean

    fun check2Login(isLogin: (Boolean) -> Unit = {})

    fun getUser():User
}