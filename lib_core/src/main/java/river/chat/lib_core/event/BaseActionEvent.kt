package river.chat.lib_core.event

open class BaseActionEvent {

    //事件类型
    var action: String = ""

    //简易参数
    var simpleValue: String = ""

    //自定义参数
    var data: Any? = null
}

open class BaseConstants {

}