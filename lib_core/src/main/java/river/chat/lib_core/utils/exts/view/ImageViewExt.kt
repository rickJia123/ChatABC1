package river.chat.lib_core.utils.exts.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import river.chat.lib_core.R


fun ImageView.loadSimple(resource: Any?) {
    this.load(resource) {
        crossfade(true)
    }
}

fun ImageView.loadCircle(resource: Any?, placeHolder: Int = R.mipmap.ic_placeholder) {
    this.load(resource) {
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}


fun getFitInSampleSize(reqWidth: Int, reqHeight: Int, options: BitmapFactory.Options): Int {
    var inSampleSize = 1
    if (options.outWidth > reqWidth || options.outHeight > reqHeight) {
        val widthRatio = Math.round(options.outWidth.toFloat() / reqWidth.toFloat())
        val heightRatio = Math.round(options.outHeight.toFloat() / reqHeight.toFloat())
        inSampleSize = Math.min(widthRatio, heightRatio)
    }
    return inSampleSize
}

fun ImageView.scaleBitmap(resource: Bitmap) {
    this.setImageBitmap(resource)
    //获取原图的宽高
    val width = resource.width
    val height = resource.height
    //获取imageView的宽
    val imageViewWidth = this.width

    //计算缩放比例
    val sy = (imageViewWidth * 0.1).toFloat() / (width * 0.1).toFloat()

    //计算图片等比例放大后的高
    val imageViewHeight = (height * sy).toInt()
    val params = this.layoutParams
    params.height = imageViewHeight
    this.layoutParams = params
}

/**
 * 获取View的截图, 支持获取整个RecyclerView列表的长截图
 * 注意：调用该方法时，请确保View已经测量完毕，如果宽高为0，则将抛出异常
 */
fun View.toBitmap(): Bitmap {
    if (measuredWidth == 0 || measuredHeight == 0) {
        throw RuntimeException("调用该方法时，请确保View已经测量完毕，如果宽高为0，则抛出异常以提醒！")
    }
    return when (this) {
        is RecyclerView -> {
            this.scrollToPosition(0)
            this.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )

            val bmp = Bitmap.createBitmap(width, measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)

            //draw default bg, otherwise will be black
            if (background != null) {
                background.setBounds(0, 0, width, measuredHeight)
                background.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            this.draw(canvas)
            //恢复高度
            this.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST)
            )
            bmp //return
        }
        else -> {
            val screenshot =
                Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_4444)
            val canvas = Canvas(screenshot)
            if (background != null) {
                background.setBounds(0, 0, width, measuredHeight)
                background.draw(canvas)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            draw(canvas)// 将 view 画到画布上
            screenshot //return
        }
    }
}

/**
 * 截取scrollview布局图片
 */
fun NestedScrollView.toBitmap(): Bitmap {
    var h = 0
    var bitmap: Bitmap? = null
    for (i in 0 until childCount) {
        h += getChildAt(i)
            .height
        //设置背景色，否则屏幕外部分布景是黑色的
        getChildAt(i)
            .setBackgroundColor(getResources().getColor(R.color.color_FFF3D6))
    }
    // 创建对应大小的bitmap
    bitmap = Bitmap.createBitmap(
        width,
        h,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}





