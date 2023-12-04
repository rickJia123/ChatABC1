package river.chat.businese_main.home

import androidx.lifecycle.lifecycleScope
import org.koin.android.ext.android.inject
import river.chat.businese_common.config.ServiceConfigManager
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.constants.CommonVmEvents
import river.chat.businese_common.update.AppUpdateManager
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.vip.VipManager
import river.chat.business_main.databinding.FragmentHomeBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment

class HomeFragment : BaseBindingViewModelFragment<FragmentHomeBinding, HomeViewModel>() {
    val userPlugin: UserPlugin by inject()

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }


    override fun initDataBinding(binding: FragmentHomeBinding) {
        super.initDataBinding(binding)

//        binding.toolBar.setTitle("GPTEvery")
//        binding.toolBar.rightClick = {
//            jump2Settings()
//        }
//        binding.toolBar.leftClick = {
//            VipManager.jump2VipPage()
//        }


    }

    override fun createViewModel() = HomeViewModel()


    override fun onEvent(eventId: Int) {
        when (eventId) {
            CommonVmEvents.LOADING -> {
//                LoadingDialog().showDialog(this)
            }
        }
    }


}