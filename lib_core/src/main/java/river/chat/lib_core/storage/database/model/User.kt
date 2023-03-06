package river.chat.lib_core.storage.database.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class User(
    @Id(assignable = true)
    var id: Long = 0,
    var token: String = "",

    var avatar: String = "",

    var name: String = "",

    ) : java.io.Serializable
