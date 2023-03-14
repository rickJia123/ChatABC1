package river.chat.businese_main

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.router.HomePlugin
import river.chat.businese_common.router.HomeRouterConstants
import river.chat.lib_core.utils.longan.toast

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