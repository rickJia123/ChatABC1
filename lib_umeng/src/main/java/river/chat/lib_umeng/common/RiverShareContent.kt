package river.chat.lib_umeng.common

import android.graphics.Bitmap
import com.umeng.socialize.ShareContent

/**
 * Created by beiyongChao on 2023/6/6
 * Description:
 */
data class RiverShareContent(
    var mUrl: String? = "http://mobile.umeng.com/social",
    var mTitle: String = "",
    var mDescription: String = "",
    var mBitmap: Bitmap? = null

) : ShareContent()