package river.chat.lib_core.net.request


/**
 * Created by BeiYongchao on 2021/10/26
 *
 * @descripyion:  ui 观察者
 */
class UiObserver {
    val showDialog by lazy { ViewModelEvent<String>() }    //显示加载框
    val dismissDialog by lazy { ViewModelEvent<Void>() }     //关闭加载框
    val viewStatus by lazy { ViewModelEvent<String>() }     //页面状态
}