package river.chat.lib_core.net.common


enum class NetRespoonseEnum(val code: String) {
    /**
     * 服务调用成功！
     */
    SUCCESS("0000"),

    /**
     * 服务调用失败
     */
    ERROR("9999"),

    /**
     * 错误的token
     */
    INCORRECT_TOKEN("0001"),

    /**
     * 错误的签名
     */
    INCORRECT_SIGNATURE("0002"),

    /**
     * 用户无此权限
     */
    PERMISSION_DENIED("0003"),

    /**
     * 无此服务或服务版本不对
     */
    NO_SERVICE("0004"),

    /**
     * 单位时间内服务调用次数超限！
     */
    REQUEST_TOO_FREQUENT("0005"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST("0006"),

    /**
     * 缺少必传参数
     */
    REQUIRED_PARAMETER_MISSING("0007"),

    /**
     * 参数错误
     */
    PARAM_ERROR("0008"),

    /**
     * 分页数据错误
     */
    PAGE_ERROR("0009"),

    /**
     * 应用ID错误
     */
    APP_ID_ERROR("0010"),

    /**
     * RPC失败
     */
    RPC_ERROR("0011"),

    /**
     * 查询开始时间不能晚于结束时间
     */
    QUERY_DATE_ERROR("0012"),

    /**
     * 数据更新错误
     */
    UPDATE_DATE_ERROR("0013"),

    /**
     * 该项已分享不能再分享
     */
    ALREADY_SHARE_ERROR("0015"),

    /**
     * token 无效
     */
    TOKEN_DATE_INVALID("0001"),

    /**
     * 该用户已经拥有该资源(购买的时候)
     */
    GOODS_HAS_OWNED("0018"),
}