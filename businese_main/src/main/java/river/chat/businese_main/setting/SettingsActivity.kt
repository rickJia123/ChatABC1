package river.chat.businese_main.setting

import com.alibaba.android.arouter.facade.annotation.Route
import org.koin.android.ext.android.inject
import river.chat.businese_common.router.jump2Feedback
import river.chat.businese_common.ui.view.dialog.SimpleDialog
import river.chat.businese_common.ui.view.dialog.SimpleDialogConfig
import river.chat.businese_common.update.AppUpdateManager
import river.chat.businese_common.update.UpdateAppDialog
import river.chat.business_main.databinding.ActivitySettingsBinding
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.*
import river.chat.lib_core.view.main.activity.BaseActivity
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity
import river.chat.lib_core.webview.WebViewHelper

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_SETTINGS)
class SettingsActivity :
    BaseBindingViewModelActivity<ActivitySettingsBinding, SettingsViewModel>() {


    private val homePlugin: HomePlugin by inject()
    private val userPlugin: UserPlugin by inject()


    override fun initDataBinding(binding: ActivitySettingsBinding) {
        super.initDataBinding(binding)
        mainThread { }

        binding.toolBar.setTitle("设置")
        var test = ""
//        MessageBox.getMsgList().forEach {
//            test += it.toString() + "\n"
//        }
        initSettings(binding)
    }

    private fun initSettings(binding: ActivitySettingsBinding) {
        binding.viewSettingPrivacy.click =
            {
                WebViewHelper.startWebViewActivity("https://baike.baidu.com/item/%E5%A5%BD%E4%BC%BC/4084695")
                "隐私协议".toast()
            }
        binding.viewSettingLogout.singleClick {
            userPlugin.logout()
        }
        //意见反馈
        binding.viewSettingFeedback.singleClick {
            jump2Feedback()
        }

        binding.viewCheckUpdate.singleClick {
            if (!AppUpdateManager.isNeedUpdate()) {
                "已是最新版本".toast()
            } else {
                AppUpdateManager.showUpdateAppDialog(this)
            }
        }
        binding.viewCheckUpdate.sub = "V$appVersionName"
        binding.viewSettingDestory.singleClick {
            userPlugin.destroyAccount()
        }
    }


    override fun createViewModel() = SettingsViewModel()

}