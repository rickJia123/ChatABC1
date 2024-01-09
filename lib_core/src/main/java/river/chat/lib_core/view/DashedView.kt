package river.chat.lib_core.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import river.chat.lib_core.R
import river.chat.lib_core.utils.exts.getColor

/**
 * Created by beiyongChao on 2023/12/21
 * Description:
 */

//rick todo 不需要写死属性
class DashedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val path = Path()
        path.moveTo(0f, (height - 1).toFloat())
        path.lineTo(width.toFloat(), (height - 1).toFloat())
        path.moveTo(0f, 1f)
        path.lineTo(width.toFloat(), 1f)

        val paint = Paint()
        paint.color = R.color.front_2_BgColor.getColor()
        paint.strokeWidth = 5f

        paint.pathEffect = CornerPathEffect(5f)

        canvas?.drawPath(path, paint)
    }
}