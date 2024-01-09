package river.chat.businese_main.mine

import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.utils.onLoad
import river.chat.business_main.databinding.ActivitySettingsBinding
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_SETTINGS)
class SettingsActivity :
    BaseBindingViewModelActivity<ActivitySettingsBinding, SettingsViewModel>() {


    override fun initDataBinding(binding: ActivitySettingsBinding) {
        onLoad()
        super.initDataBinding(binding)
        binding.toolBar.setTitle("我的")
    }


    override fun createViewModel() = SettingsViewModel()

}