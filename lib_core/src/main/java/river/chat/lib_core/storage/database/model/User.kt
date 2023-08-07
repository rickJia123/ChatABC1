package river.chat.lib_core.storage.database.model

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

    //会员信息
    var vipExpireTime: Long? = 0L,
    //会员类型
    var vipType: Int? = 0,

    //剩余试用次数
    var remainTryTimes: Int = 0

) : java.io.Serializable



