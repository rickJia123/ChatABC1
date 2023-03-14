package river.chat.lib_core.view.title.style

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import river.chat.lib_core.view.title.ITitleBarStyle
import river.chat.lib_core.view.title.TitleBarSupport.getTextTypeface

/**
 * author : Scott
 * desc   : 默认初始化器基类
 */
abstract class CommonBarStyle : ITitleBarStyle {
    override fun createTitleView(context: Context): TextView {
        val titleView = newTitleView(context)
        titleView.gravity = Gravity.CENTER_VERTICAL
        titleView.isFocusable = true
        titleView.setSingleLine()
        // 给标题设置跑马灯效果（仅在标题过长的时候才会显示）
        titleView.ellipsize = TextUtils.TruncateAt.MARQUEE
        // 设置跑马灯的循环次数
        titleView.marqueeRepeatLimit = -1
        // 设置跑马灯之后需要设置选中才能有效果
        titleView.isSelected = true
        return titleView
    }

    open fun newTitleView(context: Context): TextView {
        return AppCompatTextView(context)
    }

    override fun createLeftView(context: Context): TextView {
        val leftView = newLeftView(context)
        leftView.gravity = Gravity.CENTER_VERTICAL
        leftView.isFocusable = true
        leftView.setSingleLine()
        leftView.ellipsize = TextUtils.TruncateAt.END
        return leftView
    }

    open fun newLeftView(context: Context): TextView {
        return AppCompatTextView(context)
    }

    override fun createRightView(context: Context): TextView {
        val rightView = newRightView(context)
        rightView.gravity = Gravity.CENTER_VERTICAL
        rightView.isFocusable = true
        rightView.setSingleLine()
        rightView.ellipsize = TextUtils.TruncateAt.END
        return rightView
    }

    open fun newRightView(context: Context): TextView {
        return AppCompatTextView(context)
    }

    override fun createLineView(context: Context): View {
        return View(context)
    }

    override fun getChildHorizontalPadding(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 0f,
            context.resources
                .displayMetrics
        ).toInt()
    }

    override fun getChildVerticalPadding(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 0f,
            context.resources
                .displayMetrics
        ).toInt()
    }

    override fun getTitle(context: Context): CharSequence {
        // 如果当前上下文对象是 Activity，就获取 Activity 的 label 属性作为标题栏的标题
        if (context is Activity) {
            // 获取清单文件中的 android:label 属性值
            val label = context.title
            if (!TextUtils.isEmpty(label)) {
                try {
                    val packageManager = context.getPackageManager()
                    val packageInfo = packageManager.getPackageInfo(
                        context.getPackageName(),
                        0
                    )
                    // 如果当前 Activity 没有设置 android:label 属性，则默认会返回 App 名称，则需要过滤掉
                    if (label.toString() != packageInfo.applicationInfo.loadLabel(packageManager)
                            .toString()
                    ) {
                        // 设置标题
                        return label
                    }
                } catch (ignored: PackageManager.NameNotFoundException) {
                }
            }
        }
        return ""
    }

    override fun getLeftTitle(context: Context): CharSequence {
        return ""
    }

    override fun getRightTitle(context: Context): CharSequence {
        return ""
    }

    override fun getTitleSize(context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 17f,
            context.resources
                .displayMetrics
        )
    }

    override fun getLeftTitleSize(context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 17f,
            context.resources
                .displayMetrics
        )
    }

    override fun getRightTitleSize(context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 17f,
            context.resources
                .displayMetrics
        )
    }

    override fun getTitleTypeface(context: Context, style: Int): Typeface {
        return getTextTypeface(style)
    }

    override fun getLeftTitleTypeface(context: Context, style: Int): Typeface {
        return getTextTypeface(style)
    }

    override fun getRightTitleTypeface(context: Context, style: Int): Typeface {
        return getTextTypeface(style)
    }

    override fun getTitleStyle(context: Context): Int {
        return Typeface.NORMAL
    }

    override fun getLeftTitleStyle(context: Context): Int {
        return Typeface.NORMAL
    }

    override fun getRightTitleStyle(context: Context): Int {
        return Typeface.NORMAL
    }

    override fun getTitleIconGravity(context: Context): Int {
        return Gravity.END
    }

    override fun getLeftIconGravity(context: Context): Int {
        return Gravity.START
    }

    override fun getRightIconGravity(context: Context): Int {
        return Gravity.END
    }

    override fun getTitleIconPadding(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 0f,
            context.resources
                .displayMetrics
        ).toInt()
    }

    override fun getLeftIconPadding(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 0f,
            context.resources
                .displayMetrics
        ).toInt()
    }

    override fun getRightIconPadding(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 0f,
            context.resources
                .displayMetrics
        ).toInt()
    }

    override fun getTitleIconWidth(context: Context): Int {
        return 0
    }

    override fun getLeftIconWidth(context: Context): Int {
        return 0
    }

    override fun getRightIconWidth(context: Context): Int {
        return 0
    }

    override fun getTitleIconHeight(context: Context): Int {
        return 0
    }

    override fun getLeftIconHeight(context: Context): Int {
        return 0
    }

    override fun getRightIconHeight(context: Context): Int {
        return 0
    }

    override fun isLineVisible(context: Context): Boolean {
        return true
    }

    override fun getLineSize(context: Context): Int {
        return 1
    }

    override fun isShowLeft(context: Context): Boolean {
        return true
    }

    override fun isShowRight(context: Context): Boolean {
        return true
    }

    override fun isShowTile(context: Context): Boolean {
        return true
    }
}