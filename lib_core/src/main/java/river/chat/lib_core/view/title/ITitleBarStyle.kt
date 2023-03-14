package river.chat.lib_core.view.title

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView

/**
 * author : Scott
 * desc   : 标题栏样式接口
 */
interface ITitleBarStyle {
    /**
     * 创建标题 View
     */
    fun createTitleView(context: Context): TextView

    /**
     * 创建左标题 View
     */
    fun createLeftView(context: Context): TextView

    /**
     * 获取右标题 View
     */
    fun createRightView(context: Context): TextView

    /**
     * 获取分割线 View
     */
    fun createLineView(context: Context): View

    /**
     * 获取标题栏背景
     */
    fun getTitleBarBackground(context: Context): Drawable

    /**
     * 获取左标题的按钮背景
     */
    fun getLeftTitleBackground(context: Context): Drawable

    /**
     * 获取右标题的按钮背景
     */
    fun getRightTitleBackground(context: Context): Drawable

    /**
     * 获取默认按钮图片
     */
    fun getBackButtonDrawable(context: Context): Drawable

    /**
     * 获取子控件的水平内间距
     */
    fun getChildHorizontalPadding(context: Context): Int

    /**
     * 获取子控件的垂直内间距
     */
    fun getChildVerticalPadding(context: Context): Int

    /**
     * 获取标题的默认文本
     */
    fun getTitle(context: Context): CharSequence

    /**
     * 获取左标题的默认文本
     */
    fun getLeftTitle(context: Context): CharSequence

    /**
     * 获取右边标题的默认文本
     */
    fun getRightTitle(context: Context): CharSequence

    /**
     * 获取标题的默认字体颜色
     */
    fun getTitleColor(context: Context): ColorStateList

    /**
     * 获取左标题的默认字体颜色
     */
    fun getLeftTitleColor(context: Context): ColorStateList

    /**
     * 获取右边标题的默认字体颜色
     */
    fun getRightTitleColor(context: Context): ColorStateList

    /**
     * 获取中间标题的默认字体大小
     */
    fun getTitleSize(context: Context): Float

    /**
     * 获取左标题的默认字体大小
     */
    fun getLeftTitleSize(context: Context): Float

    /**
     * 获取右标题的默认字体大小
     */
    fun getRightTitleSize(context: Context): Float

    /**
     * 获取标题的字体样式
     *
     * @param style 文字样式
     * 常规：[Typeface.NORMAL]
     * 粗体：[Typeface.BOLD]
     * 斜体：[Typeface.ITALIC]
     * 粗斜体：[Typeface.BOLD_ITALIC]
     */
    fun getTitleTypeface(context: Context, style: Int): Typeface

    /**
     * 获取左标题的的字体样式
     *
     * @param style 文字样式
     * 常规：[Typeface.NORMAL]
     * 粗体：[Typeface.BOLD]
     * 斜体：[Typeface.ITALIC]
     * 粗斜体：[Typeface.BOLD_ITALIC]
     */
    fun getLeftTitleTypeface(context: Context, style: Int): Typeface

    /**
     * 获取右标题的的字体样式
     *
     * @param style 文字样式
     * 常规：[Typeface.NORMAL]
     * 粗体：[Typeface.BOLD]
     * 斜体：[Typeface.ITALIC]
     * 粗斜体：[Typeface.BOLD_ITALIC]
     */
    fun getRightTitleTypeface(context: Context, style: Int): Typeface

    /**
     * 获取标题的默认样式
     */
    fun getTitleStyle(context: Context): Int

    /**
     * 获取左标题的默认样式
     */
    fun getLeftTitleStyle(context: Context): Int

    /**
     * 获取右标题的默认样式
     */
    fun getRightTitleStyle(context: Context): Int

    /**
     * 获取标题的图标默认重心
     */
    fun getTitleIconGravity(context: Context): Int

    /**
     * 获取左标题的图标默认重心
     */
    fun getLeftIconGravity(context: Context): Int

    /**
     * 获取右标题的图标默认重心
     */
    fun getRightIconGravity(context: Context): Int

    /**
     * 获取标题的图标默认间距
     */
    fun getTitleIconPadding(context: Context): Int

    /**
     * 获取左标题的图标默认间距
     */
    fun getLeftIconPadding(context: Context): Int

    /**
     * 获取右标题的图标默认间距
     */
    fun getRightIconPadding(context: Context): Int

    /**
     * 获取标题的图标默认宽度
     */
    fun getTitleIconWidth(context: Context): Int

    /**
     * 获取左标题的图标默认宽度
     */
    fun getLeftIconWidth(context: Context): Int

    /**
     * 获取右标题的图标默认宽度
     */
    fun getRightIconWidth(context: Context): Int

    /**
     * 获取标题的图标默认高度
     */
    fun getTitleIconHeight(context: Context): Int

    /**
     * 获取左标题的图标默认高度
     */
    fun getLeftIconHeight(context: Context): Int

    /**
     * 获取右标题的图标默认高度
     */
    fun getRightIconHeight(context: Context): Int

    /**
     * 分割线是否显示
     */
    fun isLineVisible(context: Context): Boolean

    /**
     * 获取分割线默认大小
     */
    fun getLineSize(context: Context): Int

    /**
     * 获取分割线默认背景
     */
    fun getLineDrawable(context: Context): Drawable

    /**
     * 是否显示左边按钮
     */
    fun isShowLeft(context: Context): Boolean

    /**
     * 是否显示标题
     */
    fun isShowTile(context: Context): Boolean

    /**
     * 是否显示左边按钮
     */
    fun isShowRight(context: Context): Boolean
}