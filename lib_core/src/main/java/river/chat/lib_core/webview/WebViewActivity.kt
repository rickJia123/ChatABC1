package river.chat.lib_core.webview

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import river.chat.lib_core.R
import river.chat.lib_core.databinding.ActivityWebviewBinding
import river.chat.lib_core.view.main.activity.BaseBindingActivity
import river.chat.lib_core.webview.common.AgentWebGroup
import river.chat.lib_core.webview.common.WebViewParams

/**
 * Created by beiyongChao on 2023/6/1
 * Description:
 */
@Route(path = AgentWebGroup.ACTIVITY_AGENT_WEB_COMMON)
class WebViewActivity : BaseBindingActivity<ActivityWebviewBinding>() {


    /**
     * 来源页面
     */
    @JvmField
    @Autowired(name = AgentWebGroup.PARAMS_WEB)
    var mWebParams: WebViewParams? = null



    var mWebFragment: AgentWebFragment? = null

    override fun initDataBinding(binding: ActivityWebviewBinding) {
        mWebParams?.run {
            var obj = ARouter.getInstance().build(AgentWebGroup.FRAGMENT_AGENT_WEB)
                .withSerializable(AgentWebGroup.PARAMS_WEB, this).navigation()

            if (obj != null && obj is AgentWebFragment) {
                supportFragmentManager
                    .beginTransaction()
                    .add(
                        R.id.container_framelayout,
                        obj.also { mWebFragment = it },
                        AgentWebFragment::class.java.name
                    )
                    .show(mWebFragment!!)
                    .commit()
            }
        }
    }
}