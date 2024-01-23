package river.chat.lib_core.view.common.dash;

import android.graphics.drawable.ShapeDrawable;

/**
 * Created by yejiangmei on 2019/4/2.
 */

public class LineSpaceCursorDrawable extends ShapeDrawable {
    private int mHeight;

    public LineSpaceCursorDrawable(int cursorColor,
                                   int cursorWidth,
                                   int cursorHeight) {
        mHeight = cursorHeight;
        setDither(false);
        getPaint().setColor(cursorColor);
        setIntrinsicWidth(cursorWidth);
    }

    @Override
    public void setBounds(int paramInt1,
                          int paramInt2,
                          int paramInt3,
                          int paramInt4) {
        super.setBounds(paramInt1,
                        paramInt2,
                        paramInt3,
                        this.mHeight + paramInt2);
    }
}
