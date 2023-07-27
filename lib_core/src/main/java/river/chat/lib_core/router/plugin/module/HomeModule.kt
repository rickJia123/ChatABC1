package river.chat.lib_core.router.plugin.module

import com.alibaba.android.arouter.facade.template.IProvider
import river.chat.lib_core.constants.BaseConstants

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */

object HomeRouterConstants : BaseConstants(){
    private const val GROUP_HOME = "/home"
    const val HOME_PLUGIN = GROUP_HOME + "/plugin"

    /**
     * 首页
     */
    const val HOME_MAIN = GROUP_HOME + "/main"


    /**
     * 设置页
     */
    const val HOME_SETTINGS = GROUP_HOME + "/settings"

    /**
     * 意见反馈页
     */
    const val HOME_FEEDBACK = GROUP_HOME + "/feedback"
}


interface HomePlugin : IProvider {

    fun test()

}