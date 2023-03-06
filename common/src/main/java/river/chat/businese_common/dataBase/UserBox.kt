package river.chat.businese_common.dataBase

import river.chat.lib_core.storage.database.model.User
import river.chat.lib_core.storage.database.BaseBox

/**
 * Created by beiyongChao on 2023/3/6
 * Description:
 */
object UserBox : BaseBox<User>() {

    override fun getEntityClass()= User::class.java

    override fun exit() {

    }

//    override fun get(id: Long): UserBox {
//        return if (box == null) null else box.query().equal(UserInfo_.memberId, id).build()
//            .findFirst()
//    }
//
//    override fun add(data: UserBox?): Long {
//        return super.add(data)
//    }
}