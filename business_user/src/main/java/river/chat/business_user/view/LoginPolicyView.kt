package river.chat.business_user.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.dynamicanimation.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.utils.onReport
import river.chat.business_user.R
import river.chat.business_user.databinding.ViewLoginPolicyBinding
import river.chat.lib_core.privacy.PrivacyManager
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.utils.exts.view.loadSimple
import river.chat.lib_core.utils.exts.view.startSpringAnima
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_core.webview.WebViewHelper


class LoginPolicyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {


    private var mIsSelected = false
    private val viewBinding: ViewLoginPolicyBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_login_policy,
        this,
        true
    )


    init {
        initClick()
        update()

    }


    fun update() {
        viewBinding.tvPolicy.buildSpannableString {
            addText("已阅读并同意")
            addText("服务协议") {
                setColor("#4A90E2")
                onClick {
                    LogUtil.i("AgentWebFragment policy")
                    onReport(
                        TrackerEventName.CLICK_LOGIN,
                        TrackerKeys.CLICK_TYPE to "登录验证码页-点击查看条款"
                    )
                    WebViewHelper.startWebViewActivity(PrivacyManager.getPrivacyUrl())
                }
            }
            addText("和")
            addText("隐私政策")
            {
                setColor("#4A90E2")
                onClick {
                    onReport(
                        TrackerEventName.CLICK_LOGIN,
                        TrackerKeys.CLICK_TYPE to "登录验证码页-点击查看条款"
                    )
                    WebViewHelper.startWebViewActivity(PrivacyManager.getPrivacyUrl())
                }
            }
        }
    }

    fun checkSelected(callback: () -> Unit) {
        if (mIsSelected) {
            callback()
        } else {
            ("请先同意服务协议和隐私政策").toastSystem()
            viewBinding.ivSelector.startSpringAnima(
                DynamicAnimation.X,
                -20f,
                20f,
                SpringForce.STIFFNESS_LOW,
                DAMPING_RATIO_HIGH_BOUNCY
            )
        }
    }

    private fun initClick() {
        viewBinding.ivSelector.setOnClickListener {
            mIsSelected = !mIsSelected
            if (mIsSelected) {
                viewBinding.ivSelector.loadSimple(R.mipmap.mark_sel)
            } else {
                viewBinding.ivSelector.loadSimple(R.mipmap.mark_nosel)
            }
        }
    }


}