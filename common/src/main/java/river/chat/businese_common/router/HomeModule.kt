package river.chat.businese_common.router

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */

object HomeRouterConstants {
    private const val GROUP_HOME = "/home"
    const val HOME_PLUGIN = GROUP_HOME + "/plugin"

    const val HOME_MAIN = GROUP_HOME + "/main"
}


interface HomePlugin : IProvider {

    fun test()

}