package river.chat.business_user.login.home

import com.alibaba.android.arouter.facade.annotation.Route
import org.koin.android.ext.android.inject
import river.chat.businese_common.wx.WxManager
import river.chat.business_user.databinding.ActivityLoginBinding
import river.chat.business_user.login.LoginViewModel
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.UserRouterConstants
import river.chat.lib_core.utils.longan.screenWidth
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */
@Route(path = UserRouterConstants.LOGIN_HOME)
class LoginActivity : BaseBindingViewModelActivity<ActivityLoginBinding, LoginViewModel>() {

    private val homePlugin: HomePlugin by inject()

    private var mAnimDistance = 0
    override fun initDataBinding(binding: ActivityLoginBinding) {
        super.initDataBinding(binding)
        mAnimDistance = screenWidth

//        binding.fragmentMain.postDelayed({
//            start2CodeAnim(binding)
//        }, 2000)
    }


    override fun createViewModel() = LoginViewModel()


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