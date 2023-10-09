package river.chat.businese_common.utils.exts

import android.view.View
import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import river.chat.lib_core.R
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin

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
        error(R.drawable.avator_default)
    }
}

//未登录下隐藏view
fun View.hideWithoutLogin() {
    this.visibility = if (getPlugin<UserPlugin>().isLogin()) View.VISIBLE else View.GONE
}


