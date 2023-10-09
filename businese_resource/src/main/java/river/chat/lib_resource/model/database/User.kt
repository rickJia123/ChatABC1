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
    var rightsExpireTime: String = "",

    @Transient
    //会员类型(日卡/月卡/季卡)：VipType
   private var vipType: Int? = 0,
    //vip名称：日卡/月卡/季卡
    var rightsName: String = "",
    //剩余试用次数
    var remainTryTimes: Int = 0

) : java.io.Serializable {
    fun getVipType(): Int {
        //rick todo
//        return expireTime.isNotEmpty() && expireTime.toLong() > TimeUtils.getTimeStamp()
        if (rightsExpireTime.isNotEmpty()) {
            return VipType.VIP.value
        } else {
            return VipType.NORMAL.value
        }
    }
}



