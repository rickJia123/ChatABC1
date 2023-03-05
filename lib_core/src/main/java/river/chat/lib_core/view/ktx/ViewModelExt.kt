package river.chat.lib_core.view.ktx


import android.content.Context
import androidx.lifecycle.LifecycleOwner
import river.chat.lib_core.view.main.BaseBindingViewModelActivity
import river.chat.lib_core.view.main.BaseBindingViewModelFragment
import river.chat.lib_core.view.main.BaseViewModel

/**
 *
 * @description BaseViewModel 扩展方法，自动bind ViewModel 数据变化提示信息
 * observe owner 为继承 BaseBindingViewModelActivity 的 Activity
 * @param context
 * @return
 *
 */
fun BaseViewModel.bind(activity: BaseBindingViewModelActivity<*, *>) {
    observe(activity, activity){
//        activity.onEvent(it)
    }
}

/**
 *
 * @description BaseViewModel 扩展方法，自动bind ViewModel 数据变化提示信息
 * observe owner 为继承 BaseBindingViewModelFragment 的 Fragment
 * @param context
 * @return
 *
 */
fun BaseViewModel.bind(fragment: BaseBindingViewModelFragment<*, *>) {
    observe(fragment, fragment.context){
//        fragment.onEvent(it)
    }
}


fun  BaseViewModel.observe( owner: LifecycleOwner, context: Context?, onEvent: (Int) -> Unit){

}