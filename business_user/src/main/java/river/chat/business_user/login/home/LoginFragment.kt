package river.chat.business_user.login.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import river.chat.businese_common.wx.WxManager
import river.chat.business_user.databinding.FragmentLoginBinding
import river.chat.business_user.login.LoginStatus
import river.chat.business_user.login.LoginViewModel
import river.chat.lib_core.utils.exts.dp2px
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.width
import river.chat.lib_core.utils.longan.isPhone
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.dialog.BaseBindingDialogViewModelFragment

class LoginFragment : BaseBindingDialogViewModelFragment<FragmentLoginBinding, LoginViewModel>() {


    private var mAnimDistance = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun initDataBinding(binding: FragmentLoginBinding) {
        super.initDataBinding(binding)

        binding.scrollView.setOnTouchListener { view, motionEvent ->
            return@setOnTouchListener true
        }
        binding.ivLoginBg.post {
            mAnimDistance = binding.ivLoginBg.width
            binding.inputLayoutPhone.width(mAnimDistance - 40f.dp2px())
            binding.inputLayoutCode.width(mAnimDistance - 40f.dp2px())
        }
        initClick(binding)

        observeRequest()
    }


    private fun observeRequest() {
        viewModel.request.requestCode.observe(this) {
            if (it.isSuccess) {
                start2CodeAnim()
            }
        }
        viewModel.request.loginResult.observe(this) {
            if (it.isSuccess) {
                "登录成功".toast()
                mActivity?.finish()
            }
        }
    }

    private fun initClick(binding: FragmentLoginBinding) {
        binding.btConfirm.singleClick {
            if (viewModel.getLoginStatus().get() <= LoginStatus.PHONE_READY) {
                if (!getPhoneNum().isPhone()) {
                    "请输入正确的手机号".toast()
                    return@singleClick
                }
                viewModel.request.requestPhoneCode(getPhoneNum())
            } else {
                viewModel.request.loginByPhone("0", getPhoneNum(), getCode())
            }
        }
        binding.btAction.singleClick {
            start2HomeAnim()
        }
        binding.ivThirdWechat.singleClick {
            WxManager.getLoginCode()
        }
    }

    override fun createViewModel() = LoginViewModel()


    private fun start2CodeAnim() {
        mBinding?.inputLayoutPhone?.animate()
            ?.translationX(mAnimDistance * -1f)?.start()
        mBinding?.inputLayoutCode?.animate()
            ?.translationX(mAnimDistance * -1f)?.start()
        viewModel.updateLoginStatus(LoginStatus.CODE_READY)
        mBinding?.btConfirm?.text = "验证码登录"
    }

    private fun start2HomeAnim() {
        mBinding?.inputLayoutPhone?.animate()?.translationX(0f)?.start()
        mBinding?.inputLayoutCode?.animate()?.translationX(0f)?.start()
        viewModel.updateLoginStatus(LoginStatus.PHONE_READY)
        mBinding?.btConfirm?.text = "获取验证码"
    }

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