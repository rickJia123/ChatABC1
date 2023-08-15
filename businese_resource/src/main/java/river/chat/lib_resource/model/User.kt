package river.chat.lib_resource.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


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

    //会员过期时间文案
    var vipExpireTimeStr: String = "",
    //会员类型(普通用户/会员/试用)：VipType
    var vipType: Int? = 0,

    //剩余试用次数
    var remainTryTimes: Int = 0

) : java.io.Serializable



