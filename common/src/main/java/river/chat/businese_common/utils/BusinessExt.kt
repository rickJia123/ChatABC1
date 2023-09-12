package river.chat.businese_common.utils

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import river.chat.lib_core.R

/**
 * Created by beiyongChao on 2023/9/8
 * Description:
 */

//加载头像
fun ImageView.loadAvatar(resource: Any?) {
    this.load(resource) {
        crossfade(true)
        transformations(CircleCropTransformation())
        placeholder(R.drawable.avator_default)
    }
}
