package river.chat.lib_core.net.request


import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel

/**
 * Created by BeiYongchao on 2021/10/26
 *
 * @descripyion:  viewModel
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    /**
     * 通用ui 观察者
     */
    val uiObserver: UiObserver by lazy { UiObserver() }

    override fun onCleared() {
        viewModelScope.cancel()
    }


}