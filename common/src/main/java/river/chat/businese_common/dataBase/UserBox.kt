package river.chat.businese_common.dataBase

import river.chat.lib_resource.model.User
import river.chat.lib_core.storage.database.BaseBox

/**
 * Created by beiyongChao on 2023/3/6
 * Description:
 */
object UserBox : BaseBox<User>() {

    override fun getEntityClass() = User::class.java

    override fun exit() {

    }

    fun getCurrentUser(): User {
        loop@ run {
            box?.all
                ?.forEach {
                    if (it?.token?.isNotEmpty() == true) {
                        return it
                    }
                }
        }
        return User()
    }

    fun updateUser(user: User): Long {
        return box?.put(user) ?: 0
    }

    fun deleteAll() {
        box?.removeAll()
    }
}


