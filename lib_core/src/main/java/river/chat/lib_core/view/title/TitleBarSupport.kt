package river.chat.lib_core.view.title

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView


/**
 * author : Scott
 * desc   : 标题栏支持类
 */
object TitleBarSupport {
    /** 无色值  */
    const val NO_COLOR = Color.TRANSPARENT

      /**
     * 获取绝对重心
     */
    @JvmStatic
    fun getAbsoluteGravity(view: View, gravity: Int): Int {
        // 适配布局反方向
        return Gravity.getAbsoluteGravity(
            gravity,
            view.resources
                .configuration
                .layoutDirection
        )
    }

    /**
     * 是否启用了布局反方向特性
     */
    @JvmStatic
    fun isLayoutRtl(context: Context): Boolean {
        return context.resources
            .configuration
            .layoutDirection == View.LAYOUT_DIRECTION_RTL
    }

    /**
     * TextView 是否存在内容
     */
    @JvmStatic
    fun isContainContent(textView: TextView): Boolean {
        val text = textView.text
        if (!TextUtils.isEmpty(text)) {
            return true
        }
        val drawables = textView.compoundDrawables
        for (drawable in drawables) {
            if (drawable != null) {
                return true
            }
        }
        return false
    }

    /**
     * 给图片设置着色器
     */
    @JvmStatic
    fun setDrawableTint(drawable: Drawable?, color: Int) {
        drawable?.let {
            if (color == NO_COLOR) {
                return
            }
            it.mutate()
            it.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }

    /**
     * 清除图片设置着色器
     */
    @JvmStatic
    fun clearDrawableTint(drawable: Drawable) {
        drawable.mutate()
        drawable.clearColorFilter()
    }

    /**
     * 根据给定的大小限制 Drawable 宽高
     */
    @JvmStatic
    fun setDrawableSize(drawable: Drawable?, width: Int, height: Int) {
        drawable?.let {
            if (width <= 0 && height <= 0) {
                it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
                return
            }
            if (width > 0 && height > 0) {
                it.setBounds(0, 0, width, height)
                return
            }
            var drawableWidth = it.intrinsicWidth
            var drawableHeight = it.intrinsicHeight
            if (drawableWidth <= 0) {
                drawableWidth = width
            }
            if (drawableHeight <= 0) {
                drawableHeight = height
            }

            // 将 Drawable 等比缩放
            if (width > 0) {
                it.setBounds(0, 0, width, width * drawableHeight / drawableWidth)
            } else {
                it.setBounds(0, 0, drawableWidth * height / drawableHeight, height)
            }
        }
    }

    /**
     * 根据图片重心获取在 TextView 的 Drawable 对象
     */
    @JvmStatic
    fun getTextCompoundDrawable(textView: TextView, gravity: Int): Drawable? {
        val drawables = textView.compoundDrawables
        return when (getAbsoluteGravity(textView, gravity)) {
            Gravity.LEFT -> drawables[0]
            Gravity.TOP -> drawables[1]
            Gravity.RIGHT -> drawables[2]
            Gravity.BOTTOM -> drawables[3]
            else -> null
        }
    }

    /**
     * 根据图标重心设置 TextView 某个位置的 Drawable
     */
    @JvmStatic
    fun setTextCompoundDrawable(textView: TextView, drawable: Drawable?, gravity: Int) {
        when (getAbsoluteGravity(textView, gravity)) {
            Gravity.LEFT -> textView.setCompoundDrawables(drawable, null, null, null)
            Gravity.TOP -> textView.setCompoundDrawables(null, drawable, null, null)
            Gravity.RIGHT -> textView.setCompoundDrawables(null, null, drawable, null)
            Gravity.BOTTOM -> textView.setCompoundDrawables(null, null, null, drawable)
            else -> textView.setCompoundDrawables(null, null, null, null)
        }
    }

    /**
     * 根据文字样式返回不同的字体样式
     */
    @JvmStatic
    fun getTextTypeface(style: Int): Typeface {
        return when (style) {
            Typeface.BOLD -> Typeface.DEFAULT_BOLD
            Typeface.ITALIC, Typeface.BOLD_ITALIC -> Typeface.MONOSPACE
            Typeface.NORMAL -> Typeface.DEFAULT
            else -> Typeface.DEFAULT
        }
    }
}