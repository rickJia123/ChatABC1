package river.chat.lib_core.webview

import com.alibaba.android.arouter.launcher.ARouter
import river.chat.lib_core.webview.common.AgentWebGroup
import river.chat.lib_core.webview.common.WebViewParams

/**
 * Created by beiyongChao on 2023/6/2
 * Description:
 */
object WebViewHelper {

    //启动webViewActivity
    fun startWebViewActivity(url: String) {
        ARouter.getInstance().build(AgentWebGroup.ACTIVITY_AGENT_WEB_COMMON)
            .withSerializable(AgentWebGroup.PARAMS_WEB, WebViewParams(url))
            .navigation()
    }
}