package river.chat.businese_main.home

import org.koin.android.ext.android.inject
import river.chat.businese_common.router.jump2Settings
import river.chat.business_main.databinding.FragmentHomeBinding
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment

class HomeFragment : BaseBindingViewModelFragment<FragmentHomeBinding, HomeViewModel>() {
    val userPlugin: UserPlugin by inject()

    override fun initDataBinding(binding: FragmentHomeBinding) {
        super.initDataBinding(binding)


        binding.tvHome.singleClick {

        }
        binding.tvSetting.singleClick {
            jump2Settings()
        }
    }

    override fun createViewModel() = HomeViewModel()

}