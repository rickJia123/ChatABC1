package river.chat.lib_core.utils.exts.view

import android.content.Context
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


fun ViewPager.fixedSpeedScroller(duration: Int = 200) {
    try {
        val field = ViewPager::class.java.getDeclaredField("mScroller")
        field.isAccessible = true
        val scroller = FixedSpeedScroller(this.context, LinearInterpolator())
        field.set(this, scroller)
        scroller.setmDuration(duration)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

class FixedSpeedScroller : Scroller {
    private var mDuration = 1500

    constructor(context: Context) : super(context) {}

    constructor(context: Context, interpolator: Interpolator) : super(context, interpolator) {}

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    fun setmDuration(time: Int) {
        mDuration = time
    }

    fun getmDuration(): Int {
        return mDuration
    }
}



fun forceSelectTabPosition(position: Int, tableLayout: TabLayout) {
    val clz = tableLayout.javaClass
    try {
        val animateToTab = clz.getDeclaredMethod("selectTab", TabLayout.Tab::class.java)
        animateToTab.isAccessible = true
        animateToTab.invoke(tableLayout, tableLayout.getTabAt(position))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



