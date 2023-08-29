package river.chat.businese_main.home

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import river.chat.businese_common.config.ServiceConfigManager
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.constants.CommonVmEvents
import river.chat.businese_common.router.jump2Settings
import river.chat.businese_common.router.jump2VipOpen
import river.chat.businese_common.update.AppUpdateManager
import river.chat.businese_common.utils.getOfficalName
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.vip.VipManager
import river.chat.business_main.R
import river.chat.business_main.databinding.ActivityHomeBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.trackNode
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity


/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_MAIN)
class HomeActivity : BaseBindingViewModelActivity<ActivityHomeBinding, HomeViewModel>() {

    var userPlugin = getPlugin<UserPlugin>()
    override fun onCreate(savedInstanceState: Bundle?) {
        this.trackNode = TrackNode(
           "home" to this.getOfficalName()
        )
        super.onCreate(savedInstanceState)
        initOnHomeActivity()
        //请求配置信息
        ServiceConfigManager.loadAllConfig()
        userPlugin.refreshInfo()

        AppUpdateManager.showUpdateAppDialog(this)
    }

    override fun initDataBinding(binding: ActivityHomeBinding) {
        super.initDataBinding(binding)
        binding.toolBar.setTitle("GPTEvery")
        binding.toolBar.rightClick = {
            jump2Settings()
        }
        binding.toolBar.leftClick = {
            jump2VipOpen()
        }
        initEventListener(binding)
    }

    /**
     * 主Activity 初始化
     */
    private fun initOnHomeActivity() {
        MessageCenter.registerMsgCenter(this)
    }

    private fun initEventListener(binding: ActivityHomeBinding) {
        EventCenter.registerReceiveEvent(lifecycleScope) {
            when (it.action) {
                CommonEvent.UPDATE_VIP -> {
                    binding.viewVipStatus.update()
                    if (VipManager.isVip()) {
                        binding.toolBar.setLeftImage(R.drawable.vip)
                    } else {
                        binding.toolBar.setLeftImage(R.drawable.vip_dis)
                    }
                }
            }
        }
    }

    override fun onEvent(eventId: Int) {
        when (eventId) {
            CommonVmEvents.LOADING -> {
//                LoadingDialog().showDialog(this)
            }
        }
    }


    override fun createViewModel() = HomeViewModel()

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String?) {
        // Do something
    }


}