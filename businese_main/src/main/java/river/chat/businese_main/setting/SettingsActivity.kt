package river.chat.businese_main.setting

import com.alibaba.android.arouter.facade.annotation.Route
import org.koin.android.ext.android.inject
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.router.jump2Feedback
import river.chat.businese_common.router.jump2ShareApp
import river.chat.businese_common.update.AppUpdateManager
import river.chat.businese_common.utils.exts.hideWithoutLogin
import river.chat.businese_common.utils.onLoad
import river.chat.business_main.databinding.ActivitySettingsBinding
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.appVersionName
import river.chat.lib_core.utils.longan.mainThread
import river.chat.lib_core.utils.longan.toastSystem
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
        onLoad()
        super.initDataBinding(binding)
        mainThread { }

        binding.toolBar.setTitle("我的")
        var test = ""
//        MessageBox.getMsgList().forEach {
//            test += it.toString() + "\n"
//        }
        initSettings(binding)
    }

    private fun initSettings(binding: ActivitySettingsBinding) {
        binding.viewSettingPrivacy.click =
            {
                onSettingClick(binding.viewSettingPrivacy)
                WebViewHelper.startWebViewActivity("https://baike.baidu.com/item/%E5%A5%BD%E4%BC%BC/4084695")
                "隐私协议".toastSystem()
            }
        binding.viewSettingLogout.singleClick {
            onSettingClick(binding.viewSettingLogout)
            userPlugin.logout()
        }
        //意见反馈
        binding.viewSettingFeedback.singleClick {
            onSettingClick(binding.viewSettingFeedback)
            jump2Feedback()
        }

        binding.viewCheckUpdate.apply {
            singleClick {
                onSettingClick(binding.viewCheckUpdate)
                if (!AppUpdateManager.isNeedUpdate()) {
                    "已是最新版本".toastSystem()
                } else {
                    AppUpdateManager.showUpdateAppDialog(this@SettingsActivity, true)
                }

            }
            sub = "V$appVersionName"
            tipVisible = AppUpdateManager.isNeedUpdate()
        }

        //注销账号
        binding.viewSettingDestory.singleClick {
            onSettingClick(binding.viewSettingDestory)
            userPlugin.destroyAccount()
        }

        //分享app
        binding.viewSettingShareApp.singleClick {
            jump2ShareApp()
        }

        binding.viewSettingLogout.hideWithoutLogin()
        binding.viewSettingDestory.hideWithoutLogin()
    }

    private fun onSettingClick(settingItem: SettingItem) {
        settingItem.postTrack(
            TrackerEventName.CLICK_SETTING,
            TrackNode(TrackerKeys.CLICK_TYPE to (settingItem.name ?: ""))
        )
    }


    override fun createViewModel() = SettingsViewModel()

}