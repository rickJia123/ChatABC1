package river.chat.businese_main.vip

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.report.VIPTracker
import river.chat.businese_common.utils.onLoad
import river.chat.business_main.databinding.ActivityVipExchangeBinding
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity


/**
 * Created by beiyongChao on 2023/8/31
 * Description:vip 兑换
 */
@Route(path = HomeRouterConstants.VIP_EXCHANGE)
class VipExchangeActivity :
    BaseBindingViewModelActivity<ActivityVipExchangeBinding, VipViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initDataBinding(binding: ActivityVipExchangeBinding) {
        onLoad()
        super.initDataBinding(binding)
        binding.toolBar.setTitle("会员兑换")
        mBinding.btExchange.singleClick {
            it.postTrack(
                TrackerEventName.CLICK_VIP,
                TrackNode(VIPTracker.KEY_EXCHANGE_CONTENT to "兑换码: ${mBinding.inputView.text}")
            )
        }
        loadData()
        observerMsg()
    }

    /**
     * 监听接口消息请求
     */
    private fun observerMsg() {
//        viewModel.request.paySkuResult.observe(this) {
//            if (it.isSuccess) {
//                it.data?.let {
//                    mBinding.viewTabView.update(it)
//                }
//            }
//        }

    }

    override fun onEvent(eventId: Int) {
        when (eventId) {

        }
    }

    private fun loadData() {
        //rick todo
        viewModel.vipExchangeTips.addAll(mutableListOf<String>().apply {
            "直接联系管理员(企业微信)支付获取".also { add(it) }
            "小红书/公众号关注官方账号后，截图发给企微管理员,获取7天vip兑换码".also { add(it) }
            "兑换成功后，会员权益立即生效".also { add(it) }
        })
    }


    override fun createViewModel() = VipViewModel()


}