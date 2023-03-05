package river.chat.lib_core.businese

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by beiyongChao on 2023/2/20
 * Description:
 */
object BusineseDateUtils {

    const val PATTERN_Y_TEXT_M_TEXT_D_TEXT = "yyyy年MM月dd日"

    /*  * 标准展示时间逻辑
      *
      * @param
      var  展示毫秒数 :millis? = null
      */
    @SuppressLint("SimpleDateFormat")
    fun getYearDate(millis: Long): String? {
        val target = Calendar.getInstance()
        target.time = Date(millis)
        val current = Calendar.getInstance()

        // 同年
        return if (target[Calendar.YEAR] == current[Calendar.YEAR]) {
            // 今天
            if (target[Calendar.DAY_OF_YEAR] == current[Calendar.DAY_OF_YEAR]) {
                "今天 " + SimpleDateFormat("HH:mm").format(target.time)
            } else if (target[Calendar.DAY_OF_YEAR] == current[Calendar.DAY_OF_YEAR] - 1) {
                "昨天 " + SimpleDateFormat("HH:mm").format(target.time)
            } else {
                SimpleDateFormat("MM月dd日").format(target.time)
            }
        } else {
            SimpleDateFormat(
              PATTERN_Y_TEXT_M_TEXT_D_TEXT,
                Locale.CHINA
            ).format(target.time)
        }
    }
}