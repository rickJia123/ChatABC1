package river.chat.businese_main.vip

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.manager.MainCommonHelper
import river.chat.business_main.databinding.DialogActivitysBinding
import river.chat.business_main.databinding.ViewVipActivityBinding
import river.chat.lib_core.utils.exts.singleClick
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
    }


    private fun observeRequest() {
    }


    private fun initClick() {
        mBinding?.clRoot?.singleClick {
            dismissAllowingStateLoss()
        }
        mBinding?.viewTabView?.setTabClickListener { vipTabBean ->
            mBinding?.viewPay?.update(vipTabBean)
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
        if (MainCommonHelper.checkNeedPayActivity()) {
            show(activity.supportFragmentManager, "ActivitiesDialog")
        }

    }


}