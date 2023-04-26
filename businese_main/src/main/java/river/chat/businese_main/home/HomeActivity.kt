package river.chat.businese_main.home

import com.alibaba.android.arouter.facade.annotation.Route
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import river.chat.businese_common.constants.CommonVmEvents
import river.chat.businese_common.router.HomeRouterConstants
import river.chat.business_main.databinding.ActivityHomeBinding
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity


/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_MAIN)
class HomeActivity : BaseBindingViewModelActivity<ActivityHomeBinding, HomeViewModel>() {




    override fun initDataBinding(binding: ActivityHomeBinding) {
        super.initDataBinding(binding)
        binding.toolBar.setTitle("ChatEvery")


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