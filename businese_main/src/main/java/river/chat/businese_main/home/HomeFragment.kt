package river.chat.businese_main.home

import org.koin.android.ext.android.inject
import river.chat.businese_common.router.UserPlugin
import river.chat.businese_common.router.jump2Login
import river.chat.business_main.databinding.FragmentHomeBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.main.BaseBindingViewModelFragment

class HomeFragment : BaseBindingViewModelFragment<FragmentHomeBinding, HomeViewModel>() {
    val userPLaugin: UserPlugin by inject()

    override fun initDataBinding(binding: FragmentHomeBinding) {
        super.initDataBinding(binding)


        binding.tvHome.singleClick {
            userPLaugin.test()
        }
      binding.tvSetting.singleClick {
          jump2Login()
      }

    }

    override fun createViewModel()= HomeViewModel()

}