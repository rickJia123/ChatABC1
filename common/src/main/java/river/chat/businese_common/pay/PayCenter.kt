package river.chat.businese_common.pay

import com.umeng.socialize.utils.UmengText.WX
import river.chat.businese_common.net.CommonRequestViewModel
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_resource.model.CreateOrderRequestBean
import river.chat.lib_resource.model.CreateOrderResBean
import river.chat.lib_resource.model.PayType
import river.chat.lib_core.wx.WxManager

/**
 * Created by beiyongChao on 2023/8/31
 * Description:
 */
object PayCenter {


    fun pay(requestBean: CreateOrderRequestBean?) {
        if (requestBean == null || requestBean.skuId.isNullOrEmpty()) {
            return
        }
        //创建订单成功后下一步
        createOrder(requestBean) {
            when (requestBean.payType) {
                PayType.WECHAT_PAY -> {
                    //微信支付
                    WxManager.pay()
                }
            }
        }
    }


    //创建订单
    private fun createOrder(
        requestBean: CreateOrderRequestBean,
        onCreateOrderSuccess: (CreateOrderResBean) -> Unit
    ) {
        CommonRequestViewModel().createPayOrder(requestBean) {
            if (it.isSuccess) {
                onCreateOrderSuccess.invoke(it.data ?: CreateOrderResBean())
            } else {
                it.errorMsg.toast()
            }
        }
    }
}

