package river.chat.lib_resource.model.vip

/**
 * vip类型
 */
enum class VipType(val value: Int) {
    /*
         NORMAL : 普通用户
         VIP : 正式vip
         TRIAL : 试用

     */

    NORMAL(0),
    TRIAL(1),
    VIP(2)
}