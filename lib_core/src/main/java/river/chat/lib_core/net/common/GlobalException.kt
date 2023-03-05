package river.chat.lib_core.net.common


open class OperationException : RuntimeException {
    companion object {
        const val DEFAULT_STATUS = -102400//默认状态码
    }

    var code = DEFAULT_STATUS
        private set

    constructor(error: String?) : super(error)
    constructor(code: Int, error: String?) : super(error) {
        this.code = code
    }

    constructor(code: String, error: String?) : super(error) {
        try {
            this.code = code.toInt()
        } catch (e: Exception) {
            //
        }
    }

    override val message: String
        get() = super.message ?: ""
}

