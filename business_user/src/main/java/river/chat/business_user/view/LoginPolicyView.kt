package river.chat.business_user.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.business_user.R
import river.chat.business_user.databinding.ViewLoginPolicyBinding
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.utils.exts.view.loadSimple
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_core.webview.WebViewHelper
import river.chat.lib_resource.AppConstants


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
                    WebViewHelper.startWebViewActivity(AppConstants.POLICY_URL)
                }
            }
            addText("和")
            addText("隐私政策")
            {
                setColor("#4A90E2")
                onClick {
                    WebViewHelper.startWebViewActivity(AppConstants.POLICY_URL)
                }
            }
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