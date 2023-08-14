package river.chat.lib_core.utils.exts.view

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView

/**
 * Created on 2022/3/31
 * @author BeiYongchao
 * @descripyion: span 工具
 */

/**
 * 接口
 */
interface FdSpannableStringBuilder {
    //增加一段文字
    fun addText(text: String, method: (FdSpanBuilder.() -> Unit)? = null)
}

/**
 * span 构造器
 */
interface FdSpanBuilder {
    //设置文字颜色
    fun setColor(color: String)

    //设置文字大小
    fun setSize(size: Int)

    //设置点击事件
    fun onClick(useUnderLine: Boolean = true, onClick: (View) -> Unit)
}


class FdSpannableStringBuilderImpl : FdSpannableStringBuilder {
    private val builder = SpannableStringBuilder()

    var lastIndex: Int = 0


    var isClickable = false

    override fun addText(text: String, method: (FdSpanBuilder.() -> Unit)?) {
        val start = lastIndex
        builder.append(text)
        lastIndex += text.length
        val spanBuilder = FdSpanBuilderImpl()
        method?.let { spanBuilder.it() }
        spanBuilder.apply {
            onClickSpan?.let {
                builder.setSpan(it, start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                isClickable = true
            }
            if (!useUnderLine) {
                val noUnderlineSpan = NoUnderlineSpan()
                builder.setSpan(noUnderlineSpan, start, lastIndex, Spanned.SPAN_MARK_MARK)
            }
            foregroundColorSpan?.let {
                builder.setSpan(it, start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            absoluteSpan?.let {
                builder.setSpan(it, start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    fun build(): SpannableStringBuilder {
        return builder
    }
}

class FdSpanBuilderImpl : FdSpanBuilder {
    var foregroundColorSpan: ForegroundColorSpan? = null
    var onClickSpan: ClickableSpan? = null
    var absoluteSpan: AbsoluteSizeSpan? = null

    /**
     * 是否使用下划线
     */
    var useUnderLine = true

    override fun setColor(color: String) {
        foregroundColorSpan = ForegroundColorSpan(Color.parseColor(color))
    }

    override fun setSize(size: Int) {
        absoluteSpan = AbsoluteSizeSpan(size,true)
    }

    override fun onClick(useUnderLine: Boolean, onClick: (View) -> Unit) {
        onClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick(widget)
            }
        }
        this.useUnderLine = useUnderLine
    }
}

class NoUnderlineSpan : UnderlineSpan() {
    override fun updateDrawState(ds: TextPaint) {
        ds.color = ds.linkColor
        ds.isUnderlineText = false
    }
}


/**
 * 添加不同颜色/不同点击事件
 * tv.buildSpannableString {
 * addText("正常文本")
 * addText("《点击文本》"){
 * setColor("#颜色值")
 * onClick(false) {
 * //do some thing
 * }}}
 */
fun TextView.buildSpannableString(init: FdSpannableStringBuilder.() -> Unit) {
    val spanStringBuilderImpl = FdSpannableStringBuilderImpl()
    spanStringBuilderImpl.init()
    movementMethod = LinkMovementMethod.getInstance()
    text = spanStringBuilderImpl.build()
}
