package river.chat.businese_main.utils

import android.graphics.Bitmap
import river.chat.businese_common.utils.BusinessUtils
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_resource.model.database.AiPictureBean

fun String.logChat() =
    LogUtil.i("river Chat:$this")


fun AiPictureBean?.getBitmap(): Bitmap? {
    var data = this?.content
    var bitmap: Bitmap? = null
    if (data?.isNotEmpty() == true) {
        bitmap = BusinessUtils.base64ToBitmap(data ?: "")
        LogUtil.i("PictureFragment drawBitmap data:" + ":::" + (data == null) + ":::" + (bitmap == null))
        LogUtil.i("PictureFragment drawBitmap bitmap:" + ":::" + bitmap)
    }
    return bitmap
}


