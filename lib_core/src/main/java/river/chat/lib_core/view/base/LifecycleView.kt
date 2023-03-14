package river.chat.lib_core.view.base

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.*

/**
 * Created by beiyongChao on 2022/11/8
 * Description: 绑定生命周期的view
 */
open class LifecycleView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), LifecycleObserver {
    init {
        (context as? AppCompatActivity)?.lifecycle?.addObserver(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        Log.e("rick LifecycleView：", "LifecycleWindow :onFinishInflate")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.e("rick LifecycleView：", "LifecycleWindow :onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.e("rick LifecycleView：", "LifecycleWindow :onDetachedFromWindow")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        Log.e("rick LifecycleView：", "LifecycleWindow :onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        Log.e("rick LifecycleView：", "LifecycleWindow :onDestroy")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        Log.e("rick LifecycleView：", "LifecycleWindow :onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        Log.e("rick LifecycleView：", "LifecycleWindow :onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        Log.e("rick LifecycleView：", "LifecycleWindow :onResume")
    }
}
