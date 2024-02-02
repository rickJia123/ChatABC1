package river.chat.business_user.login.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.utils.onReport
import river.chat.businese_common.utils.onLoad
import river.chat.business_user.databinding.FragmentLoginMainBinding
import river.chat.business_user.login.LoginPage
import river.chat.business_user.login.LoginViewModel
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.main.dialog.BaseBindingDialogViewModelFragment
import river.chat.lib_core.wx.WxManager

class LoginMainFragment :
    BaseBindingDialogViewModelFragment<FragmentLoginMainBinding, LoginViewModel>() {

    @SuppressLint("ClickableViewAccessibility")
    override fun initDataBinding(binding: FragmentLoginMainBinding) {
        onLoad()
        super.initDataBinding(binding)
        initClick(binding)

        observeRequest()
    }


    private fun observeRequest() {

    }

    private fun initClick(binding: FragmentLoginMainBinding) {

        binding.clLogin.singleClick {
            onReport(
                TrackerEventName.CLICK_LOGIN,
                TrackerKeys.CLICK_TYPE to "登录首页-微信登录"
            )
            binding.viewLoginPolicy.checkSelected {
                WxManager.getLoginCode()
            }
        }
        binding.tvPhone.singleClick {
            onReport(
                TrackerEventName.CLICK_LOGIN,
                TrackerKeys.CLICK_TYPE to "登录首页-点击验证码登录"
            )
            viewModel.loginPage.value = LoginPage.LOGIN_PHONE
//            LoginCenter.launchLoginPhoneDialog(mActivity as AppCompatActivity)
        }
    }

    override fun createViewModel() = getActivityScopeViewModel(LoginViewModel::class.java)


    fun showLogin(activity: AppCompatActivity) {
        show(activity.supportFragmentManager, "LoginFragment")
    }


}