package river.chat.business_user.login

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import okhttp3.internal.notify
import river.chat.business_user.api.UserRequest
import river.chat.business_user.login.LoginStatus.CODE_READY
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.main.BaseViewModel

class LoginViewModel : BaseViewModel() {


    /**
     * 当前登录状态
     */
    private val loginStatus = ObservableInt(LoginStatus.READY)
    val lastStepVisible = ObservableInt(View.GONE)

    val request = UserRequest(this)

    fun getLoginStatus(): ObservableInt {
        return loginStatus
    }

    fun updateLoginStatus(status: Int) {
        loginStatus.set(status)
        lastStepVisible.set(if (status == CODE_READY) View.VISIBLE else View.GONE)
    }


    fun needShowPre(): Int {
        ("needShowPre:" + loginStatus.get() + "").log()
        return if (loginStatus.get() == CODE_READY) View.VISIBLE else View.GONE
    }


}