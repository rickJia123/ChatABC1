package river.chat.lib_core.utils.exts

import kotlin.math.roundToInt

/**
 * Created by beiyongChao on 2023/8/6
 * Description:
 */

/**
 * 自动取两位小数，小数后面都是00，取整数
 */
fun Float?.autoIntOr2Str(): String {
    try {
        val number1 = String.format("%.2f", this)//只保留小数点后2位
        val number2 = number1.toDouble()
        if ((number2.roundToInt() - number2) == 0.0) {
            return number2.toLong().toString()
        }
        return number2.toString()
    } catch (e: Exception) {
        return ""
    }


}

/**
 * 自动取一位小数，小数后面都是00，取整数
 */
fun Float?.autoIntOr1Str(): String {
    try {
        val number1 = String.format("%.1f", this)//只保留小数点后2位
        val number2 = number1.toDouble()
        if ((number2.roundToInt() - number2) == 0.0) {
            return number2.toLong().toString()
        }
        return number2.toString()
    } catch (e: Exception) {
        return ""
    }
}

/**
 * 自动取两位小数，小数后面都是00，取整数
 */
fun Double?.autoIntOr2Str(): String {
    try {
        val number1 = String.format("%.2f", this)//只保留小数点后2位
        val number2 = number1.toDouble()
        if ((number2.roundToInt() - number2) == 0.0) {
            return number2.toLong().toString()
        }
        return number2.toString()
    } catch (e: Exception) {
        return ""
    }


}

/**
 * 自动取一位小数，小数后面都是00，取整数
 */
fun Double?.autoIntOr1Str(): String {
    try {
        val number1 = String.format("%.1f", this)//只保留小数点后2位
        val number2 = number1.toDouble()
        if ((number2.roundToInt() - number2) == 0.0) {
            return number2.toLong().toString()
        }
        return number2.toString()
    } catch (e: Exception) {
        return ""
    }


}