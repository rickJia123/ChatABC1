package river.chat.lib_resource.model.database

import androidx.recyclerview.widget.DiffUtil
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
    var time: Long? = 0, //时间戳

    //0 未发送 1 发送(接收)中 2 成功 3 失败
    var status: Int? = 0,

    //MessageSource
    var source: Int? = 0,

    var avatar: Any? = null,

    var failFlag: Boolean? = false,

    var failMsg: String? = "",

    //是否收藏
    var isCollected: Boolean? = false,


    var extraMsg1: String? = "",


) : java.io.Serializable {
    fun isSelf(): Boolean {
        return source == MessageSource.FRE_SELF
    }
}

data class MessageBean2(
    var msgList: MutableList<MessageFlowBean> = mutableListOf<MessageFlowBean>()

)

data class MessageFlowBean(
    var data: String = "",
    var status: Int = 0,

    var id: String = "",

    var failFlag: Boolean? = false,

    var failMsg: String? = "",

    )


//消息状态
object MessageStatus {
    const val LOADING = 0
    const val COMPLETE = 1
    const val FAIL_COMMON = 2

    //被禁止(超过限制/未登录)
    const val FAIL_LIMIT = 3
}


//消息类型：文本/图片
object MessageType {
    const val TEXT = 0
    const val PICTURE = 1
}

//该条会话类型()
object MessageSource {

    //无效
    const val INVALID = -1
    const val FROM_AI = 0
    const val FRE_SELF = 1

    //单独消息(Ai招呼/热词提示等)
    const val SINGLE_AI_DEFAULT = 2

    //付费提示
    const val SINGLE_PAY_TIP = 3
}


//msg 接收
data class MessageReceiveBean(
    var msg: MessageBean? = null
)


data class CardMsgBean(
    var questionMsg: MessageBean = MessageBean(),
    var answerMsg: MessageBean = MessageBean()
) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<CardMsgBean>() {
            override fun areItemsTheSame(
                oldItem: CardMsgBean,
                newItem: CardMsgBean
            ): Boolean {
//                return oldItem.questionMsg.id == newItem.questionMsg.id
                return false
            }

            override fun areContentsTheSame(
                oldItem: CardMsgBean,
                newItem: CardMsgBean
            ): Boolean {
//                return oldItem == newItem
                return false
            }
        }
    }
}


