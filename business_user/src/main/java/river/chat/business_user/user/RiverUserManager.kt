package river.chat.business_user.user

import river.chat.businese_common.dataBase.UserBox
import river.chat.lib_core.storage.database.model.User
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.logConfig

/**
 * Created by beiyongChao on 2023/3/15
 * Description:
 */
object RiverUserManager {

    /**
     * 是否登录
     */
    fun isLogin(): Boolean {
        getCurrentUser().toString().logConfig()
        return getCurrentUser().id > 0
    }

    /**
     * 登录成功
     */
    fun onLoginSuccess(user: User) {
        updateUser(user)
    }

    /**
     * 退出成功
     */
    fun onLogoutSuccess() {
        UserBox.deleteAll()
    }


    /**
     * 更新用户信息
     */
    fun updateUser(user: User) {
        UserBox.deleteAll()
        UserBox.updateUser(user)
    }

    /**
     *
     */
    fun getCurrentUser() = getCurrentUserByDb()

    /**
     * 从数据库读取用户
     */
    private fun getCurrentUserByDb() = UserBox.getCurrentUser()

}