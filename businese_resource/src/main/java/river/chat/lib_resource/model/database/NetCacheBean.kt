package river.chat.lib_resource.model.database

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 接口数据缓存
 */
@Entity
data class NetCacheBean(
    @Id
    var id: Long = 0,
    var data: String = "",

    //日期
    var date: String = "",
    var key: String = "",
) : java.io.Serializable