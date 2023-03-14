package river.chat.lib_core.view.title

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import river.chat.lib_core.R
import river.chat.lib_core.utils.exts.dp2px
import river.chat.lib_core.utils.longan.log

/**
 * @author Scott
 * 项目通用导航栏
 */
class CommonTitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TitleBar(context, attrs, defStyleAttr) {

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

    override fun onCommonSetting() {
        ("CommonTitleBar init：").log()
        /*整体设置*/
        setChildPadding(8.dp2px(), 4.dp2px())//设置内边距
        setLineVisible(false)//默认不展示底部分割线

        /*主标题设置*/
        setTitleSize(17.toFloat())//默认标题字体大小
        setTitleStyle(Typeface.BOLD)//默认标题加粗
        setTitleColor(ContextCompat.getColor(context, R.color.default_text))//默认标题颜色

        /*左边图标设置*/
        setLeftIconSize(36.dp2px(), 36.dp2px())//默认左图标尺寸
        setLeftIcon(R.mipmap.back_common)//默认左图标
//        setLeftIcon(R.drawable.ic_back_icon)//默认左图标
        setLeftTitleSize(17.toFloat())//默认标题字体大小
        setLeftTitleColor(ContextCompat.getColor(context, R.color.default_text))//默认标题颜色


        /*右边图标设置*/
        setRightIconSize(36.dp2px(), 36.dp2px())
        /*右边文本标题设置*/
        setRightTitleSize(17.toFloat())//默认标题字体大小
        setRightTitleColor(ContextCompat.getColor(context, R.color.default_text))//默认标题颜色
        setRightIconPadding(7.dp2px())
        val params = rightView.layoutParams as LayoutParams
        rightView.layoutParams = params.apply {
            rightMargin = 7.dp2px()//因为内边距，左右不能独立设置，为了兼容现有导航栏的样式，单独设置右边控件外边距
        }

        setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                super.onLeftClick(titleBar)
                leftClick()
            }

            override fun onRightClick(titleBar: TitleBar) {
                super.onRightClick(titleBar)
                rightClick()
            }

            override fun onTitleClick(titleBar: TitleBar) {
                super.onTitleClick(titleBar)
                titleClick()
            }
        })
    }


}