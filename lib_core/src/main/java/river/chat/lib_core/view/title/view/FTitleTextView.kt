package river.chat.lib_core.view.title.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import river.chat.lib_core.view.title.abstract.ITitleBarItem
import river.chat.lib_core.view.title.bean.FTitleItem
import river.chat.lib_core.view.title.dp2px

/**
 * @author Scott
 * TextView类型Item
 */
class FTitleTextView : AppCompatTextView {
    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
        gravity = Gravity.CENTER
        setPadding(dp2px(12f), dp2px(12f), dp2px(12f), dp2px(12f))
    }

      var itemInfo: FTitleItem? = null
        set(value) {
            field = value
        }

      fun setViewChange(isDefault: Boolean) {
        if (isDefault) {
            itemInfo?.from?.let {
                try {
                    setTextColor(Color.parseColor(it))
                } catch (e: Exception) {
                }
            }
        } else {
            itemInfo?.to?.let {
                try {
                    setTextColor(Color.parseColor(it))
                } catch (e: Exception) {
                }
            }
        }
    }

    fun setDirectionPadding(padding: Float? = null) {
        setPadding(
            dp2px(padding ?: 12f),
            dp2px(padding ?: 12f),
            dp2px(padding ?: 12f),
            dp2px(padding ?: 12f)
        )
    }

}