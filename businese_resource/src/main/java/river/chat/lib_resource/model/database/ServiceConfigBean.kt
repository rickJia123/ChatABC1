package river.chat.lib_resource.model.database

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 接口获取的配置信息
 */
@Entity
data class ServiceConfigBean(
    @Id
    var id: Long = 0,

    var appDownUrl: String = "",
    var appPolicyUrl: String = "",
    var appPrivacyPolicy: String = "",
    var appShareBg: String = "",
    var value: String ?= "",

    //更新时间
    var updateTime: Long =0
) : java.io.Serializable