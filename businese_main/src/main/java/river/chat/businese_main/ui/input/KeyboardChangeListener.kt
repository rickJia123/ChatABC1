package river.chat.businese_main.ui.input

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import river.chat.lib_core.utils.log.LogUtil

/**
 * CreateDate: 2/23/22
 * CreateTime: 4:02 PM
 *
 * Author : zang.peng
 * Email : zangp_hq@163.com
 * Version : 1.0
 */
class KeyboardChangeListener(private val activity: Activity?) : ViewTreeObserver.OnGlobalLayoutListener {
    private val TAG = "ListenerHandler"
    private var mContentView // 当前界面的根视图
            : View? = null
    private var mPreHeight // 改变之前根视图的高度
            = 0
    private var mKeyBoardListen: KeyBoardListener? = null

    private var mCurrentTime = 0L
    private var mDialogIsShow = false

    init {
        if (activity == null) {
            LogUtil.i("$TAG contextObj is null")
        } else {
            mContentView = findContentView(activity)
            mCurrentTime = System.currentTimeMillis()
            if (mContentView != null) {
                addContentTreeObserver()
            }
        }
    }

    private fun findContentView(activity: Activity): View {
        return activity.findViewById(android.R.id.content)
    }

    private fun addContentTreeObserver() {
        mContentView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        val rect = Rect()
        activity?.window?.decorView?.getWindowVisibleDisplayFrame(rect)
        val currHeight: Int = mContentView?.height ?: 0
        val visibleHeight = rect.bottom - rect.top

        // 软键盘隐藏，mDialogIsShow = false，则表示当前状态和现在状态一致，不需要处理
        if (visibleHeight >= currHeight * 0.8 && !mDialogIsShow) {
            return
        }

        // 软键盘显示
        if (visibleHeight < currHeight * 0.8) {
            mDialogIsShow = true
        } else {
            mDialogIsShow = false
            mKeyBoardListen?.onKeyboardChange(false, currHeight - visibleHeight)
        }
    }

    fun setKeyBoardListener(keyBoardListen: KeyBoardListener?) {
        mKeyBoardListen = keyBoardListen
    }

    // 资源释放，如果立即释放资源，那么监听将不起作用，导致监听不到
    fun destroy() {
        if (mContentView != null) {
            mContentView?.postDelayed({
                mContentView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            }, 200)
        }
    }

    interface KeyBoardListener {
        fun onKeyboardChange(isShow: Boolean, keyboardHeight: Int)
    }
}