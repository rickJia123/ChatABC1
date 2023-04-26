package river.chat.lib_core.storage.database.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class User(
    var token: String = "",

    @Id(assignable = true)
    var id: Long = 0,
    var cityId: Int = 0,
    var inviteUserId: Int = 0,


    var sex: Int = 0,
    var headImg:  Any ?=null,
    var realName: String = "",
    var nickName: String = "",
    var mobile: String = "",

    var isNew: Int = 0,

    ) : java.io.Serializable

//会员状态
data class VipInfo(
    //免费/周会员
    var type: Int = 0
)

