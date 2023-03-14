package river.chat.lib_core.view.title

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.LinearLayoutCompat
import river.chat.lib_core.R
import river.chat.lib_core.utils.exts.getDrawable
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.title.TitleBarSupport.clearDrawableTint
import river.chat.lib_core.view.title.TitleBarSupport.getAbsoluteGravity
import river.chat.lib_core.view.title.TitleBarSupport.getTextCompoundDrawable
import river.chat.lib_core.view.title.TitleBarSupport.getTextTypeface
import river.chat.lib_core.view.title.TitleBarSupport.isContainContent
import river.chat.lib_core.view.title.TitleBarSupport.isLayoutRtl
import river.chat.lib_core.view.title.TitleBarSupport.setDrawableSize
import river.chat.lib_core.view.title.TitleBarSupport.setDrawableTint
import river.chat.lib_core.view.title.TitleBarSupport.setTextCompoundDrawable
import river.chat.lib_core.view.title.abstract.ITitleBarItem
import river.chat.lib_core.view.title.style.LightBarStyle
import river.chat.lib_core.view.title.style.RippleBarStyle
import river.chat.lib_core.view.title.style.TransparentBarStyle
import river.chat.lib_core.view.title.view.FTitleImageView
import kotlin.math.max

/**
 * author : Scott
 * desc   : 标题栏框架
 */
open abstract class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener, View.OnLayoutChangeListener {
    /**
     * 获取当前的初始化器
     */
    /** 当前初始化器  */
    var currentStyle: ITitleBarStyle

    /** 监听器对象  */
    private var mListener: OnTitleBarListener? = null
    /**
     * 获取左标题View对象
     */
    /** 标题栏子控件  */
    val leftView: TextView

    /**
     * 获取标题View对象
     */
    val titleView: TextView

    /**
     * 获取右标题View对象
     */
    val rightView: TextView

    /**
     * 获取分割线View对象
     */
    val lineView: View

    /** 控件内间距  */
    private var mHorizontalPadding: Int
    private var mVerticalPadding: Int

    /** 图标显示大小  */
    private var mLeftIconWidth = 0
    private var mLeftIconHeight = 0
    private var mTitleIconWidth = 0
    private var mTitleIconHeight = 0
    private var mRightIconWidth = 0
    private var mRightIconHeight = 0

    /** 图标显示重心  */
    private var mLeftIconGravity = 0
    private var mTitleIconGravity = 0
    private var mRightIconGravity = 0

    /** 图标着色器  */
    private var mLeftIconTint = 0
    private var mTitleIconTint = 0
    private var mRightIconTint = TitleBarSupport.NO_COLOR

    /**
     * [View.OnLayoutChangeListener]
     */
    private val leftButtons: LinearLayoutCompat by lazy {
        createLeftButterParent()
    }
    private val rightButtons: LinearLayoutCompat by lazy {
        createRightButterParent()
    }

    protected open fun onCommonSetting() {}

    override fun onLayoutChange(
        v: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
    ) {
        // 先移除当前的监听，因为 TextView.setMaxWidth 方法会重新触发监听
        removeOnLayoutChangeListener(this)
        if (leftView.maxWidth != Int.MAX_VALUE && titleView.maxWidth != Int.MAX_VALUE && rightView.maxWidth != Int.MAX_VALUE) {
            // 不限制子 View 的宽度
            leftView.maxWidth = Int.MAX_VALUE
            titleView.maxWidth = Int.MAX_VALUE
            rightView.maxWidth = Int.MAX_VALUE
            // 对子 View 重新进行测量
            leftView.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            titleView.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            rightView.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
        }

        // 标题栏子 View 最大宽度限制算法
        val barWidth = right - left
        val sideWidth = max(leftView.measuredWidth, rightView.measuredWidth)
        val maxWidth = sideWidth * 2 + titleView.measuredWidth
        // 算出来子 View 的宽大于标题栏的宽度
        if (maxWidth >= barWidth) {
            // 判断是左右项太长还是标题项太长
            if (sideWidth > barWidth / 3) {
                // 如果是左右项太长，那么按照比例进行划分
                leftView.maxWidth = barWidth / 4
                titleView.maxWidth = barWidth / 2
                rightView.maxWidth = barWidth / 4
            } else {
                // 如果是标题项太长，那么就进行动态计算
                leftView.maxWidth = sideWidth
                titleView.maxWidth = barWidth - sideWidth * 2
                rightView.maxWidth = sideWidth
            }
        } else {
            if (leftView.maxWidth != Int.MAX_VALUE && titleView.maxWidth != Int.MAX_VALUE && rightView.maxWidth != Int.MAX_VALUE) {
                // 不限制子 View 的最大宽度
                leftView.maxWidth = Int.MAX_VALUE
                titleView.maxWidth = Int.MAX_VALUE
                rightView.maxWidth = Int.MAX_VALUE
            }
        }

        // TextView 里面必须有东西才能被点击
        leftView.isEnabled = isContainContent(leftView)
        titleView.isEnabled = isContainContent(titleView)
        rightView.isEnabled = isContainContent(rightView)
        post { // 这里再次监听需要延迟，否则会导致递归的情况发生
            addOnLayoutChangeListener(this@TitleBar)
        }
    }

    /**
     * [View.OnClickListener]
     */
    override fun onClick(view: View) {
        mListener?.let {
            when {
                view === leftView -> {
                    it.onLeftClick(this)
                }
                view === rightView -> {
                    it.onRightClick(this)
                }
                view === titleView -> {
                    it.onTitleClick(this)
                }
                view === mCustomTitleView -> {
                    it.onTitleClick(this)
                }
            }
        }
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams) {
        if (params.width == LayoutParams.WRAP_CONTENT) {
            // 如果当前宽度是自适应则转换成占满父布局
            params.width = LayoutParams.MATCH_PARENT
        }
        val horizontalPadding = mHorizontalPadding
        var verticalPadding = 0
        // 如果当前高度是自适应则设置默认的内间距
        if (params.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            verticalPadding = mVerticalPadding
        }
        setChildPadding(horizontalPadding, verticalPadding)
        super.setLayoutParams(params)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    /**
     * 设置标题栏的点击监听器
     */
    fun setOnTitleBarListener(listener: OnTitleBarListener?): TitleBar {
        mListener = listener
        // 设置监听
        titleView.setOnClickListener(this)
        leftView.setOnClickListener(this)
        rightView.setOnClickListener(this)
        mCustomTitleView?.setOnClickListener(this)
        return this
    }

    /**
     * 设置标题的文本
     */
    fun setTitle(id: Int): TitleBar {
        return setTitle(resources.getString(id))
    }

    fun setTitle(text: CharSequence?): TitleBar {
        titleView.text = text
        return this
    }

    val title: CharSequence
        get() = titleView.text

    /**
     * 设置左标题的文本
     */
    fun setLeftTitle(id: Int): TitleBar {
        return setLeftTitle(resources.getString(id))
    }

    fun setLeftTitle(text: CharSequence?): TitleBar {
        leftView.text = text
        return this
    }

    val leftTitle: CharSequence
        get() = leftView.text

    /**
     * 设置右标题的文本
     */
    fun setRightTitle(id: Int): TitleBar {
        return setRightTitle(resources.getString(id))
    }

    /**
     * 设置右标题的文本
     */
    fun setClearRightTitle(id: Int): TitleBar {
        clearCompoundDrawables(rightView)
        rightView.visibility = VISIBLE
        rightView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
        rightView.setTextColor(Color.BLACK)
        return setRightTitle(resources.getString(id))
    }

    private fun clearCompoundDrawables(textView: TextView) {
        textView.setCompoundDrawables(null, null, null, null)
    }

    fun setRightTitle(text: CharSequence?): TitleBar {
        rightView.text = text
        return this
    }

    val rightTitle: CharSequence
        get() = rightView.text

    fun setTitleStyle(style: Int): TitleBar {
        return setTitleStyle(getTextTypeface(style), style)
    }

    /**
     * 设置标题样式
     *
     * @param typeface 字体样式
     * @param style    文字样式
     */
    fun setTitleStyle(typeface: Typeface?, style: Int): TitleBar {
        titleView.setTypeface(typeface, style)
        return this
    }

    fun setLeftTitleStyle(style: Int): TitleBar {
        return setLeftTitleStyle(getTextTypeface(style), style)
    }

    /**
     * 设置左标题样式
     *
     * @param typeface 字体样式
     * @param style    文字样式
     */
    fun setLeftTitleStyle(typeface: Typeface?, style: Int): TitleBar {
        leftView.setTypeface(typeface, style)
        return this
    }

    fun setRightTitleStyle(style: Int): TitleBar {
        return setRightTitleStyle(getTextTypeface(style), style)
    }

    /**
     * 设置右边标题样式
     *
     * @param typeface 字体样式
     * @param style    文字样式
     */
    fun setRightTitleStyle(typeface: Typeface?, style: Int): TitleBar {
        rightView.setTypeface(typeface, style)
        return this
    }

    /**
     * 设置标题的字体颜色
     */
    fun setTitleColor(color: Int): TitleBar {
        return setTitleColor(ColorStateList.valueOf(color))
    }

    fun setTitleColor(color: ColorStateList?): TitleBar {
        if (color != null) {
            titleView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置左标题的字体颜色
     */
    fun setLeftTitleColor(color: Int): TitleBar {
        return setLeftTitleColor(ColorStateList.valueOf(color))
    }

    fun setLeftTitleColor(color: ColorStateList?): TitleBar {
        if (color != null) {
            leftView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置右标题的字体颜色
     */
    fun setRightTitleColor(color: Int): TitleBar {
        return setRightTitleColor(ColorStateList.valueOf(color))
    }

    fun setRightTitleColor(color: ColorStateList?): TitleBar {
        if (color != null) {
            rightView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置标题的字体大小
     */
    fun setTitleSize(size: Float): TitleBar {
        return setTitleSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    fun setTitleSize(unit: Int, size: Float): TitleBar {
        titleView.setTextSize(unit, size)
        return this
    }

    /**
     * 设置左标题的字体大小
     */
    fun setLeftTitleSize(size: Float): TitleBar {
        return setLeftTitleSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    fun setLeftTitleSize(unit: Int, size: Float): TitleBar {
        leftView.setTextSize(unit, size)
        return this
    }

    /**
     * 设置右标题的字体大小
     */
    fun setRightTitleSize(size: Float): TitleBar {
        return setRightTitleSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    fun setRightTitleSize(unit: Int, size: Float): TitleBar {
        rightView.setTextSize(unit, size)
        return this
    }

    /**
     * 设置标题的图标
     */
    fun setTitleIcon(id: Int): TitleBar {
        return setTitleIcon(id.getDrawable())
    }

    fun setTitleIcon(drawable: Drawable?): TitleBar {
        setDrawableTint(drawable, mTitleIconTint)
        setDrawableSize(drawable, mTitleIconWidth, mTitleIconHeight)
        setTextCompoundDrawable(titleView, drawable, mTitleIconGravity)
        return this
    }

    /**
     * 清除标题所有信息
     */
    fun clearTitle() {
        setTitle(null)
        setTitleIcon(null)
    }

    val titleIcon: Drawable?
        get() = getTextCompoundDrawable(titleView, mTitleIconGravity)

    /**
     * 设置左标题的图标
     */
    fun setLeftIcon(id: Int): TitleBar {
        return setLeftIcon(id.getDrawable())
    }

    fun setLeftIcon(drawable: Drawable?): TitleBar {
        setDrawableTint(drawable, mLeftIconTint)
        setDrawableSize(drawable, mLeftIconWidth, mLeftIconHeight)
        setTextCompoundDrawable(leftView, drawable, mLeftIconGravity)
        return this
    }

    val leftIcon: Drawable?
        get() = getTextCompoundDrawable(leftView, mLeftIconGravity)

    /**
     * 设置右标题的图标
     */
    fun setRightIcon(id: Int): TitleBar {
        return setRightIcon(id.getDrawable())
    }

    fun setRightIcon(drawable: Drawable?): TitleBar {
        setDrawableTint(drawable, mRightIconTint)
        setDrawableSize(drawable, mRightIconWidth, mRightIconHeight)
        setTextCompoundDrawable(rightView, drawable, mRightIconGravity)
        return this
    }

    val rightIcon: Drawable?
        get() = getTextCompoundDrawable(rightView, mRightIconGravity)

    /**
     * 设置标题的图标大小
     */
    fun setTitleIconSize(width: Int, height: Int): TitleBar {
        mTitleIconWidth = width
        mTitleIconHeight = height
        setDrawableSize(titleIcon, width, height)
        return this
    }

    /**
     * 设置左标题的图标大小
     */
    fun setLeftIconSize(width: Int, height: Int): TitleBar {
        mLeftIconWidth = width
        mLeftIconHeight = height
        setDrawableSize(leftIcon, width, height)
        return this
    }

    /**
     * 设置右标题的图标大小
     */
    fun setRightIconSize(width: Int, height: Int): TitleBar {
        mRightIconWidth = width
        mRightIconHeight = height
        setDrawableSize(rightIcon, width, height)
        return this
    }

    /**
     * 设置标题的文字和图标间距
     */
    fun setTitleIconPadding(padding: Int): TitleBar {
        titleView.compoundDrawablePadding = padding
        return this
    }

    /**
     * 设置左标题的文字和图标间距
     */
    fun setLeftIconPadding(padding: Int): TitleBar {
        leftView.compoundDrawablePadding = padding
        return this
    }

    /**
     * 设置右标题的文字和图标间距
     */
    fun setRightIconPadding(padding: Int): TitleBar {
        rightView.compoundDrawablePadding = padding
        return this
    }

    /**
     * 设置标题的图标着色器
     */
    fun setTitleIconTint(color: Int): TitleBar {
        mTitleIconTint = color
        setDrawableTint(titleIcon, color)
        return this
    }

    /**
     * 设置左标题的图标着色器
     */
    fun setLeftIconTint(color: Int): TitleBar {
        mLeftIconTint = color
        setDrawableTint(leftIcon, color)
        return this
    }

    /**
     * 设置右标题的图标着色器
     */
    fun setRightIconTint(color: Int): TitleBar {
        mRightIconTint = color
        setDrawableTint(rightIcon, color)
        return this
    }

    /**
     * 清楚标题的图标着色器
     */
    fun clearTitleIconTint(): TitleBar {
        mTitleIconTint = TitleBarSupport.NO_COLOR
        clearDrawableTint(titleIcon!!)
        return this
    }

    /**
     * 清楚左标题的图标着色器
     */
    fun clearLeftIconTint(): TitleBar {
        mLeftIconTint = TitleBarSupport.NO_COLOR
        clearDrawableTint(leftIcon!!)
        return this
    }

    /**
     * 清楚右标题的图标着色器
     */
    fun clearRightIconTint(): TitleBar {
        mRightIconTint = TitleBarSupport.NO_COLOR
        clearDrawableTint(rightIcon!!)
        return this
    }

    /**
     * 设置标题的图标显示重心
     */
    fun setTitleIconGravity(gravity: Int): TitleBar {
        val drawable = titleIcon
        mTitleIconGravity = gravity
        if (drawable != null) {
            setTextCompoundDrawable(titleView, drawable, gravity)
        }
        return this
    }

    /**
     * 设置左标题的图标显示重心
     */
    fun setLeftIconGravity(gravity: Int): TitleBar {
        val drawable = leftIcon
        mLeftIconGravity = gravity
        if (drawable != null) {
            setTextCompoundDrawable(leftView, drawable, gravity)
        }
        return this
    }

    /**
     * 设置右标题的图标显示重心
     */
    fun setRightIconGravity(gravity: Int): TitleBar {
        val drawable = rightIcon
        mRightIconGravity = gravity
        if (drawable != null) {
            setTextCompoundDrawable(rightView, drawable, gravity)
        }
        return this
    }

    /**
     * 设置左标题的背景状态选择器
     */
    fun setLeftBackground(id: Int): TitleBar {
        return setLeftBackground(id.getDrawable())
    }

    fun setLeftBackground(drawable: Drawable?): TitleBar {
        leftView.setBackground(drawable)
        return this
    }

    /**
     * 设置右标题的背景状态选择器
     */
    fun setRightBackground(id: Int): TitleBar {
        return setRightBackground(id.getDrawable())
    }

    fun setRightBackground(drawable: Drawable?): TitleBar {
        rightView.setBackground(drawable)
        return this
    }

    /**
     * 设置分割线是否显示
     */
    fun setLineVisible(visible: Boolean): TitleBar {
        lineView.visibility = if (visible) VISIBLE else INVISIBLE
        return this
    }

    /**
     * 设置标题是否显示
     */
    fun setTitleVisible(visible: Boolean): TitleBar {
        titleView.visibility = if (visible) VISIBLE else INVISIBLE
        return this
    }

    /**
     * 设置右边是否显示
     */
    fun setRightVisible(visible: Boolean): TitleBar {
        rightView.visibility = if (visible) VISIBLE else INVISIBLE
        return this
    }

    /**
     * 设置左边是否显示
     */
    fun setLeftVisible(visible: Boolean): TitleBar {
        leftView.visibility = if (visible) VISIBLE else INVISIBLE
        return this
    }

    /**
     * 设置分割线的颜色
     */
    fun setLineColor(color: Int): TitleBar {
        return setLineDrawable(ColorDrawable(color))
    }

    fun setLineDrawable(drawable: Drawable?): TitleBar {
        lineView.setBackground(drawable!!)
        return this
    }

    /**
     * 设置分割线的大小
     */
    fun setLineSize(px: Int): TitleBar {
        val layoutParams = lineView.layoutParams
        layoutParams.height = px
        lineView.layoutParams = layoutParams
        return this
    }

    /**
     * 设置标题重心
     */
    @SuppressLint("RtlHardcoded")
    fun setTitleGravity(gravity: Int): TitleBar {
        var gravity = gravity
        gravity = getAbsoluteGravity(this, gravity)

        // 如果标题的重心为左，那么左边就不能有内容
        if (gravity == Gravity.LEFT &&
            isContainContent(
                if (isLayoutRtl(context)) rightView else leftView
            )
        ) {
            Log.e(
                LOG_TAG,
                "Title center of gravity for the left, the left title can not have content"
            )
            return this
        }

        // 如果标题的重心为右，那么右边就不能有内容
        if (gravity == Gravity.RIGHT &&
            isContainContent(
                if (isLayoutRtl(context)) leftView else rightView
            )
        ) {
            Log.e(
                LOG_TAG,
                "Title center of gravity for the right, the right title can not have content"
            )
            return this
        }
        val params = titleView.layoutParams as LayoutParams
        params.gravity = gravity
        titleView.layoutParams = params
        return this
    }

    /**
     * 设置子 View 内间距
     */
    fun setChildPadding(horizontalPadding: Int, verticalPadding: Int): TitleBar {
        mHorizontalPadding = horizontalPadding
        mVerticalPadding = verticalPadding
        leftView.setPadding(
            horizontalPadding,
            verticalPadding,
            horizontalPadding,
            verticalPadding
        )
        titleView.setPadding(
            horizontalPadding,
            verticalPadding,
            horizontalPadding,
            verticalPadding
        )
        rightView.setPadding(
            horizontalPadding,
            verticalPadding,
            horizontalPadding,
            verticalPadding
        )
        return this
    }

    companion object {
        private const val LOG_TAG = "TitleBar"

        /** 默认初始化器  */
        private var sGlobalStyle: ITitleBarStyle = LightBarStyle()

        /**
         * 设置默认初始化器
         */
        @JvmStatic
        fun setDefaultStyle(style: ITitleBarStyle) {
            sGlobalStyle = style
        }
    }

    init {

        val array = context.obtainStyledAttributes(
            attrs,
            R.styleable.TitleBar,
            0,
            R.style.TitleBarStyle
        )
        currentStyle = when (array.getInt(R.styleable.TitleBar_barStyle, 0)) {
            0x10 -> LightBarStyle()
            0x30 -> TransparentBarStyle()
            0x40 -> RippleBarStyle()
            else -> sGlobalStyle
        }
        titleView = currentStyle.createTitleView(context)
        leftView = currentStyle.createLeftView(context)
        rightView = currentStyle.createRightView(context)
        lineView = currentStyle.createLineView(context)
        titleView.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            Gravity.CENTER_HORIZONTAL
        )
        leftView.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            Gravity.START
        )
        rightView.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            Gravity.END
        )
        lineView.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            currentStyle.getLineSize(context),
            Gravity.BOTTOM
        )

        onCommonSetting()
        // 设置图标显示的重心
        setTitleIconGravity(
            array.getInt(
                R.styleable.TitleBar_titleIconGravity,
                currentStyle.getTitleIconGravity(context)
            )
        )
        setLeftIconGravity(
            array.getInt(
                R.styleable.TitleBar_leftIconGravity,
                currentStyle.getLeftIconGravity(context)
            )
        )
        setRightIconGravity(
            array.getInt(
                R.styleable.TitleBar_rightIconGravity,
                currentStyle.getRightIconGravity(context)
            )
        )

        // 设置图标显示的大小
        setTitleIconSize(
            array.getDimensionPixelSize(
                R.styleable.TitleBar_titleIconWidth,
                currentStyle.getTitleIconWidth(context)
            ),
            array.getDimensionPixelSize(
                R.styleable.TitleBar_titleIconHeight,
                currentStyle.getTitleIconHeight(context)
            )
        )
        setLeftIconSize(
            array.getDimensionPixelSize(
                R.styleable.TitleBar_leftIconWidth,
                currentStyle.getLeftIconWidth(context)
            ),
            array.getDimensionPixelSize(
                R.styleable.TitleBar_leftIconHeight,
                currentStyle.getLeftIconHeight(context)
            )
        )
        setRightIconSize(
            array.getDimensionPixelSize(
                R.styleable.TitleBar_rightIconWidth,
                currentStyle.getRightIconWidth(context)
            ),
            array.getDimensionPixelSize(
                R.styleable.TitleBar_rightIconHeight,
                currentStyle.getRightIconHeight(context)
            )
        )

        // 设置文字和图标之间的间距
        setTitleIconPadding(
            array.getDimensionPixelSize(
                R.styleable.TitleBar_titleIconPadding,
                currentStyle.getTitleIconPadding(context)
            )
        )
        setLeftIconPadding(
            array.getDimensionPixelSize(
                R.styleable.TitleBar_leftIconPadding,
                currentStyle.getLeftIconPadding(context)
            )
        )
        setRightIconPadding(
            array.getDimensionPixelSize(
                R.styleable.TitleBar_rightIconPadding,
                currentStyle.getRightIconPadding(context)
            )
        )

        // 标题设置
        if (array.hasValue(R.styleable.TitleBar_title)) {
            setTitle(
                currentStyle.getTitle(context)
            )
        }
        if (array.hasValue(R.styleable.TitleBar_leftTitle)) {
            setLeftTitle(
                currentStyle.getLeftTitle(
                    context
                )
            )
        }
        if (array.hasValue(R.styleable.TitleBar_rightTitle)) {
            setRightTitle(
                currentStyle.getRightTitle(
                    context
                )
            )
        }

        // 图标着色设置
        if (array.hasValue(R.styleable.TitleBar_titleIconTint)) {
            setTitleIconTint(array.getColor(R.styleable.TitleBar_titleIconTint, 0))
        }
        if (array.hasValue(R.styleable.TitleBar_leftIconTint)) {
            setLeftIconTint(array.getColor(R.styleable.TitleBar_leftIconTint, 0))
        }
        if (array.hasValue(R.styleable.TitleBar_rightIconTint)) {
            setRightIconTint(array.getColor(R.styleable.TitleBar_rightIconTint, 0))
        }

        // 图标设置
        if (array.hasValue(R.styleable.TitleBar_titleIcon)) {
            setTitleIcon(
                array.getResourceId(
                    R.styleable.TitleBar_titleIcon,
                    0
                ).getDrawable()
            )
        }
        ("TitleBar_leftIcon：" + array.hasValue(R.styleable.TitleBar_leftIcon)).log()
        if (array.hasValue(R.styleable.TitleBar_leftIcon)) {
            setLeftIcon(
                array.getResourceId(
                    R.styleable.TitleBar_leftIcon,
                    0
                ).getDrawable()
            )
        }
        if (array.hasValue(R.styleable.TitleBar_rightIcon)) {
            setRightIcon(
                array.getResourceId(
                    R.styleable.TitleBar_rightIcon,
                    0
                ).getDrawable()
            )
        }

        // 文字颜色设置
        setTitleColor(
            if (array.hasValue(R.styleable.TitleBar_titleColor)) array.getColorStateList(R.styleable.TitleBar_titleColor) else currentStyle.getTitleColor(
                context
            )
        )
        setLeftTitleColor(
            if (array.hasValue(R.styleable.TitleBar_leftTitleColor)) array.getColorStateList(
                R.styleable.TitleBar_leftTitleColor
            ) else currentStyle.getLeftTitleColor(context)
        )
        setRightTitleColor(
            if (array.hasValue(R.styleable.TitleBar_rightTitleColor)) array.getColorStateList(
                R.styleable.TitleBar_rightTitleColor
            ) else currentStyle.getRightTitleColor(context)
        )

        // 文字大小设置
        setTitleSize(
            TypedValue.COMPLEX_UNIT_PX,
            (if (array.hasValue(R.styleable.TitleBar_titleSize)) array.getDimensionPixelSize(
                R.styleable.TitleBar_titleSize,
                0
            ) else currentStyle.getTitleSize(context)).toFloat()
        )
        val leftTileSize = if (array.hasValue(R.styleable.TitleBar_leftTitleSize)) {
            array.getDimensionPixelSize(R.styleable.TitleBar_leftTitleSize, 0)
        } else {
            currentStyle.getLeftTitleSize(context)
        }
        setLeftTitleSize(TypedValue.COMPLEX_UNIT_PX, leftTileSize.toFloat())
        setRightTitleSize(
            TypedValue.COMPLEX_UNIT_PX,
            (if (array.hasValue(R.styleable.TitleBar_rightTitleSize)) array.getDimensionPixelSize(
                R.styleable.TitleBar_rightTitleSize,
                0
            ) else currentStyle.getRightTitleSize(context)).toFloat()
        )

        // 文字样式设置
        val titleStyle = if (array.hasValue(R.styleable.TitleBar_titleStyle)) array.getInt(
            R.styleable.TitleBar_titleStyle,
            Typeface.NORMAL
        ) else currentStyle.getTitleStyle(context)
        setTitleStyle(currentStyle.getTitleTypeface(context, titleStyle), titleStyle)
        val leftTitleStyle = if (array.hasValue(R.styleable.TitleBar_leftTitleStyle)) array.getInt(
            R.styleable.TitleBar_leftTitleStyle,
            Typeface.NORMAL
        ) else currentStyle.getLeftTitleStyle(context)
        setLeftTitleStyle(
            currentStyle.getLeftTitleTypeface(context, leftTitleStyle),
            leftTitleStyle
        )
        val rightTitleStyle =
            if (array.hasValue(R.styleable.TitleBar_rightTitleStyle)) array.getInt(
                R.styleable.TitleBar_rightTitleStyle,
                Typeface.NORMAL
            ) else currentStyle.getRightTitleStyle(context)
        setRightTitleStyle(
            currentStyle.getRightTitleTypeface(context, rightTitleStyle),
            rightTitleStyle
        )

        // 标题重心设置
        if (array.hasValue(R.styleable.TitleBar_titleGravity)) {
            setTitleGravity(array.getInt(R.styleable.TitleBar_titleGravity, Gravity.NO_GRAVITY))
        }

        // 背景设置
        if (array.hasValue(R.styleable.TitleBar_android_background)) {
            setBackground(currentStyle.getTitleBarBackground(context))
        }
        if (array.hasValue(R.styleable.TitleBar_leftBackground)) {
            setLeftBackground(
                currentStyle.getLeftTitleBackground(
                    context
                )
            )
        }
        if (array.hasValue(R.styleable.TitleBar_rightBackground)) {
            setRightBackground(
                currentStyle.getRightTitleBackground(
                    context
                )
            )
        }

        // 分割线设置
        setLineVisible(
            array.getBoolean(
                R.styleable.TitleBar_lineVisible,
                currentStyle.isLineVisible(context)
            )
        )
        setLeftVisible(
            array.getBoolean(
                R.styleable.TitleBar_isShowLeft,
                currentStyle.isShowLeft(context)
            )
        )
        setTitleVisible(
            array.getBoolean(
                R.styleable.TitleBar_isShowTile,
                currentStyle.isShowTile(context)
            )
        )
        setRightVisible(
            array.getBoolean(
                R.styleable.TitleBar_isShowRight,
                currentStyle.isShowRight(context)
            )
        )
        if (array.hasValue(R.styleable.TitleBar_lineDrawable)) {
            setLineDrawable(
                currentStyle.getLineDrawable(
                    context
                )
            )
        }
        if (array.hasValue(R.styleable.TitleBar_lineSize)) {
            setLineSize(array.getDimensionPixelSize(R.styleable.TitleBar_lineSize, 0))
        }

        // 设置子控件的内间距
        mHorizontalPadding = array.getDimensionPixelSize(
            R.styleable.TitleBar_childPaddingHorizontal,
            currentStyle.getChildHorizontalPadding(
                context
            )
        )
        mVerticalPadding = array.getDimensionPixelSize(
            R.styleable.TitleBar_childPaddingVertical,
            currentStyle.getChildVerticalPadding(context)
        )
        setChildPadding(mHorizontalPadding, mVerticalPadding)

        // 回收 TypedArray 对象
        array.recycle()
        addView(titleView, 0)
        addView(leftView, 1)
        addView(rightView, 2)
        addView(lineView, 3)
        addOnLayoutChangeListener(this)

        // 如果当前是布局预览模式
        if (isInEditMode) {
            measure(0, 0)
            titleView.measure(0, 0)
            leftView.measure(0, 0)
            rightView.measure(0, 0)
            val horizontalMargin = max(
                leftView.measuredWidth,
                rightView.measuredWidth
            ) + mHorizontalPadding
            val layoutParams = titleView.layoutParams as MarginLayoutParams
            layoutParams.setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }
    }

    /**
     * 深度定制模式
     */
    fun customModel() {
        lineView.visibility = GONE
        removeView(leftView)
        removeView(rightView)
    }

    private val mItemViews by lazy {
        ArrayList<View>()
    }

    /**
     * 添加item
     */
    @JvmOverloads
    fun addItem(isLeft: Boolean, item: View, index: Int? = null) {
        if (isLeft) {
            addItemView(leftButtons, item, index)
        } else {
            addItemView(rightButtons, item, index)
        }
    }

    private fun addItemView(parent: LinearLayoutCompat, item: View, index: Int? = null) {
        if (index == null) {
            parent.addView(item)
        } else {
            parent.addView(item, index)
        }
        mItemViews.add(item)
    }

    fun createIcon(id: Int? = null, onClick: OnClickListener? = null): FTitleImageView {
        val backItem = FTitleImageView(context)
        if (onClick != null) {
            backItem.setOnClickListener(onClick)
        }
        if (id != null) {
            backItem.setImageResource(id)
        }
        return backItem
    }

    private fun createRightButterParent(): LinearLayoutCompat {
        val linearLayout = LinearLayoutCompat(context).apply {
            orientation = LinearLayoutCompat.HORIZONTAL
        }
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            Gravity.END
        )
        linearLayout.layoutDirection = LAYOUT_DIRECTION_RTL
        addView(linearLayout, layoutParams)
        return linearLayout
    }

    private fun createLeftButterParent(): LinearLayoutCompat {
        val linearLayout = LinearLayoutCompat(context).apply {
            orientation = LinearLayoutCompat.HORIZONTAL
        }
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            Gravity.START
        )
        addView(linearLayout, layoutParams)
        return linearLayout
    }

    /**
     * 重置左右定制按钮
     */
    fun resetCustom() {
        leftButtons.removeAllViews()
        rightButtons.removeAllViews()
        mItemViews.clear()
    }


    var mCustomTitleView: View? = null

    /**
     * 定制标题View
     */
    fun setTitleView(view: View) {
        removeView(titleView)
        mCustomTitleView?.let {
            removeView(mCustomTitleView)
        }
        view.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            Gravity.CENTER_HORIZONTAL
        )
        mCustomTitleView = view
        addView(mCustomTitleView, 0)
    }

    /**
     * 恢复标题默认TextView
     */
    fun resumeTitleView() {
        removeView(titleView)
        mCustomTitleView?.let {
            removeView(mCustomTitleView)
        }
        addView(titleView, 0)
    }

    /**
     * 定制标题View
     */
    fun setTitleView(@LayoutRes id: Int) {
        try {
            setTitleView(inflate(context, id, null))
        } catch (e: Exception) {
        }
    }
}

fun dp2px(value: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        Resources.getSystem()
            .displayMetrics
    ).toInt()
}

fun px2dp(value: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        value,
        Resources.getSystem()
            .displayMetrics
    )
}