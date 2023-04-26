package river.chat.business_user.login.home

import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.router.UserRouterConstants
import river.chat.business_user.databinding.ActivityLoginBinding
import river.chat.business_user.login.LoginViewModel
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity
import river.chat.businese_common.router.HomePlugin
import org.koin.android.ext.android.inject

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