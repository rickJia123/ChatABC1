package river.chat.businese_common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import coil.decode.DecodeUtils.calculateInSampleSize
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.log.LogUtil
import java.util.Base64

/**
 * Created by beiyongChao on 2024/1/16
 * Description:
 */
object BusinessUtils {

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    fun base64ToBitmap(base64Data: String): Bitmap? {

//        var bytes = base64Data.encodeToByteArray()
        var bytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(base64Data)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

//        val options = BitmapFactory.Options()
//        options.inJustDecodeBounds = false
//        options.inSampleSize = calculateInSampleSize() // 根据实际需求计算采样率
//        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
//        LogUtil.i("PictureFragment base64ToBitmap:" + ":::" + bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)


    }

//    fun test() {
//
//        var filePath = "c:/01.jpg"
//        var bitmap = BitmapFactory.decodeFile(filePath, getBitmapOption(2)); //将图片的长和宽缩小味原来的1/2
//
//
//    }
//
//    private fun getBitmapOption(inSampleSize: Int) {
//        System.gc();
//        var options =   BitmapFactory.Options()
//        options.inPurgeable = true;
//        options.inSampleSize = inSampleSize;
//        return options
//    }

}
