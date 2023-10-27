package river.chat.lib_resource.model.database

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import river.chat.lib_resource.model.VipType


@Entity
data class User(
    var token: String = "",

    @Id(assignable = true)
    var id: Long? = 0,
    var cityId: Int? = 0,
    var inviteUserId: Int? = 0,

    var sex: Int? = 0,
    var headImg: Any? = null,
    var realName: String? = "",
    var nickName: String? = "",
    var mobile: String? = "",

    var isNew: Int? = 0,

    //会员过期时间
    var rightsExpireTime: String? = "",
    //权益状态:0没有权益;1会员权益;2会员过期;3体验权益；4体验用完
    var rightsStatus: Int? = 0,
    //vip名称：日卡/月卡/季卡
    var rightsName: String = "",
    //剩余试用次数
    var trialBalance: Int? = 0,

    //是否收藏
    @Transient
    var isCollection: Boolean = false

) : java.io.Serializable {
    fun getVipType(): Int {
        //rick todo
//        return if (rightsStatus == 1 && (rightsExpireTime?.toLong() ?: 0 > System.currentTimeMillis())) {
//            VipType.VIP.value
//        } else if (rightsStatus == 3 && ((trialBalance ?: 0) > 0)) {
//            VipType.VIP.value
//        } else {
//            VipType.NORMAL.value
//        }


        return if (rightsStatus == 1) {
            VipType.VIP.value
        } else if (rightsStatus == 3 && ((trialBalance ?: 0) > 0)) {
            VipType.VIP.value
        } else {
            VipType.NORMAL.value
        }
    }
}



