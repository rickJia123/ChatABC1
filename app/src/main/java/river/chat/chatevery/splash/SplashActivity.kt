package river.chat.chatevery.splash

import river.chat.businese_common.router.jump2Main
import river.chat.businese_main.home.HomeActivity
import river.chat.chatevery.databinding.ActivitySplashBinding
import river.chat.lib_core.privacy.PrivacyManager
import river.chat.lib_core.utils.longan.deviceModel
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */

class SplashActivity : BaseBindingViewModelActivity<ActivitySplashBinding, SplashViewModel>() {


    override fun initDataBinding(binding: ActivitySplashBinding) {
        super.initDataBinding(binding)
        PrivacyManager.tryShowPrivacyDialog(this) {
            if (it) {

                binding.tvSplash.postDelayed({
                    jump2Main()
                    finish()
                }, 2000)
            } else {
                finish()
            }
        }


    }


    override fun createViewModel() = SplashViewModel()


}