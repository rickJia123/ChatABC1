package river.chat.businese_main.vip

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import river.chat.businese_common.config.ServiceConfigManager
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.constants.CommonVmEvents
import river.chat.businese_common.router.jump2Settings
import river.chat.businese_common.update.AppUpdateManager
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.vip.VipManager
import river.chat.business_main.R
import river.chat.business_main.databinding.ActivityHomeBinding
import river.chat.business_main.databinding.ActivityVipOpenBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity


/**
 * Created by beiyongChao on 2023/8/8
 * Description:vip 开通页
 */
@Route(path = HomeRouterConstants.VIP_OPEN)
class VipOpenActivity : BaseBindingViewModelActivity<ActivityVipOpenBinding, VipOpenViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initDataBinding(binding: ActivityVipOpenBinding) {
        super.initDataBinding(binding)
        binding.toolBar.setTitle("GPTEvery会员")
        viewModel.initRightsList()
    }

    override fun onEvent(eventId: Int) {
        when (eventId) {

        }
    }


    override fun createViewModel() = VipOpenViewModel()


}