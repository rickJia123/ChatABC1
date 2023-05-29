package river.chat.businese_main.setting

import com.alibaba.android.arouter.facade.annotation.Route
import org.koin.android.ext.android.inject
import river.chat.businese_common.dataBase.MessageBox
import river.chat.business_main.databinding.ActivitySettingsBinding
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_SETTINGS)
class SettingsActivity :
    BaseBindingViewModelActivity<ActivitySettingsBinding, SettingsViewModel>() {

    private val homePlugin: HomePlugin by inject()

    override fun initDataBinding(binding: ActivitySettingsBinding) {
        super.initDataBinding(binding)

        var test = ""
        MessageBox.getMsgList().forEach {
            test += it.toString() + "\n"
        }
        binding.tvTest.text = test
    }


    override fun createViewModel() = SettingsViewModel()

}