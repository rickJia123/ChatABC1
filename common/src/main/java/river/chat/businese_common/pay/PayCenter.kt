package river.chat.businese_common.pay

import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.net.CommonRequestViewModel
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.router.jump2Main
import river.chat.businese_common.ui.view.dialog.SimpleDialog
import river.chat.businese_common.ui.view.dialog.SimpleDialogConfig
import river.chat.lib_core.event.BaseActionEvent
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.activity.BaseActivity
import river.chat.lib_core.wx.WxManager
import river.chat.lib_resource.model.CreateOrderRequestBean
import river.chat.lib_resource.model.CreateOrderResBean
import river.chat.lib_resource.model.PayType
import river.chat.lib_resource.model.WechatPayModel

/**
 * Created by beiyongChao on 2023/8/31
 * Description:
 */
object PayCenter {
      var mCurrentOrderBean: CreateOrderResBean? = null

    fun pay(requestBean: CreateOrderRequestBean?) {
        if (requestBean == null || requestBean.skuId.isNullOrEmpty()) {
            return
        }
        //创建订单成功后下一步
        createOrder(requestBean) { resBean ->
            mCurrentOrderBean = resBean
            ReportManager.reportEvent(
                TrackerEventName.REQUEST_VIP,
                mutableMapOf(
                    TrackerKeys.REQUEST_CONTENT to "创建订单成功:  价格：" + resBean.payPrice + "  ："
                )
            )
            when (requestBean.payType) {
                PayType.WECHAT_PAY -> {
                    //
                    GsonKits.fromJson<WechatPayModel>(resBean.payData)?.let { WxManager.pay(it) }
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
                it.errorMsg.toastSystem()
            }
        }
    }


    /**
     * 支付成功(刷新用户信息，刷新vip开通页，聊天页，设置页)
     */
    fun onPaySuccess() {
        getPlugin<UserPlugin>().refreshInfo()
//        showSuccessDialog()
    }

    fun postRefreshVipStatus() {
        EventCenter.postEvent(BaseActionEvent().apply {
            action = CommonEvent.UPDATE_USER
        })
    }

    private fun showSuccessDialog() {
        SimpleDialog.builder(topActivity as BaseActivity).config(
            SimpleDialogConfig()
                .apply {
                    title = "购买成功"
                    des = "恭喜您，已经为您开通了VIP会员\n快去体验ChatGpt吧！"
                    leftButtonStr = "取消"
                    rightButtonStr = "去体验"
                    rightClick = {
                        jump2Main()
                    }
                }).show()
    }
}

