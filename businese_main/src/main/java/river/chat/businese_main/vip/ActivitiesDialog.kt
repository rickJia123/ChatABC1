package river.chat.businese_main.vip

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import river.chat.businese_common.dataBase.AppUpdateConfigBox
import river.chat.businese_common.helper.TimeIntervalHelper
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.manager.MainCommonHelper
import river.chat.business_main.databinding.DialogActivitysBinding
import river.chat.business_main.databinding.ViewVipActivityBinding
import river.chat.lib_core.config.AppLocalConfigKey
import river.chat.lib_core.config.ConfigManager
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.appVersionName
import river.chat.lib_core.view.main.dialog.BaseBindingDialogViewModelFragment

class ActivitiesDialog :
    BaseBindingDialogViewModelFragment<DialogActivitysBinding, VipViewModel>() {

    @SuppressLint("ClickableViewAccessibility")
    override fun initDataBinding(binding: DialogActivitysBinding) {
        onLoad()
        super.initDataBinding(binding)
        initClick()
        observeRequest()
        mBinding?.viewActivity?.update(context as AppCompatActivity)
        loadData()
        ReportManager.reportEvent(
            TrackerEventName.DIALOG_ACTIVITY,
            mutableMapOf(
                TrackerKeys.LOAD_PAGE to "活动内容：" + ServiceConfigBox.getConfig().activityTitle
            )
        )
    }


    private fun observeRequest() {
    }


    private fun initClick() {
        mBinding?.clRoot?.singleClick {
            ReportManager.reportEvent(
                TrackerEventName.DIALOG_ACTIVITY,
                mutableMapOf(
                    TrackerKeys.CLICK_TYPE to "关闭 ：" + ServiceConfigBox.getConfig().activityTitle
                )
            )
            dismissAllowingStateLoss()
        }
        mBinding?.viewTabView?.setTabClickListener { vipTabBean ->
            mBinding?.viewPay?.update(vipTabBean)
        }

        mBinding?.viewPay?.onPayClick = {
            ReportManager.reportEvent(
                TrackerEventName.DIALOG_ACTIVITY,
                mutableMapOf(
                    TrackerKeys.CLICK_TYPE to "支付 ：" + ServiceConfigBox.getConfig().activityTitle
                )
            )
            if (getPlugin<UserPlugin>().isLogin()) {
                ConfigManager.putAppConfig(AppLocalConfigKey.ACTIVITY_HAS_CLICK, true)
            }
        }
    }


    override fun createViewModel() = getActivityScopeViewModel(VipViewModel::class.java)


    private fun loadData() {
        if (!MainCommonHelper.mSkuPayList.isNullOrEmpty()) {
            MainCommonHelper.mSkuPayList?.let {
                mBinding?.viewTabView?.updateData(it)
            }
        }
    }

    fun showActivityDialog(activity: AppCompatActivity) {
        //检测是否需要展示活动并且在本地限制时间内
        if (MainCommonHelper.checkNeedPayActivity() && !TimeIntervalHelper.isActivityDialogLimit()) {
            TimeIntervalHelper.updateActivityDialogTime()
            show(activity.supportFragmentManager, "ActivitiesDialog")
        }

    }
}