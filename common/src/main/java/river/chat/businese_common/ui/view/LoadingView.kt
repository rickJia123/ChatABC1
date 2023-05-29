package river.chat.businese_common.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.databinding.DataBindingUtil
import river.chat.common.R
import river.chat.common.databinding.ViewLoadingBinding
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2023/3/15
 * Description:
 */
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    private var animDuration = 200L
    private var isLoading = false
    private val viewBinding: ViewLoadingBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.view_loading,
        this,
        true
    )


    init {
        start()

    }


    fun toggle() {
        if (isLoading) {
            stop()
        } else {
            start()
        }
    }

    fun start() {
        val anim =
            RotateAnimation(0f, 360f, viewBinding.ivLoading.pivotX, viewBinding.ivLoading.pivotY)
        anim.duration = animDuration
        anim.repeatCount = RotateAnimation.INFINITE
        anim.repeatMode = RotateAnimation.RESTART
        anim.interpolator = LinearInterpolator()
        viewBinding.ivLoading.startAnimation(anim)
        isLoading = true
    }

    fun stop() {
        viewBinding.ivLoading.clearAnimation()
        isLoading = false
    }

}