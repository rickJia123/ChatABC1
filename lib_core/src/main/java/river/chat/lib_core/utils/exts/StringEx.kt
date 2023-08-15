package river.chat.lib_core.utils.exts

/**
 * Created by beiyongChao on 2023/8/15
 * Description:
 */
/**
 * 字符串提取数字
 */
fun String?.extractDigit(): Long {
    if (this == null) {
        return 0
    }
    var nums = this.replace("\\D+".toRegex(), "")

    if (nums.isEmpty())
        return 0

    return nums.toLong()
}

/**
 * 为空情况下返回默认值
 */
fun String?.ifEmptyOrBlank(default: String): String {
    return if (this.isNullOrBlank()) {
        default
    } else {
        this
    }
}

