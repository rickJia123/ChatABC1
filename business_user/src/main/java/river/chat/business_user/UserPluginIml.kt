package river.chat.business_user

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.router.UserPlugin
import river.chat.businese_common.router.UserRouterConstants
import river.chat.lib_core.utils.longan.toast

/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = UserRouterConstants.USER_PLUGIN)
class UserPluginIml : UserPlugin {
    override fun test() {
        "UserPluginIml test".toast()
    }

    override fun init(context: Context?) {

    }


}