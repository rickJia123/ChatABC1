package river.chat.businese_main.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.config.ServiceConfigManager
import river.chat.businese_common.ui.view.dialog.simplelist.SimpleDialogListBean
import river.chat.businese_common.ui.view.dialog.simplelist.SimpleDialogListConfig
import river.chat.businese_common.ui.view.dialog.simplelist.SimpleListDialog
import river.chat.businese_main.constants.MainConstants.SERVICE_LINK
import river.chat.businese_main.vip.VipManager
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewHomeTitleBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_core.view.main.activity.BaseActivity
import river.chat.lib_core.webview.WebViewHelper


class HomeTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {


    private val viewBinding: ViewHomeTitleBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_home_title,
        this,
        true
    )

    init {
        update()
        initClick()

    }

    private fun initClick() {
        viewBinding.viewVip.singleClick {
            VipManager.jump2VipPage()
        }

        viewBinding.viewService.singleClick {
            onServiceClick()
        }
    }

    /**
     * 点击客服，先弹窗，再
     */
    private fun onServiceClick() {
        var tipStr = "请在设置-帮助与反馈中提交您的问题"
        SimpleListDialog.builder(topActivity as BaseActivity).config(
            SimpleDialogListConfig()
                .apply {
                    list.add(SimpleDialogListBean("支付问题") {
                        tipStr.toastSystem()
                    })
                    list.add(SimpleDialogListBean("功能问题") {
                        tipStr.toastSystem()
                    })
                    list.add(SimpleDialogListBean("其他问题") {
                        tipStr.toastSystem()
                    })
                    list.add(SimpleDialogListBean("人工客服") {

//        WebViewHelper.launcher(Api.API_CARD + Api.Eula)
//                .launchAgent(context);
//                        val intent = Intent()
//                        intent.action = "android.intent.action.VIEW"
//                        val content_url = Uri.parse(SERVICE_LINK)
//                        intent.data = content_url
//                        context.startActivity(intent)
                        WebViewHelper.startWebViewActivity(SERVICE_LINK)
                    })
                    list.add(SimpleDialogListBean("取消") {
                    })
                }).show()
    }


    fun update() {
        if (ServiceConfigManager.isNeedHideVip() && ServiceConfigManager.isNeedHideExchange()) {
            viewBinding.viewVip.visibility = View.GONE
        }
        viewBinding.viewVip.refresh()
    }

    fun onSelection(position: Int) {
        if (position >= 3) {
            viewBinding.viewService.visibility = View.VISIBLE
        } else {
            viewBinding.viewService.visibility = View.GONE
        }


    }


}