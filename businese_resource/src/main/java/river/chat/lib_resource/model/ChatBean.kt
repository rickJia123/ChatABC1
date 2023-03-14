package river.chat.lib_resource.model

import river.chat.lib_core.storage.database.model.User
import river.chat.lib_core.storage.database.model.VipInfo

/**
 * Created by beiyongChao on 2023/3/2
 * Description:
 */
data class MessageBean(
    //MessageSource
    var source: Int = 0,
    var id: Long = 0,
    var msg: String? = "",

    //用户信息
    var user: User? = null,
    var vipInfo: VipInfo? = null,

    //MessageStatus
    var status: Int = 0

)

//该条会话类型()
object MessageSource {
    const val FROM_AI = 0
    const val FRE_SELF = 1
}

//该条会话类型()
object MessageStatus {
    const val READY = 0
    const val LOADING = 1
    const val COMPLETE = 2
    const val FAIL = 3
}

//msg 接收
data class MessageReceiveBean(
var msg :MessageBean ?=null
)


