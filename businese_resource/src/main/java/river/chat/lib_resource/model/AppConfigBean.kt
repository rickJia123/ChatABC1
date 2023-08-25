package river.chat.lib_resource.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class AppConfigBean(

    @Id
    var id: Long = 0,
    var key: String = "",
    var value: String = "",
    //更新时间
    var updateTime: Long
) : java.io.Serializable

