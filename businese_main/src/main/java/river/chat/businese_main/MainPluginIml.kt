package river.chat.businese_main

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants

/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_PLUGIN)
class MainPluginIml : HomePlugin {
    override fun test() {

    }

    override fun init(context: Context?) {

    }


}