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
import river.chat.lib_core.privacy.PrivacyManager
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


    override fun initDataBinding(binding: ActivitySettingsBinding) {
        onLoad()
        super.initDataBinding(binding)
        mainThread { }

        binding.toolBar.setTitle("我的")

    }


    override fun createViewModel() = SettingsViewModel()

}