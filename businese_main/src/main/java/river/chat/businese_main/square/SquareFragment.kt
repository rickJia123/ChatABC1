package river.chat.businese_main.square

import android.media.MediaPlayer
import org.koin.android.ext.android.inject
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.router.jump2Feedback
import river.chat.businese_common.router.jump2ShareApp
import river.chat.businese_common.update.AppUpdateManager
import river.chat.businese_common.utils.exts.hideWithoutLogin
import river.chat.businese_common.utils.onLoad
import river.chat.business_main.databinding.FragmentSettingsBinding
import river.chat.business_main.databinding.FragmentSquareBinding
import river.chat.lib_core.privacy.PrivacyManager
import river.chat.lib_core.router.plugin.module.HomePlugin
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
class SquareFragment :
    BaseBindingViewModelFragment<FragmentSquareBinding, SquareViewModel>() {


    private val homePlugin: HomePlugin by inject()
    private val userPlugin: UserPlugin by inject()

    companion object {
        @JvmStatic
        fun newInstance() = SquareFragment()
    }

    override fun initDataBinding(binding: FragmentSquareBinding) {
        onLoad()

        super.initDataBinding(binding)


    }


    override fun createViewModel() = SquareViewModel()

}