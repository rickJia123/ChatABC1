package river.chat.lib_core.router.plugin.module

import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.template.IProvider
import river.chat.lib_resource.model.database.User

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


    /**
     * 微信登录
     */
    fun loginByWechat(code: String)

    /**
     * 退出登录
     */
    fun logout()

    fun launchLoginDialog(activity: AppCompatActivity)

    fun isLogin(): Boolean

    /**
     * 检查是否登录
     */
    fun check2Login(isLogin: (Boolean) -> Unit = {})

    fun getUser(): User

    fun updateUser(user: User)

    /**
     * 注销账户
     */
    fun destroyAccount()

    /**
     * 刷新用户信息
     */
    fun refreshInfo()

}