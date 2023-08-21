package river.chat.business_user.login

/**
 * Created by beiyongChao on 2023/3/20
 * Description:
 */
object LoginStatus {
    const val READY = 0

    //手机号输入结束,还没点击下一步
    const val PHONE_READY = 1

    //验证码输入结束,还没点击下一步
    const val CODE_READY = 2
}

object LoginPage {
    const val LOGIN_MAIN = 0

    const val LOGIN_PHONE = 1

}