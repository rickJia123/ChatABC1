package river.chat.lib_core.net.request

/**
 * Created by BeiYongchao on 2023/1/12
 *
 * @descripyion: 请求结果
 */


open class RequestResult<T>
@JvmOverloads constructor(
    /**
     * 是否请求成功
     */
    var isSuccess: Boolean = false,
    var data: T? = null,
    var errorMsg: String = ""
)
