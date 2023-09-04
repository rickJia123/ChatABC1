package river.chat.lib_resource.model

import com.google.gson.annotations.SerializedName

/**
 * Created by beiyongChao on 2023/8/31
 * Description:
 */

/**
 * 创建订单入参
 */
data class CreateOrderRequestBean(
    var payType: String = PayType.WECHAT_PAY,
    var price: String = "",
    var skuId: String = "",
)

/**
 * 创建订单出参
 */
data class CreateOrderResBean(
    var orderCode: String = "",
    var orderId: Long = 0,
    var payData: String = "",
    var payPrice: String = "",
)

/**
 * 查询订单信息入参
 */
data class QueryOrderRequestBean(
    var id: String = "",
)

/**
 * 查询订单信息出参
 */
data class QueryOrderResBean(
    var finish: Boolean = false,
    var remark: String = "",
)

object PayType {
    const val WECHAT_PAY = "WALLET"
}

//data class WePayPrepayResponseModel (
//    var orderNumber: String? = "",
//    var appId: String? = "",
//    var partnerId: String? = "",
//    var prepayId: String? = "",
//    var nonceStr: String? = "",
//    var timestamp: String? = "",
//
//    @SerializedName("package")
//    var packageValue: String? = "",
//    var sign: String? = "",
//
//    //跨sku返回参数
//    var payData: String? = "",
//)

