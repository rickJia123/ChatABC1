package river.chat.lib_resource.model

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by beiyongChao on 2023/8/15
 * Description:
 */


data class VipSkuBean(

    //vip 时长文案(三个月/一年)
    var skuName: String = "",

    //仅1.1元/天
    var promoText3: String? = "",
    var price: Float? = 0.0f,

    /**
     * 单位价格文案（7.6/天，25/月）
     */
    var promoText2: String? = "",

    //折扣文案
    var promoText1: String? = "",

    var skuId: String = ""

) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<VipSkuBean>() {
            override fun areItemsTheSame(
                oldItem: VipSkuBean,
                newItem: VipSkuBean
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: VipSkuBean,
                newItem: VipSkuBean
            ): Boolean {
                return false
            }
        }
    }
}


data class VipRightsBean(
    //会员权益名称
    var normal: MutableList<String> = mutableListOf(),
    var vip: MutableList<String> = mutableListOf(),
)


/**
 * vip类型
 */
enum class VipType(val value: Int) {
    /*
         NORMAL : 普通用户
         VIP : 正式vip
         TRIAL : 试用

     */

    //    权益状态:0没有权益;1会员权益;2会员过期;3体验权益；4体验用完；
    NORMAL(0),
    VIP(1),
    VIP_TIMEOUT(1),
    TRIAL(3),
    TRIAL_END(4)
}