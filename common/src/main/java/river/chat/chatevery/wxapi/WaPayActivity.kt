package river.chat.chatevery.wxapi

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.net.CommonRequestViewModel
import river.chat.businese_common.pay.PayCenter
import river.chat.businese_common.router.jump2Main
import river.chat.businese_common.router.jump2VipExchange
import river.chat.businese_common.router.jump2VipOpen
import river.chat.lib_core.databinding.ActivityWxPayBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity
import river.chat.lib_resource.model.QueryOrderRequestBean


/**
 * Created by beiyongChao on 2023/11/18
 * Description:
 */
@Route(path = HomeRouterConstants.VIP_PAY_RESULT)
class WaPayActivity : BaseBindingViewModelActivity<ActivityWxPayBinding, WxPayViewModel>(),
    IWXAPIEventHandler {

    var userPlugin = getPlugin<UserPlugin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        "微信支付 启动:".log()
        showLoading()
        checkOrderStatus()

        mBinding.tvFunction.singleClick {
            finish()
            jump2Main()
        }
        initEventListener()


    }

    private fun initEventListener() {
        EventCenter.registerReceiveEvent(lifecycleScope) {
            when (it.action) {
                CommonEvent.UPDATE_USER -> {
                    mBinding.tvExpireTime.text = "有效期至：" + userPlugin.getUser().rightsExpireTime
                }
            }
        }
    }

    private fun checkOrderStatus() {
        CommonRequestViewModel().queryOrder(QueryOrderRequestBean().apply {
            id = PayCenter.mCurrentOrderBean?.orderId.safeToString()
        }) {
            dismissLoading()
            if (it.isSuccess) {
                it.data?.let { it ->
                    if (it.finish) {
                        mBinding.tvFunction.visibility = View.VISIBLE
                        PayCenter.onPaySuccess()
                        mBinding.clSuccess.visibility = View.VISIBLE
                    } else {
                        mBinding.tvFunction.visibility = View.GONE
                        mBinding.clFail.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    override fun onEvent(eventId: Int) {

    }


    override fun createViewModel() = WxPayViewModel()
    override fun onReq(req: BaseReq?) {
        ("微信支付 onReq:  type${req?.type}").log()
    }

    override fun onResp(resp: BaseResp?) {
        ("微信支付 onResp: ${resp?.errCode}   type${resp?.type}").log()

    }

}