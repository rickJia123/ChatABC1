package river.chat.lib_core.view.common.dash;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import river.chat.lib_core.R;
import river.chat.lib_core.utils.system.DisplayUtil;

/**
 * Created by Marcus You on 2018/11/7.
 */
public class DashLine extends AppCompatEditText {

    private int mPaddingBottom;
    private boolean mSolidLine;
    private int mStrokeWidth;
    private int cursorColor;
    private int cursorWidth;
    private int cursorHeight;
    private boolean mInsertSpaceAtEnd;
    //虚线宽度
    private int dashWidth = 10;
    //实线宽度
    private int solidWidth = 10;
    private int solidColor = 0;

    public DashLine(Context context) {
        this(context, null);
    }

    public DashLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getAttrsValue(context, attrs);
//        initPaint();
    }

    private void getAttrsValue(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                                                      R.styleable.LinedEditText);
        mSolidLine = a.getBoolean(R.styleable.LinedEditText_solid_line, true);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.LinedEditText_stroke_width,
                                               0);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.LinedEditText_stroke_width,
                                               0);
        dashWidth = a.getDimensionPixelSize(R.styleable.LinedEditText_dash_width,
                                            DisplayUtil.dp2px(10));
        solidWidth = a.getDimensionPixelSize(R.styleable.LinedEditText_solid_width,
                                             DisplayUtil.dp2px(8));
        solidColor = a.getColor(R.styleable.LinedEditText_solid_color,
                                0x66CCCCCC);
        mInsertSpaceAtEnd = a.getBoolean(R.styleable.LinedEditText_insert_space_at_end,
                                         false);
        cursorColor = getColorAccent(context);
        cursorHeight = (int) (1.25 * getTextSize());
        cursorWidth = 6;
        a.recycle();
        setTextCursorDrawable();
    }

    private int getColorAccent(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme()
                .resolveAttribute(R.attr.colorAccent, typedValue, true);
        return typedValue.data;
    }

    private void setTextCursorDrawable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setTextCursorDrawable(new LineSpaceCursorDrawable(getCursorColor(),
                                                              getCursorWidth(),
                                                              getCursorHeight()));
            return;
        }
        try {
            Method method = AppCompatTextView.class.getDeclaredMethod(
                    "createEditorIfNeeded");
            method.setAccessible(true);
            method.invoke(this);
            Field field1 = AppCompatTextView.class.getDeclaredField("mEditor");
            Field field2 = Class.forName("android.widget.Editor")
                    .getDeclaredField("mCursorDrawable");
            field1.setAccessible(true);
            field2.setAccessible(true);
            Object arr = field2.get(field1.get(this));
            Array.set(arr,
                      0,
                      new LineSpaceCursorDrawable(getCursorColor(),
                                                  getCursorWidth(),
                                                  getCursorHeight()));
            Array.set(arr,
                      1,
                      new LineSpaceCursorDrawable(getCursorColor(),
                                                  getCursorWidth(),
                                                  getCursorHeight()));
        } catch (Exception ignored) {
        }
    }

    public int getCursorColor() {
        return cursorColor;
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
        setTextCursorDrawable();
        invalidate();
    }

    public int getCursorHeight() {
        return cursorHeight;
    }

    public void setCursorHeight(int cursorHeight) {
        this.cursorHeight = cursorHeight;
        setTextCursorDrawable();
        invalidate();
    }

    public int getCursorWidth() {
        return cursorWidth;
    }

    public void setCursorWidth(int cursorWidth) {
        this.cursorWidth = cursorWidth;
        setTextCursorDrawable();
        invalidate();
    }

    private void initPaint() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setHeight(getMeasuredHeight() + getLineHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置画笔
        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);//描边
        if (mStrokeWidth != 0) {
            mPaint.setStrokeWidth(DisplayUtil.dp2px(1));
        }
        mPaint.setColor(solidColor);//画笔颜色
        /*
         * PathEffect有6个子类：
         * CornerPathEffect(float radius)：给path加一些圆角，只有一个构造方法，radius代表圆角半径
         * DashPathEffect(float[] intervals,float phase):实现一个虚实线的效果，intervals就是虚线效果中实线和间隔的长度数组，intervals数组长度至少为2；phase指虚线相位，动态改变它，可是实现虚线移动效果
         * DiscretePathEffect(float segmentLength,float deviation):实现path边界藤蔓丛生的效果；path会被分成长度为segmentLength的碎片，然后在每个碎片上会随机产生以deviation为基数的偏离，这样藤蔓效果就产生了
         * PathDashPathEffect(Path shape,float advance,float phase,Style style):讲path的边界用一下小path首尾相连展现出来；shape就是用来组成path的小图形，advance是shape之间的距离；phase是相位，用来实现path移动的效果
         * ComposePathEffect和SumPathEffect都是讲两个PathEffect组合起来，达到一种组合效果；只不过ComposePahtEffect是两种效果组合起来，而SumPathEffect是将梁红在那个效果叠加起来
         * 值得注意的是，PathEffect虽然名字叫Path的Effect,但它的效果不仅仅局限在path，而是作用paint绘制的所有内容，包括文字
         *
         * */
        PathEffect effects;
        if (mSolidLine) {
            effects = new DashPathEffect(new float[]{5, 0}, 0);
        } else {
            effects = new DashPathEffect(new float[]{
                    solidWidth, dashWidth
            }, 0);
        }
        mPaint.setPathEffect(effects);

        /* 视图的left ， top ， right ， bottom 的值是针对其父视图的相对位置，所有属性均在下图标出*/
        int textMeasureWidth = (int) getPaint().measureText(getText().toString()) + getPaddingRight();//文本的宽度,适配一行输入框一直输入文字没有下划线问题
        int left = getLeft();//子View左边距离父view原点的距离
        int right = textMeasureWidth > getRight() ? textMeasureWidth :
                getRight();//子View右边距离父view原点的距离
        int paddingTop = getPaddingTop();//view的内容到view上面的距离
        //view的内容到view下面的距离
        if (mPaddingBottom == 0) {
            mPaddingBottom = getPaddingBottom();
        }
        int paddingLeft = getPaddingLeft();//view的内容到view左边的距离
        int paddingRight = getPaddingRight();//右边留白
        int height = getHeight();//view高度
        int lineHeight = getLineHeight();//返回一行的高度。注意标记内的文本可能高于或低于这个高度，布局可能包含额外的第一行或最后一行填充的部分。我们只是获取一行文字的高度，不计算间距
        int spcingHeight = (int) getLineSpacingExtra();//获取行距，就是每行文字距离上下横线的距离

        int count = ((height - paddingTop - mPaddingBottom) / lineHeight) + 1;//横线条数

        for (int i = 0; i < count; i++) {

            int baseline = lineHeight * (i + 1) + paddingTop - spcingHeight / 2+5;//得到第一行线，距离view上面的高度
//            canvas.drawLine(0, (int) (baseline * 1.0), right, (int) (baseline * 1.0), mPaint);//划线
            Path path = new Path();
            path.moveTo(0, (int) (baseline * 1.0));
            path.lineTo(right, (int) (baseline * 1.0));
            canvas.drawPath(path, mPaint);
        }
        setPadding(paddingLeft,
                   paddingTop,
                   paddingRight,
                   mPaddingBottom + spcingHeight / 2);//让最后一行的线显示出来
        super.onDraw(canvas);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isEnabled() && super.onTouchEvent(event);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (!mInsertSpaceAtEnd) {
            return;
        }
        String content = getText().toString();
        final int contentLength = content.length();
        if (isEndSpace(content)) {
            if (selEnd == contentLength) {
                if (contentLength > 0) {
                    setSelection(contentLength - 1);
                }
            }
        } else {
            Editable editable = getText();
            editable.insert(contentLength, " ");
        }
    }

    public boolean isEndSpace(String content) {
        if (content != null && content.length() > 0) {
            return content.endsWith(" ");
        }
        return false;
    }
}
