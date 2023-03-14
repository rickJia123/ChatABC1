package river.chat.lib_core.view.title.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import river.chat.lib_core.utils.exts.view.loadSimple
import river.chat.lib_core.view.title.abstract.ITitleBarItem
import river.chat.lib_core.view.title.bean.FTitleItem
import river.chat.lib_core.view.title.dp2px

/**
 * @author Scott
 * ImageView类型Item
 */
class FTitleImageView : AppCompatImageView {
    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    var getResourceId: ((String?) -> Int?)? = null

    init {
        layoutParams = LinearLayout.LayoutParams(
            dp2px(44f),
            dp2px(44f)
        )
        setPadding(dp2px(5f), dp2px(5f), dp2px(5f), dp2px(5f))
    }

      var itemInfo: FTitleItem? = null
        set(value) {
            field = value
        }

      fun setViewChange(isDefault: Boolean) {
        if (isDefault) {
            changIconView(itemInfo?.from)
        } else {
            changIconView(itemInfo?.to)
        }
    }

    private fun changIconView(resourcePath: String?) {
        resourcePath?.let { path ->
            val localResourceId = getResourceId?.invoke(path)
            if (localResourceId != null) {
                setImageResource(localResourceId)
            } else {
                this.loadSimple(path)
            }
        }
    }
}


