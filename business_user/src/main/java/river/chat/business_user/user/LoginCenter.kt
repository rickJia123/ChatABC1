package river.chat.business_user.user

import androidx.appcompat.app.AppCompatActivity
import river.chat.business_user.login.home.LoginMainFragment
import river.chat.business_user.login.home.LoginPhoneFragment

/**
 * Created by beiyongChao on 2023/3/15
 * Description:
 */
object LoginCenter {

    fun launchLoginDialog(activity: AppCompatActivity) {
        LoginMainFragment().showLogin(activity)
    }

    fun launchLoginPhoneDialog(activity: AppCompatActivity) {
        LoginPhoneFragment().showLogin(activity)
    }
}