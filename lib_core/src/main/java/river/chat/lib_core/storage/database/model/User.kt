package river.chat.lib_core.storage.database.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class User(
    @Id(assignable = true)
    var id: Long = 0,
    var token: String = "",

    var avatar: Any ?=null,

    var name: String = "",


    ) : java.io.Serializable

//会员状态
data class VipInfo(
    //免费/周会员
    var type: Int = 0
)

