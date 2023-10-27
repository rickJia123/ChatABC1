package river.chat.lib_resource.model.database

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 本地存储的配置信息
 */
@Entity
data class AppConfigBean(
    @Id
    var id: Long = 0,
    var key: String = "",
    var value: String = "",
    //更新时间
    var updateTime: Long
) : java.io.Serializable

