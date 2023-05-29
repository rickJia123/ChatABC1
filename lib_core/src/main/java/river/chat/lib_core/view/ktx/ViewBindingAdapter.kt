package river.chat.lib_core.view.ktx

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import river.chat.lib_core.utils.exts.view.loadSimple

/**
 *Author: chengminghui
 *Time: 2019-09-04
 *Description: xxx
 */

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isVisibility: Boolean) {
    view.visibility = if (isVisibility) View.VISIBLE else View.GONE
}


@BindingAdapter("android:src")
fun setImage(imageView: ImageView, imgRes: Int) {
    imageView.loadSimple(imgRes)
}