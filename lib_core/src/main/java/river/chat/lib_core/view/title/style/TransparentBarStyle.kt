package river.chat.lib_core.view.title.style

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import river.chat.lib_core.R
import river.chat.lib_core.utils.exts.getDrawable
import river.chat.lib_core.view.title.SelectorDrawable

/**
 * author : Scott
 * desc   : 透明主题样式实现（对应布局属性：app:barStyle="transparent"）
 */
open class TransparentBarStyle : CommonBarStyle() {
    override fun getBackButtonDrawable(context: Context): Drawable {
        return R.mipmap.back_common.getDrawable()!!
    }

    override fun getLeftTitleBackground(context: Context): Drawable {
        return SelectorDrawable.Builder()
            .setDefault(ColorDrawable(0x00000000))
            .setFocused(ColorDrawable(0x22000000))
            .setPressed(ColorDrawable(0x22000000))
            .build()
    }

    override fun getRightTitleBackground(context: Context): Drawable {
        return SelectorDrawable.Builder()
            .setDefault(ColorDrawable(0x00000000))
            .setFocused(ColorDrawable(0x22000000))
            .setPressed(ColorDrawable(0x22000000))
            .build()
    }

    override fun getTitleBarBackground(context: Context): Drawable {
        return ColorDrawable(0x00000000)
    }

    override fun getTitleColor(context: Context): ColorStateList {
        return ColorStateList.valueOf(-0x1)
    }

    override fun getLeftTitleColor(context: Context): ColorStateList {
        return ColorStateList.valueOf(-0x1)
    }

    override fun getRightTitleColor(context: Context): ColorStateList {
        return ColorStateList.valueOf(-0x1)
    }

    override fun getLineDrawable(context: Context): Drawable {
        return ColorDrawable(0x00000000)
    }
}