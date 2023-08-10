package river.chat.business_user.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.core.text.italic
import androidx.databinding.DataBindingUtil
import river.chat.business_user.R
import river.chat.business_user.databinding.ViewLoginPolicyBinding
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.longan.ClickableSpan
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_core.webview.WebViewHelper
import river.chat.lib_resource.AppConstants


class LoginPolicyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {


    private val viewBinding: ViewLoginPolicyBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_login_policy,
        this,
        true
    )


    init {

        update()

    }


    fun update() {
        viewBinding.tvPolicy.text = buildSpannedString {
            append("已阅读并同意")
            inSpans(ClickableSpan(river.chat.lib_core.R.color.highTextColor.getColor(), true) {
                WebViewHelper.startWebViewActivity(AppConstants.POLICY_URL)
            }) {
                italic { // 设置斜体
                    append("服务协议")
                }

            }
            append("和")
            inSpans(ClickableSpan(river.chat.lib_core.R.color.highTextColor.getColor(), true) {
                WebViewHelper.startWebViewActivity(AppConstants.POLICY_URL)
            }) {
                italic { // 设置斜体
                    append("隐私政策")
                }
            }
        }


    }

    private fun initClick() {

    }


}