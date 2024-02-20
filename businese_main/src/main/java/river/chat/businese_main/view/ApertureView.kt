package river.chat.businese_main.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.LinearInterpolator
import androidx.core.graphics.withSave
import river.chat.lib_core.utils.exts.dp2px
import river.chat.lib_core.utils.longan.dp

/**
 *
 * @ClassName: apertureView
 * @Author: 史大拿
 * @CreateDate: 11/28/22$ 1:35 PM$
 * TODO
 */
class ApertureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        //        val DEF_WIDTH = 200.dp
//        val DEF_HEIGHT = DEF_WIDTH
        private val RADIUS = 10.dp
    }

    private var mHeight = 0f
    private var mWidth = 0f

    private val animator by lazy {
//        val animator = ObjectAnimator.ofFloat(this, "currentSpeed", 0f, 360f)
        val animator = ObjectAnimator.ofFloat(this, "currentSpeed", 0f, 100f)
        animator.repeatCount = -1
        animator.interpolator = LinearInterpolator()
        animator.duration = 2000L
        animator
    }

    init {
//         设置view圆角
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                // 设置圆角率为
                outline.setRoundRect(0, 0, view.width, view.height, RADIUS)
            }
        }
        clipToOutline = true

        animator.start()
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(mWidth.toInt(), widthMeasureSpec)
        val height = resolveSize(mHeight.toInt(), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }


    //
    private val color1 by lazy {
        LinearGradient(
            width * 1f,
            height / 3f,
            width * 1f,
            height * 1f,
            intArrayOf(Color.TRANSPARENT, Color.RED),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    private val color2 by lazy {
        LinearGradient(
            width / 3f,
            height / 3f,
            width / 3f,
            0f,
            intArrayOf(Color.TRANSPARENT, Color.GREEN),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }


    private var currentSpeed = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val rectF by lazy {
        val left = 0f + RADIUS / 2f
        val top = 0f + RADIUS / 2f
        val right = left + mWidth - RADIUS
        val bottom = top + mHeight - RADIUS
        RectF(left, top, right, bottom)
    }

    private val path by lazy {
        Path().also { it.addRoundRect(rectF, RADIUS, RADIUS, Path.Direction.CCW) }
    }

    override fun onDraw(canvas: Canvas) {
//

        var rightTopRate = mWidth / (mWidth + mHeight)
        var rightBottomRate = (mWidth + mHeight) / (mWidth + mHeight)
        var leftBottomRate = (mWidth * 2 + mHeight) / (mWidth + mHeight)
        var currentRate= currentSpeed / 100
        canvas.withSave {
            canvas.rotate(currentSpeed, width / 2f, height / 2f)
            val left1 = rectF.left + rectF.width() / 2f
            val right1 = rectF.right + rectF.width()
            val top1 = rectF.top + rectF.height() / 2f
            val bottom1 = rectF.bottom + rectF.height() / 2f
            // 绘制渐变view1
//            paint.shader = color1
//            canvas.drawRect(left1, top1, right1, bottom1, paint)
//            paint.shader = null

            // 绘制渐变view2
            paint.shader = color2
            canvas.drawRect(left1, top1, -right1, -bottom1, paint)
            paint.shader = null
        }

        canvas.drawRoundRect(rectF, RADIUS, RADIUS, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
//        mHeight = 65f.dp2px().toFloat()
    }

}