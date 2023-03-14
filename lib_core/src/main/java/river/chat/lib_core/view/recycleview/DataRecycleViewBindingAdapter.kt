package river.chat.lib_core.view.recycleview

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import river.chat.lib_core.utils.exts.view.loadCircle
import river.chat.lib_core.utils.exts.view.loadSimple

/**
 *Author: loongwind
 *Time: 2019-09-04
 *Description: Data binding Adapter with recycler view
 */
@BindingAdapter("load:imageCircle")
fun setImageCircle(
    imageView: AppCompatImageView,
    resource: Any?
) {
    resource?.let {
        imageView.loadCircle(resource)
    }

}

@BindingAdapter("load:imageSimple")
fun setImageSimple(
    imageView: AppCompatImageView,
    resource: Any?
) {
    resource?.let {
        imageView.loadSimple(resource)
    }
}



