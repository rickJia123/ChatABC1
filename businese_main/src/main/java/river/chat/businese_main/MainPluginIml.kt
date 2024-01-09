package river.chat.businese_main

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_main.message.MessageCenter
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageFlowBean

/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_PLUGIN)
class MainPluginIml : HomePlugin {
    override fun test() {

    }

    override fun onMsgReceive(msg: MessageBean) {
        MessageCenter.onReceiveMsg(msg)
    }

    override fun init(context: Context?) {

    }


}