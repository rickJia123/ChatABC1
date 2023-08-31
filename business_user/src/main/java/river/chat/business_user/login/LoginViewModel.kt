package river.chat.business_user.login

import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import river.chat.business_user.api.UserRequest
import river.chat.lib_core.view.main.BaseViewModel

class LoginViewModel : BaseViewModel() {


    /**
     * 当前登录状态
     */
    private val loginStatus = ObservableInt(LoginStatus.READY)

    //当前所在登录页面
    val loginPage =  MutableLiveData<Int>()



    val request = UserRequest(this)



    fun getLoginStatus(): ObservableInt {
        return loginStatus
    }


    // 微信登录
    fun loginByWechat(
        code: String
    ) {
        request.loginByWechat(code)
    }


}