package river.chat.lib_core.storage.database.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class AppConfigBean(

    @Id
    var id: Long = 0,
    var key: String = "",
    var value: String = "",
) : java.io.Serializable

