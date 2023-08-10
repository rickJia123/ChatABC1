package river.chat.lib_core.beans

/**
 * Created by beiyongChao on 2023/4/26
 * Description:
 */

enum class VipType(val type: Int) {
    NORMAL(0), //普通用户
    VIP_MONTH(1), //月会员
    VIP_QUARTER(2)//季度会员
}

data class VipRightsBeans(
    //会员权益名称
    var name: String,
)