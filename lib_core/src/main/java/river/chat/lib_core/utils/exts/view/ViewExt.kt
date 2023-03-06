package river.chat.lib_core.utils.exts

import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DimenRes
import river.chat.lib_core.app.BaseApplication
import river.chat.lib_core.utils.other.FastClickUtil

fun View.show() {
    if (!isShown && visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.hide() {
    hide(false)
}

fun View.hide(gone: Boolean) {
    if (isShown || visibility == View.VISIBLE) {
        visibility = if (gone) View.GONE else View.INVISIBLE
    }
}

/**
 * 计算view的大小
 */
fun Activity.backstageCreateView(layoutRes: Int): View {
    //将布局转化成view对象
    val viewBitmap = LayoutInflater.from(this).inflate(layoutRes, null)

    val manager = this.windowManager
    val outMetrics = DisplayMetrics()
    manager?.defaultDisplay?.getMetrics(outMetrics)
    val width = outMetrics.widthPixels
    val height = outMetrics.heightPixels

    //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
    // 整个View的大小 参数是左上角 和右下角的坐标
    viewBitmap.layout(0, 0, width, height)
    val measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
    val measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST)

    viewBitmap.measure(measuredWidth, measuredHeight)
    viewBitmap.layout(0, 0, viewBitmap.measuredWidth, viewBitmap.measuredHeight)
    return viewBitmap
}

fun View.backstageCreateBitmap(): Bitmap {
    this.isDrawingCacheEnabled = true
    this.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    this.drawingCacheBackgroundColor = Color.WHITE

    // 把一个View转换成图片
    val w = this.width
    val h = this.height

    val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val c = Canvas(bmp)

    c.drawColor(Color.WHITE)
    /** 如果不设置canvas画布为白色，则生成透明  */

    this.layout(0, 0, w, h)
    this.draw(c)
    return bmp
}

fun View.getViewBitmap(): Bitmap? {
    this.clearFocus()
    this.isPressed = false
    val willNotCache = this.willNotCacheDrawing()
    this.setWillNotCacheDrawing(false)
    val color = this.drawingCacheBackgroundColor
    this.drawingCacheBackgroundColor = 0
    if (color != 0) {
        this.destroyDrawingCache()
    }
    this.buildDrawingCache()
    val cacheBitmap = this.drawingCache ?: return null
    val bitmap = Bitmap.createBitmap(cacheBitmap)
    this.destroyDrawingCache()
    this.setWillNotCacheDrawing(willNotCache)
    this.drawingCacheBackgroundColor = color
    return bitmap
}

fun View.getDimensSize(@DimenRes res: Int): Int = BaseApplication.getInstance().resources.getDimensionPixelSize(res)

fun ProgressBar.updateProgress(progress: Int, animate: Boolean) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.setProgress(progress, animate)
        } else {
            this.progress = progress
        }

fun View.fitSampleBitmap(filePath: String, width: Int, height: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(filePath, options)
    options.inSampleSize = getFitInSampleSize(width, height, options)
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeFile(filePath, options)
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
 * 设置View的高度
 */
fun View.height(height: Int): View {
    val params = layoutParams
    params.height = height
    layoutParams = params
    return this
}

/**
 * 设置View高度，限制在min和max范围之内
 * @param h
 * @param min 最小高度
 * @param max 最大高度
 */
fun View.limitHeight(h: Int, min: Int, max: Int): View {
    val params = layoutParams
    when {
        h < min -> params.height = min
        h > max -> params.height = max
        else -> params.height = h
    }
    layoutParams = params
    return this
}

/**
 * 设置View的宽度
 */
fun View.width(width: Int): View {
    val params = layoutParams
    params.width = width
    layoutParams = params
    return this
}

/**
 * 设置View宽度，限制在min和max范围之内
 * @param w
 * @param min 最小宽度
 * @param max 最大宽度
 */
fun View.limitWidth(w: Int, min: Int, max: Int): View {
    val params = layoutParams
    when {
        w < min -> params.width = min
        w > max -> params.width = max
        else -> params.width = w
    }
    layoutParams = params
    return this
}

/**
 * 设置View的宽度和高度
 * @param width 要设置的宽度
 * @param height 要设置的高度
 */
fun View.widthAndHeight(width: Int, height: Int): View {
    val params = layoutParams
    params.width = width
    params.height = height
    layoutParams = params
    return this
}

/**
 * 设置View的margin
 * @param leftMargin 默认是0
 * @param topMargin 默认是0
 * @param rightMargin 默认是0
 * @param bottomMargin 默认是0
 */
fun View.margin(leftMargin: Int = 0, topMargin: Int = 0, rightMargin: Int = 0, bottomMargin: Int = 0): View {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.leftMargin = leftMargin
    params.topMargin = topMargin
    params.rightMargin = rightMargin
    params.bottomMargin = bottomMargin
    layoutParams = params
    return this
}

fun TextView.focusMarquee() {
    this.marqueeRepeatLimit = -1
    this.ellipsize = TextUtils.TruncateAt.MARQUEE
    this.maxLines = 1
    this.setSingleLine()
}

/**
 * 设置点击监听
 */
fun View.singleClick(action: (view: View) -> Unit) {
    setOnClickListener {
        if (!FastClickUtil.isFastClick())
            action(it)
    }
}

/**
 * 设置点击监听
 */
fun View.permissionCheckClick(action: (view: View) -> Unit) {
    setOnClickListener {
        if (!FastClickUtil.isFastClick())
            action(it)
    }
}


fun View.rotation(startValue: Float, endValue: Float) {
    val animator = ObjectAnimator.ofFloat(this, "rotation", startValue, endValue)
            .setDuration(400L)
    animator.start()
}




