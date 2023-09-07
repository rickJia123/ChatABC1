package river.chat.businese_main.vip

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.utils.onLoad
import river.chat.business_main.databinding.ActivityVipOpenBinding
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity


/**
 * Created by beiyongChao on 2023/8/8
 * Description:vip 开通页
 */
@Route(path = HomeRouterConstants.VIP_OPEN)
class VipOpenActivity : BaseBindingViewModelActivity<ActivityVipOpenBinding, VipViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initDataBinding(binding: ActivityVipOpenBinding) {
        onLoad()
        super.initDataBinding(binding)
        binding.toolBar.setTitle("会员中心")

        binding.viewTabView.setTabClickListener { position, vipTabBean ->
            binding.viewPay.update(vipTabBean)
        }

        loadData()
        observerMsg()
    }

    /**
     * 监听接口消息请求
     */
    private fun observerMsg() {
        viewModel.request.paySkuResult.observe(this) {
            if (it.isSuccess) {
                it.data?.let {
                    mBinding.viewTabView.update(it)
                }
            }
        }
        viewModel.request.vipRightResult.observe(this) {
            if (it.isSuccess) {
                it.data?.let {
                    mBinding.viewVipRights.updateRights(it)
                }
            }
        }
    }

    override fun onEvent(eventId: Int) {
        when (eventId) {

        }
    }

    private fun loadData() {
        viewModel.request.getPaySku()
        viewModel.request.getVipRights()
    }




    override fun createViewModel() = VipViewModel()


}