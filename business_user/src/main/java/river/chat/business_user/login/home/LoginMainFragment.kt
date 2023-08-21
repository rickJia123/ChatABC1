package river.chat.business_user.login.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import river.chat.businese_common.wx.WxManager
import river.chat.business_user.databinding.FragmentLoginMainBinding
import river.chat.business_user.login.LoginPage
import river.chat.business_user.login.LoginViewModel
import river.chat.business_user.user.LoginCenter
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.screenWidth
import river.chat.lib_core.utils.system.DisplayUtil
import river.chat.lib_core.view.main.dialog.BaseBindingDialogViewModelFragment

class LoginMainFragment :
    BaseBindingDialogViewModelFragment<FragmentLoginMainBinding, LoginViewModel>() {


    @SuppressLint("ClickableViewAccessibility")
    override fun initDataBinding(binding: FragmentLoginMainBinding) {
        super.initDataBinding(binding)

        initClick(binding)

        observeRequest()
    }


    private fun observeRequest() {

    }

    private fun initClick(binding: FragmentLoginMainBinding) {

        binding.ivWechat.singleClick {
            WxManager.getLoginCode()
        }
        binding.tvPhone.singleClick {
            viewModel.loginPage.value = LoginPage.LOGIN_PHONE
//            LoginCenter.launchLoginPhoneDialog(mActivity as AppCompatActivity)
        }
    }

    override fun createViewModel() = getActivityScopeViewModel(LoginViewModel::class.java)


    fun showLogin(activity: AppCompatActivity) {
        show(activity.supportFragmentManager, "LoginFragment")
    }


}