package river.chat.business_user.login.home

import com.alibaba.android.arouter.facade.annotation.Route
import org.koin.android.ext.android.inject
import river.chat.business_user.databinding.ActivityLoginBinding
import river.chat.business_user.login.LoginViewModel
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.UserRouterConstants
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */
@Route(path = UserRouterConstants.LOGIN_HOME)
class LoginActivity : BaseBindingViewModelActivity<ActivityLoginBinding, LoginViewModel>() {

    private val homePlugin: HomePlugin by inject()

    override fun initDataBinding(binding: ActivityLoginBinding) {
        super.initDataBinding(binding)

    }


    override fun createViewModel() = LoginViewModel()

}