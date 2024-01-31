package river.chat.lib_resource.model.database

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 接口获取的默认配置信息
 */
@Entity
data class  AppUpdateConfigResBean(
    @Id
    var id: Long = 0,
    var appUrl: String = "",
    var appVersion: String = "",
    var content: String = "",
    var isForce: Int = 0,
    var isRenew: Int = 0,
    var updateTime: Long = 0,
) : java.io.Serializable