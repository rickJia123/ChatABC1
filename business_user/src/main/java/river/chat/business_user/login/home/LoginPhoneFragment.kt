package river.chat.business_user.login.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import river.chat.business_user.databinding.FragmentLoginPhoneBinding
import river.chat.business_user.login.LoginStatus
import river.chat.business_user.login.LoginViewModel
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.isPhone
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.dialog.BaseBindingDialogViewModelFragment

class LoginPhoneFragment :
    BaseBindingDialogViewModelFragment<FragmentLoginPhoneBinding, LoginViewModel>() {


    private var loginStatus = LoginStatus.PHONE_READY

    @SuppressLint("ClickableViewAccessibility")
    override fun initDataBinding(binding: FragmentLoginPhoneBinding) {
        super.initDataBinding(binding)

        initClick(binding)

        observeRequest()
    }


    private fun observeRequest() {
        viewModel.request.requestCode.observe(this) {
            if (it.isSuccess) {
                loginStatus = LoginStatus.CODE_READY
                mBinding?.tvConfirm?.text = "登录"
//                start2CodeAnim()
            }
        }
        viewModel.request.loginResult.observe(this) {
            if (it.isSuccess) {
                "登录成功".toast()
                mActivity?.finish()
            }
        }
    }

    private fun initClick(binding: FragmentLoginPhoneBinding) {
        binding.tvGetCOde.singleClick {
            checkPhoneNum {
                viewModel.request.requestPhoneCode(getPhoneNum())
            }
        }
        binding.tvConfirm.singleClick {
            checkCode {
                viewModel.request.loginByPhone("0", getPhoneNum(), getCode())
            }
        }
    }

    override fun createViewModel() = getActivityScopeViewModel(LoginViewModel::class.java)

    /**
     * 检测手机号有效性
     */
    private fun checkPhoneNum(callback: () -> Unit) {
        var isValidPhone = getPhoneNum().isPhone()
        if (!isValidPhone) {
            "请输入正确的手机号".toast()
        }
        callback.invoke()

    }

    /**
     * 检测验证码是否唯恐
     */
    private fun checkCode(callback: () -> Unit) {
        var isValidCode = getCode().isNullOrBlank()
        if (!isValidCode) {
            "请输入验证码".toast()
        }
        callback.invoke()
    }
//
//    private fun start2CodeAnim() {
//        mBinding?.inputLayoutPhone?.animate()
//            ?.translationX(mAnimDistance * -1f)?.start()
//        mBinding?.inputLayoutCode?.animate()
//            ?.translationX(mAnimDistance * -1f)?.start()
//        viewModel.updateLoginStatus(LoginStatus.CODE_READY)
//        mBinding?.btConfirm?.text = "验证码登录"
//    }
//
//    private fun start2HomeAnim() {
//        mBinding?.inputLayoutPhone?.animate()?.translationX(0f)?.start()
//        mBinding?.inputLayoutCode?.animate()?.translationX(0f)?.start()
//        viewModel.updateLoginStatus(LoginStatus.PHONE_READY)
//        mBinding?.btConfirm?.text = "获取验证码"
//    }

    fun showLogin(activity: AppCompatActivity) {
        show(activity.supportFragmentManager, "LoginFragment")
    }

    private fun getPhoneNum(): String {
        return mBinding?.inputPhone?.text?.toString() ?: ""
    }

    private fun getCode(): String {
        return mBinding?.inputCode?.text?.toString() ?: ""
    }

}