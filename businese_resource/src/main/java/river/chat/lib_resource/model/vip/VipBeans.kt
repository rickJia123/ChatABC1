package river.chat.lib_resource.model.vip

/**
 * Created by beiyongChao on 2023/8/15
 * Description:
 */


data class VipSkuBean(

    //vip 时长文案(三个月/一年)
    var skuName: String = "",

    //vip 价格
    var price: Float = 0.0f,

    /**
     * 单位价格文案（7.6/天，25/月）
     */
    var cheapText: String = "",

    //折扣文案
    var promoText: String = "",

    var skuId: String = ""
)

///recharge/rightsDescr
data class VipRightsBean(
    //会员权益名称
    var normal: MutableList<String> = mutableListOf(),
    var vip: MutableList<String> = mutableListOf(),
)

data class VipRightsBeans(
    //会员权益名称
    var name: String,
)