package river.chat.lib_umeng.common

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

/**
 * Created by beiyongChao on 2024/1/18
 * Description:
 */


    /**
     * 保存图片到本地
     */
    fun Bitmap.save2Local(context:Context):String {

        var fileName="shareImg"
        val path = context.filesDir.path + File.separator+"tempImg"+ fileName + ".jpg"
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
        val out = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.flush()
        out.close()
        return file.absolutePath
    }
