package river.chat.lib_core.view.title.style

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import river.chat.lib_core.utils.exts.getDrawable

/**
 * author : Scott
 * desc   : 水波纹样式实现（对应布局属性：app:barStyle="ripple"）
 */
class RippleBarStyle : TransparentBarStyle() {
    override fun getLeftTitleBackground(context: Context): Drawable {
        val drawable = createRippleDrawable(context)
        return drawable ?: super.getLeftTitleBackground(context)
    }

    override fun getRightTitleBackground(context: Context): Drawable {
        val drawable = createRippleDrawable(context)
        return drawable ?: super.getRightTitleBackground(context)
    }

    /**
     * 获取水波纹的点击效果
     */
    fun createRippleDrawable(context: Context): Drawable? {
        val typedValue = TypedValue()
        return if (context.theme
                .resolveAttribute(
                    android.R.attr.selectableItemBackgroundBorderless,
                    typedValue,
                    true
                )
        ) {
            typedValue.resourceId.getDrawable()
        } else null
    }
}