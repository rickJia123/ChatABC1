package river.chat.business_user.login.home

import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import river.chat.businese_common.utils.onLoad
import river.chat.business_user.R
import river.chat.business_user.databinding.ActivityLoginBinding
import river.chat.business_user.login.LoginPage
import river.chat.business_user.login.LoginViewModel
import river.chat.lib_core.router.plugin.module.UserRouterConstants
import river.chat.lib_core.utils.exts.width
import river.chat.lib_core.utils.longan.screenWidth
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */
@Route(path = UserRouterConstants.LOGIN_HOME)
class LoginActivity : BaseBindingViewModelActivity<ActivityLoginBinding, LoginViewModel>() {


    private var mAnimDistance = 0
    override fun initDataBinding(binding: ActivityLoginBinding) {
        onLoad()
        super.initDataBinding(binding)
        mAnimDistance = screenWidth
        viewModel.loginPage.value = LoginPage.LOGIN_MAIN
        binding.fragmentMain.width(screenWidth)
        binding.fragmentPhone.width(screenWidth)
        binding.toolBar.setLeftImage(R.drawable.back_title_black)
        binding.toolBar.leftClick= {
            onBackPressed()
        }
        observeData(binding)
        ImmersionBar.with(this).statusBarDarkFont(true).navigationBarEnable(false).init()
    }


    private fun observeData(binding: ActivityLoginBinding) {
        viewModel.loginPage.observe(this) {
            when (it) {
                LoginPage.LOGIN_MAIN -> {
                    start2HomeAnim(binding)
                }
                LoginPage.LOGIN_PHONE -> {
                    start2CodeAnim(binding)
                }
            }
        }
    }

    override fun onBackPressed() {
        when (viewModel.loginPage.value) {
            LoginPage.LOGIN_MAIN -> {
                finish()
            }
            LoginPage.LOGIN_PHONE -> {
                viewModel.loginPage.value = LoginPage.LOGIN_MAIN
            }
        }
    }

    override fun createViewModel() = getActivityScopeViewModel(LoginViewModel::class.java)


    private fun start2CodeAnim(mBinding: ActivityLoginBinding) {
        mBinding.fragmentMain.animate()
            ?.translationX(mAnimDistance * -1f)?.start()
        mBinding.fragmentPhone.animate()
            ?.translationX(mAnimDistance * -1f)?.start()
    }

    private fun start2HomeAnim(mBinding: ActivityLoginBinding) {
        mBinding.fragmentMain.animate()?.translationX(0f)?.start()
        mBinding.fragmentPhone.animate()?.translationX(0f)?.start()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}