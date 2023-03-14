package river.chat.businese_common.router

import com.alibaba.android.arouter.facade.template.IProvider

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

    fun test()

}