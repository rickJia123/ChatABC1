package river.chat.businese_main.shareapp

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.umeng.socialize.bean.SHARE_MEDIA
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import river.chat.businese_common.constants.CommonVmEvents
import river.chat.business_main.databinding.ActivityShareappBinding
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity
import river.chat.lib_umeng.ShareManager
import river.chat.lib_umeng.common.RiverShareContent


/**
 * Created by beiyongChao on 2023/10/18
 * Description:
 */
@Route(path = HomeRouterConstants.SHARE_APP)
class ShareAppActivity :
    BaseBindingViewModelActivity<ActivityShareappBinding, ShareAppViewModel>() {

    var userPlugin = getPlugin<UserPlugin>()
//    private var mShareUrl = "https://www.pgyer.com/j1Yecs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initDataBinding(binding: ActivityShareappBinding) {
        super.initDataBinding(binding)
        binding.toolBar.setTitle("分享GPTEvery")

        binding.ivShare.singleClick {
            share()
        }
        binding.toolBar.leftClick = {
            finish()
        }
        binding.webView.loadUrl(ServiceConfigBox.getConfig().appDownUrl)
        initEventListener(binding)

    }


    private fun initEventListener(binding: ActivityShareappBinding) {

    }

    override fun onEvent(eventId: Int) {
        when (eventId) {
            CommonVmEvents.LOADING -> {
//                LoadingDialog().showDialog(this)
            }
        }
    }


    private fun share() {
        ShareManager.update(
            RiverShareContent().apply {
                mTitle = "GPTEvery--ChatGpt连接工具"
                mUrl = ServiceConfigBox.getConfig().appDownUrl
                mDescription="我发现了一个强大的ChatGpt工具,你也来试试吧"
            }, SHARE_MEDIA.WEIXIN
        ).shareUrl(topActivity)
    }

    override fun createViewModel() = ShareAppViewModel()

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String?) {
        // Do something
    }


}