package river.chat.lib_core.view.title

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import river.chat.lib_core.R
import river.chat.lib_core.databinding.ViewTitleBinding
import river.chat.lib_core.utils.exts.getDrawable
import river.chat.lib_core.utils.exts.singleClick

/**
 * Create by Circle on 2019/7/18
 */
class RiverTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    /**
     * 左侧控件点击
     */
    var leftClick: () -> Unit = {
        if (context is Activity) {
            context.finish()
        }
    }

    /**
     * 标题栏点击
     */
    var titleClick: () -> Unit = {
    }

    /**
     * 右侧控件点击
     */
    var rightClick: () -> Unit = {
    }


    private var title: String? = null
    private var titleTextColor: Int = Color.WHITE
    private var leftImage: Int = R.drawable.back_title_white
    private var leftImageVisible = true

    private var leftText: String? = null
    private var leftTextColor = Color.TRANSPARENT

    private var rightImage: Int = 0
    private var rightText: String? = null
    private var rightTextColor = Color.TRANSPARENT

    private val viewBinding: ViewTitleBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_title,
        this,
        true
    )

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RiverTitle)
        typedArray?.let {

            title = typedArray.getString(R.styleable.RiverTitle_tb_title)
            setTitle(title)

            if (typedArray.hasValue(R.styleable.RiverTitle_tb_titleTextColor)) {
                titleTextColor =
                    typedArray.getColor(R.styleable.RiverTitle_tb_titleTextColor, Color.TRANSPARENT)
                setTitleTextColor(titleTextColor)
            }


            leftImage = typedArray.getResourceId(
                R.styleable.RiverTitle_tb_leftImage,
                R.drawable.back_title_white
            )
            setLeftImage(leftImage)

            leftImageVisible =
                typedArray.getBoolean(R.styleable.RiverTitle_tb_leftImageVisible, true)
            setLeftImageVisible(leftImageVisible)

            if (typedArray.hasValue(R.styleable.RiverTitle_tb_leftText)) {
                leftText = typedArray.getString(R.styleable.RiverTitle_tb_leftText)
                setLeftText(leftText)
            }

            if (typedArray.hasValue(R.styleable.RiverTitle_tb_leftTextColor)) {
                leftTextColor =
                    typedArray.getColor(R.styleable.RiverTitle_tb_leftTextColor, Color.TRANSPARENT)
                setLeftTextColor(leftTextColor)
            }

            if (typedArray.hasValue(R.styleable.RiverTitle_tb_rightImage)) {
                rightImage = typedArray.getResourceId(
                    R.styleable.RiverTitle_tb_rightImage,
                    0
                )
                setRightImage(rightImage)
            }

            if (typedArray.hasValue(R.styleable.RiverTitle_tb_rightText)) {
                rightText = typedArray.getString(R.styleable.RiverTitle_tb_rightText)
                setRightText(rightText)
            }
            if (typedArray.hasValue(R.styleable.RiverTitle_tb_rightTextColor)) {
                rightTextColor =
                    typedArray.getColor(R.styleable.RiverTitle_tb_rightTextColor, Color.TRANSPARENT)
                setRightTextColor(rightTextColor)
            }
        }
        initClick()
    }

    /**
     * 设置标题
     */
    fun setTitle(text: String?): RiverTitle {
        viewBinding.tvTitle.text = text ?: ""
        return this
    }

    /**
     * 设置左侧文本颜色
     */
    fun setTitleTextColor(@ColorInt color: Int): RiverTitle {
        viewBinding.tvTitle.setTextColor(color)
        return this
    }


    /**
     * 设置标题
     */
    fun setTitle(@StringRes resId: Int): RiverTitle {
        viewBinding.tvTitle.setText(resId)
        return this
    }


    /**
     * 设置左侧按钮
     */
    fun setLeftImage(resId: Int): RiverTitle {
        viewBinding.ivLeft.setImageResource(resId)
        return this
    }

    /**
     * 设置左侧按钮
     */
    fun setLeftImage(drawable: Drawable): RiverTitle {
        viewBinding.ivLeft.setImageDrawable(drawable)
        return this
    }

    /**
     * 设置左侧按钮是否显示 默认显示
     */
    fun setLeftImageVisible(leftImageVisible: Boolean) {
        viewBinding.ivLeft.visibility = if (leftImageVisible) View.VISIBLE else View.GONE
    }

    /**
     * 设置左侧文本
     */
    fun setLeftText(text: String?): RiverTitle {
        if (TextUtils.isEmpty(text)) {
            viewBinding.tvLeft.visibility = View.GONE
        } else {
            viewBinding.tvLeft.visibility = View.VISIBLE
            viewBinding.tvLeft.text = text
        }
        return this
    }

    /**
     * 设置左侧文本
     */
    fun setLeftText(@StringRes resId: Int): RiverTitle {
        if (resId == 0) {
            viewBinding.tvLeft.visibility = View.GONE
        } else {
            viewBinding.tvLeft.visibility = View.VISIBLE
            viewBinding.tvLeft.setText(resId)
            setLeftImageVisible(false)
        }
        return this
    }


    /**
     * 设置左侧文本颜色
     */
    fun setLeftTextColor(@ColorInt color: Int): RiverTitle {
        viewBinding.tvLeft.setTextColor(color)
        return this
    }

    /**
     * 设置右侧按钮
     */
    fun setRightImage(@DrawableRes resId: Int): RiverTitle {
        viewBinding.ivRight.visibility = View.VISIBLE
        viewBinding.ivRight.setImageResource(resId)
        return this
    }

    /**
     * 设置右侧按钮是否显示 默认显示
     */
    fun setRightImageVisible(visibility: Int) {
        viewBinding.ivRight.visibility = visibility
    }

    /**
     * 设置右侧文本
     */
    fun setRightText(text: String?): RiverTitle {
        if (TextUtils.isEmpty(text)) {
            viewBinding.tvRight.visibility = View.GONE
        } else {
            viewBinding.tvRight.visibility = View.VISIBLE
            viewBinding.tvRight.text = text
        }
        return this
    }


    /**
     * 设置右侧文本
     */
    fun setRightText(@StringRes resId: Int): RiverTitle {
        if (resId == 0) {
            viewBinding.tvLeft.visibility = View.GONE
        } else {
            viewBinding.tvLeft.visibility = View.VISIBLE
            viewBinding.tvLeft.setText(resId)
        }
        return this
    }

    /**
     * 设置右侧文本颜色
     */
    fun setRightTextColor(color: Int): RiverTitle {
        viewBinding.tvRight.setTextColor(color)
        return this
    }

    /**
     * 设置右侧文本颜色
     */
    fun setRightTextColor(color: ColorStateList?): RiverTitle {
        viewBinding.tvRight.setTextColor(color)
        return this
    }

    /**
     * 设置右侧文本背景
     */
    fun setRightTextBack(res: Int): RiverTitle {
        viewBinding.tvRight.background = res.getDrawable()
        return this
    }


    fun initClick() {
        viewBinding.ivLeft.singleClick { leftClick.invoke() }
        viewBinding.tvLeft.singleClick { leftClick.invoke() }

        viewBinding.ivRight.singleClick { rightClick.invoke() }
        viewBinding.tvRight.singleClick { rightClick.invoke() }

        viewBinding.tvTitle.singleClick { titleClick.invoke() }
        viewBinding.tvSubTitle.singleClick { titleClick.invoke() }
    }


    /**
     * 设置右侧文本
     */
    fun setSubTitleText(text: String?): RiverTitle {
        if (TextUtils.isEmpty(text)) {
            viewBinding.tvSubTitle.visibility = View.GONE
        } else {
            viewBinding.tvSubTitle.visibility = View.VISIBLE
            viewBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
            viewBinding.tvSubTitle.text = text
        }
        return this
    }


    fun getRightText() = viewBinding.tvRight.text.toString().trim()

    fun getRightTextView() = viewBinding.tvRight

    fun getRightIcon() = viewBinding.ivRight
    fun getLeftIcon() = viewBinding.ivLeft

}
