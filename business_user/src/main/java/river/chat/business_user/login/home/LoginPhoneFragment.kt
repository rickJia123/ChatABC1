package river.chat.business_user.login.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.utils.onReport
import river.chat.businese_common.utils.onLoad
import river.chat.business_user.R
import river.chat.business_user.databinding.FragmentLoginPhoneBinding
import river.chat.business_user.login.LoginStatus
import river.chat.business_user.login.LoginViewModel
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.isPhone
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.utils.other.CutdownUtils
import river.chat.lib_core.view.main.dialog.BaseBindingDialogViewModelFragment

class LoginPhoneFragment :
    BaseBindingDialogViewModelFragment<FragmentLoginPhoneBinding, LoginViewModel>() {


    private var loginStatus = LoginStatus.PHONE_READY

    //请求验证码间隔时间
    private var mRequestCodeDuration = 60

    private var mCurrentTotalTick = 0

    private var mRequestDefaultStr = "获取验证码"

    @SuppressLint("ClickableViewAccessibility")
    override fun initDataBinding(binding: FragmentLoginPhoneBinding) {
        onLoad()
        super.initDataBinding(binding)

        initClick(binding)

        observeRequest()
    }


    private fun observeRequest() {
        viewModel.request.requestCode.observe(this) {
            if (it.isSuccess) {
                loginStatus = LoginStatus.CODE_READY
                onRequestCodeSuccess()
            }
        }
        viewModel.request.loginResult.observe(this) {
            if (it.isSuccess) {
                "登录成功".toastSystem()
                mActivity?.finish()
            }
        }
    }

    private fun initClick(binding: FragmentLoginPhoneBinding) {
        binding.tvGetCOde.singleClick {
            onReport(
                TrackerEventName.CLICK_LOGIN,
                TrackerKeys.CLICK_TYPE to "登录验证码页-点击获取验证码"
            )
            if (binding.tvGetCOde.text.toString() == mRequestDefaultStr) {
                checkPhoneNum {
                    viewModel.request.requestPhoneCode(getPhoneNum())
                }
            }

        }
        binding.btLogin.singleClick {
            onReport(
                TrackerEventName.CLICK_LOGIN,
                TrackerKeys.CLICK_TYPE to "登录验证码页-点击登录"
            )
            binding.viewLoginPolicy.checkSelected {
                onReport(
                    TrackerEventName.CLICK_LOGIN,
                    TrackerKeys.CLICK_TYPE to "登录验证码页-点击查看条款"
                )
                checkCode {
                    viewModel.request.loginByPhone("0", getPhoneNum(), getCode())
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun onRequestCodeSuccess() {
        mBinding?.tvGetCOde?.setTextColor(R.color.defaultSubTitleColor.getColor())
        CutdownUtils.countDownCoroutines(((mRequestCodeDuration)),
            scope = (context as AppCompatActivity).lifecycleScope,
            onFinish = {
                mBinding?.tvGetCOde?.text = mRequestDefaultStr
                mBinding?.tvGetCOde?.setTextColor(R.color.defaultTitleColor.getColor())
            },
            onTick = {
                mCurrentTotalTick += 1
                mBinding?.tvGetCOde?.text = "剩余 " + (mRequestCodeDuration - mCurrentTotalTick) + "s"
            })
    }

    override fun createViewModel() = getActivityScopeViewModel(LoginViewModel::class.java)

    /**
     * 检测手机号有效性
     */
    private fun checkPhoneNum(callback: () -> Unit) {
        var isValidPhone = getPhoneNum().isPhone()
        if (!isValidPhone) {
            "请输入正确的手机号".toastSystem()
        }
        callback.invoke()

    }

    /**
     * 检测验证码是否唯恐
     */
    private fun checkCode(callback: () -> Unit) {
        var isValidCode = getCode().isNullOrBlank()
        if (!isValidCode) {
            "请输入验证码".toastSystem()
        }
        callback.invoke()
    }


    fun showLogin(activity: AppCompatActivity) {
        show(activity.supportFragmentManager, "LoginFragment")
    }

    private fun getPhoneNum(): String {
        return mBinding?.inputViewPhone?.text?.toString() ?: ""
    }

    private fun getCode(): String {
        return mBinding?.inputViewCode?.text?.toString() ?: ""
    }

}