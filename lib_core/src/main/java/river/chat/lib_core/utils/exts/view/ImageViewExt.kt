package river.chat.lib_core.utils.exts.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.TextUtils
import android.view.PixelCopy
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import river.chat.lib_core.R
import river.chat.lib_core.utils.common.TimeUtils
import river.chat.lib_core.utils.longan.toastSystem


/**
 * 图片预加载
 */
fun preLoad(context: Context, resource: Any?) {
    val imageLoader = ImageLoader.Builder(context)
        .build()
// 预加载图片到缓存
    val request = ImageRequest.Builder(context)
        .data(resource) // 图片 URL
        .target(null) // 可以提供一个空的目标，因为我们只是预加载图片而不显示
        .build()

    imageLoader.enqueue(request)

}


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
                Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
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

fun View.captureView(window: Window?, bitmapCallback: (Bitmap) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Above Android O, use PixelCopy
        val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
        val location = IntArray(2)
        this.getLocationInWindow(location)
        window?.let {
            PixelCopy.request(
                it,
                Rect(location[0], location[1], location[0] + this.width, location[1] + this.height),
                bitmap,
                {
                    if (it == PixelCopy.SUCCESS) {
                        bitmapCallback.invoke(bitmap)
                    }
                },
                Handler(Looper.getMainLooper())
            )
        }

    } else {
        val tBitmap = Bitmap.createBitmap(
            this.width, this.height, Bitmap.Config.RGB_565
        )
        val canvas = Canvas(tBitmap)
        this.draw(canvas)
        canvas.setBitmap(null)
        bitmapCallback.invoke(tBitmap)
    }
}


/**
 * 截取scrollview布局图片
 */
fun NestedScrollView.toBitmap(): Bitmap {
    var h = 0
    var bitmap: Bitmap? = null
    for (i in 0 until this.childCount) {
        h += this.getChildAt(i).height
        this.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"))
    }
    bitmap = Bitmap.createBitmap(this.width, h, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    return bitmap
}


/**
 * 截取scrollview布局图片
 */
//fun NestedScrollView.toBitmap(): Bitmap {
//    var h = 0
//    var bitmap: Bitmap? = null
//    for (i in 0 until childCount) {
//        h += getChildAt(i)
//            .height
//        //设置背景色，否则屏幕外部分布景是黑色的
//        getChildAt(i)
//            .setBackgroundColor(getResources().getColor(R.color.color_FFF3D6))
//    }
//    // 创建对应大小的bitmap
//    bitmap = Bitmap.createBitmap(
//        width,
//        h,
//        Bitmap.Config.ARGB_8888
//    )
//    val canvas = Canvas(bitmap)
//    draw(canvas)
//    return bitmap
//}


/**
 *下载图片到相册
 */
fun saveBitmapToMediaStore(
    context: Context,
    bitmap: Bitmap?,
    fileName: String? = "AI绘图" + TimeUtils.montageSystemTime() + ".jpg"
): String? {
    var path = ""
    try {
        val cr = context.contentResolver
        val uri = MediaStore.Images.Media.insertImage(
            cr,
            bitmap,
            fileName,
            ""
        )
        if (!TextUtils.isEmpty(uri)) {
            // 因为Android4.4中限制了系统应用才有权限使用广播通知系统扫描SD卡。
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            // 所以通知系统重新扫描SD卡  解决方式：
            path = getAbsoluteImagePath(context, Uri.parse(uri)) ?: ""
            MediaScannerConnection.scanFile(
                context, arrayOf(path),
                null,
                null
            )
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE) //发通知刷新图库，解决保存本地后微信查不到图片的问题
            val uriScan = Uri.parse(path)
            intent.data = uriScan
            context.sendBroadcast(intent)
        }
        "图片已保存".toastSystem()
    } catch (ex: Exception) {
        ex.printStackTrace()
        "图片保存失败".toastSystem()
    }
    return path
}

fun getAbsoluteImagePath(context: Context, uri: Uri): String? {
    val path = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver
        .query(uri, path, null, null, null)
    val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    return cursor.getString(column_index)
}


