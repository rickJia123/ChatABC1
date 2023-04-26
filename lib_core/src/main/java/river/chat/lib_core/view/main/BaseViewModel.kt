package river.chat.lib_core.view.main

import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Description: ViewModel 基类，定义数据加载状态（isLoading）、提示信息（hintText/hintTextRes）、
 * ViewModel 与视图之间的事件传递 ViewModelEvent
 * @Author: wanglejun
 * @CreateDate： 2020/8/13 6:09 AM
 *
 */
open class BaseViewModel: ViewModel() {

    // 事件
    var event = MutableLiveData<BaseVmEvent<Int>>()
    var isLoading = MutableLiveData<Boolean>()
    private var showLoadingCount = ObservableInt(0)


    init {
        showLoadingCount.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val isShowLoading = showLoadingCount.get() > 0
                if(isShowLoading != isLoading.value){
                    isLoading.value = isShowLoading
                }
            }
        })
    }


    fun showLoading(){
        showLoadingCount.set(showLoadingCount.get() + 1)
    }

    fun dismissLoading(){
        showLoadingCount.set(showLoadingCount.get() - 1)
    }


      fun postEvent(eventId: Int) {
        event.value = BaseVmEvent(eventId)
    }


}