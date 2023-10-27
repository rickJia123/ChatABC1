package river.chat.lib_resource.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by beiyongChao on 2023/3/2
 * Description:
 */

@Entity
data class MessageBean(

    @Id(assignable = true)
    var id: Long = 0,//消息id

    var parentId: Long = 0,//问题id

    var content: String? = "",//消息内容
    var time: Long ?= 0, //时间戳

    //0 未发送 1 发送(接收)中 2 成功 3 失败
    var status: Int ?= 0,

    //MessageSource
    var source: Int ?= 0,

    var avatar: Any? = null,

    var failFlag: String ?= "",

    var failMsg: String ?= "",

) : java.io.Serializable {
    fun isSelf(): Boolean {
        return source == MessageSource.FRE_SELF
    }
}

//消息状态
object MessageStatus {
    const val LOADING = 0
    const val COMPLETE = 1
    const val FAIL_COMMON = 2
    //被禁止(超过限制/未登录)
    const val FAIL_LIMIT = 3
}

//该条会话类型()
object MessageSource {
    const val FROM_AI = 0
    const val FRE_SELF = 1
}


//msg 接收
data class MessageReceiveBean(
    var msg: MessageBean? = null
)


data class CardMsgBean(
    var questionMsg: MessageBean = MessageBean(),
    var answerMsg: MessageBean = MessageBean()
)


