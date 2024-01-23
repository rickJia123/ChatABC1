package river.chat.businese_main.mine

import androidx.lifecycle.lifecycleScope
import org.koin.android.ext.android.inject
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.router.jump2Feedback
import river.chat.businese_common.router.jump2ShareApp
import river.chat.businese_common.update.AppUpdateManager
import river.chat.businese_common.utils.exts.hideWithoutLogin
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.constants.MainConstants
import river.chat.business_main.databinding.FragmentSettingsBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.privacy.PrivacyManager
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.appVersionName
import river.chat.lib_core.utils.longan.mainThread
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment
import river.chat.lib_core.webview.WebViewHelper

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */
class SettingsFragment :
    BaseBindingViewModelFragment<FragmentSettingsBinding, SettingsViewModel>() {


    private val userPlugin: UserPlugin by inject()

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun initDataBinding(binding: FragmentSettingsBinding) {
        onLoad()
        super.initDataBinding(binding)
        mainThread { }


        initSettings(binding)
        initEventListener()
    }

    private fun initSettings(binding: FragmentSettingsBinding) {
        binding.viewSettingPrivacy.click =
            {
                onSettingClick(binding.viewSettingPrivacy)
                WebViewHelper.startWebViewActivity(PrivacyManager.getPrivacyUrl())
            }
        binding.viewSettingProtocol.click =
            {
                onSettingClick(binding.viewSettingPrivacy)
                WebViewHelper.startWebViewActivity(PrivacyManager.getPrivacyUrl())
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
                    getActivityContext()?.let {
                        AppUpdateManager.showUpdateAppDialog(it, true)
                    }
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
        binding.viewEntranceCollection.refreshData(MainConstants.ENTRANCE_TYPE_COLLECTION)
    }

    private fun onSettingClick(settingItem: SettingItem) {
        settingItem.postTrack(
            TrackerEventName.CLICK_SETTING,
            TrackNode(TrackerKeys.CLICK_TYPE to (settingItem.name ?: ""))
        )
    }

    private fun initEventListener() {
        EventCenter.registerReceiveEvent(lifecycleScope) {
            when (it.action) {
                CommonEvent.LOGIN_CHANGE -> {
                    mBinding.viewUser.update()
                    initSettings(mBinding)
                }

                CommonEvent.UPDATE_USER -> {
                    mBinding.viewUser.update()
                }
            }
        }
    }


    override fun createViewModel() = SettingsViewModel()

}