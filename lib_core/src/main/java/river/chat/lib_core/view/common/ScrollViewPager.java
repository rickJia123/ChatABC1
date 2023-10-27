package river.chat.lib_core.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by wuxiangyi on 2017/3/29.
 */

public class ScrollViewPager extends ViewPager {
    private boolean mScroll = true;
    private boolean mCanScrollHorizontally = true;

    OnTouchViewListener onTouchViewListener;//用于响应区域内的触摸事件（点区域内任何位置收回tag）

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ScrollViewPager(Context context) {
        super(context);
    }

    public void setCanScrollHorizontally(boolean canScrollHorizontally) {
        this.mCanScrollHorizontally = canScrollHorizontally;
    }


    public void setScroll(boolean scroll) {
        this.mScroll = scroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return mCanScrollHorizontally;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (!mScroll) {
            return false;
        } else {
            try {//#3330658 java.lang.IndexOutOfBoundsException
                //#2548 java.lang.IllegalArgumentException
                return super.onTouchEvent(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (onTouchViewListener != null) {
            onTouchViewListener.onTouchView();
        }
        if (!mScroll) {
            return false;
        } else {
            //#7310 java.lang.IllegalArgumentException
            try {
                return super.onInterceptTouchEvent(arg0);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    public void setOnTouchViewListener(OnTouchViewListener listener) {
        this.onTouchViewListener = listener;
    }

    public interface OnTouchViewListener {
        void onTouchView();
    }
}
