package river.chat.businese_common.utils.exts

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import river.chat.businese_common.constants.CommonConstants
import river.chat.businese_common.utils.BusinessUtils
import river.chat.lib_core.R
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_resource.model.database.AiPictureBean

/**
 * Created by beiyongChao on 2023/9/8
 * Description:
 */

//加载头像
fun ImageView.loadAvatar(resource: Any?) {
    if (resource == null || resource.toString().isNullOrEmpty()) {
        this.setImageResource(R.drawable.avator_default)
    } else {
        this.load(resource) {
            crossfade(true)
            transformations(CircleCropTransformation())
            placeholder(R.drawable.avator_default)
            error(R.drawable.avator_default)
        }
    }
}

//未登录下隐藏view
fun View.hideWithoutLogin() {
    this.visibility = if (getPlugin<UserPlugin>().isLogin()) View.VISIBLE else View.GONE
}

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







