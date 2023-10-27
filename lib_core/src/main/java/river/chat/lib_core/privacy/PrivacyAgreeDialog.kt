package river.chat.lib_core.privacy


import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.core.text.italic
import river.chat.lib_core.R
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.databinding.DialogPrivacyAgreeBinding
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.tracker.trackNode
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.getString
import river.chat.lib_core.utils.exts.ifEmptyOrBlank
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.ClickableSpan
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment
import river.chat.lib_core.webview.WebViewHelper

/**
 * Created by beiyongChao on 2023/6/9
 * Description:
 */
class PrivacyAgreeDialog(var dialogActivity: AppCompatActivity) :
    BaseBindingDialogFragment<DialogPrivacyAgreeBinding>() {

    private var mPrivacyAgreeCallback: (Boolean) -> Unit = {}
    var mAppName = R.string.app_name.getString()


    var mPrivacy =
        """
        感谢您信任并使用${mAppName}服务。本APP使用过程中需要联网，可能会产生流量费用，具体资费标准请咨询当地运营商。为了提升您的使用体验，保障正常使用相关功能，我们将对您的个人信息进行收集、使用和共享。请您仔细阅读《服务协议》和《隐私政策》，并确认了解我们对您个人信息的处理规则，为了给您提供更好的服务，我们会向您申请以下权限和信息，需要说明的是，以下权限均为非必要权限，如您拒绝提供仅会影响相应功能的使用，并不影响您正常使用产品与/或服务的其他功能：
        打开手机/电话权限
        为了更好的保证您在使用本APP过程中的安全，我们会通过申请该权限识别您的设备，收集手机号码和运营商网络。
        """.trimIndent()
    var mTips = "请您仔细阅读完整版服务协议和隐私政策"

    companion object {
        @JvmStatic
        fun builder(activity: AppCompatActivity): PrivacyAgreeDialog = PrivacyAgreeDialog(activity)
    }

    override fun onStart() {
        super.onStart()

        this.trackNode = TrackNode(
            "LOAD_PAGE" to "隐私协议弹窗",
        )
        this.postTrack("PAGE_LOAD")
    }

    override fun initDataBinding(binding: DialogPrivacyAgreeBinding) {

        binding.tvDes.text = ServiceConfigBox.getConfig().appPrivacyPolicy.ifEmptyOrBlank(mPrivacy)
        binding.tvTips.movementMethod = LinkMovementMethod.getInstance() // 设置了才能点击

        binding.tvTips.text = buildSpannedString {
            append("请您仔细阅读完整版")
            inSpans(ClickableSpan(R.color.highTextColor.getColor(), true) {
                WebViewHelper.startWebViewActivity(PrivacyManager.getPrivacyUrl())
            }) {
                italic { // 设置斜体
                    append("服务协议")
                }
            }
            append("和")
            inSpans(ClickableSpan(R.color.highTextColor.getColor(), true) {
                WebViewHelper.startWebViewActivity(PrivacyManager.getPrivacyUrl())
            }) {
                italic { // 设置斜体
                    append("隐私政策")
                }
            }
        }

        binding.tvLButton.singleClick {
            mPrivacyAgreeCallback?.invoke(false)
            closeDialog()
        }
        binding.tvRButton.singleClick {
            mPrivacyAgreeCallback?.invoke(true)
            closeDialog()
            PrivacyManager.updatePrivacyAgree(true)
        }
    }

    private fun closeDialog() {
        dismiss()
    }

    fun show(privacyAgreeCallback: (Boolean) -> Unit) {
        mPrivacyAgreeCallback = privacyAgreeCallback
        dialogActivity.supportFragmentManager?.let { show(it, "PrivacyAgreeDialog") }
    }

}
