package river.chat.businese_main.chat

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import river.chat.business_main.R
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_core.utils.longan.dp
import river.chat.lib_core.utils.longan.screenWidth

/**
 * Create by Circle on 2019/10/17
 */
class ChatItemMorePopWindow(
    context: Context,
    var msg: MessageBean,
    listener: OnChatItemMoreClickListener?
) : PopupWindow(context) {

    var mWidth = 0

    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.popwindow_chat_item_more, null)


        contentView.findViewById<TextView>(R.id.tvShare).setOnClickListener {
            listener?.let { listener.onShareClick() }
            dismiss()
        }
        contentView.findViewById<TextView>(R.id.tvCopy).setOnClickListener {
            listener?.let { listener.onCopyClick() }
            dismiss()
        }

        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        setBackgroundDrawable(BitmapDrawable())

        setTouchInterceptor(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_OUTSIDE) {
                    dismiss()
                    return true
                }
                return false
            }

        })
    }

    fun showAsDropDown(parent: View, contentView: View) {
        try {
            if (msg.isSelf()) {
                showAsDropDown(
                    contentView,
                    contentView.width/2,
                    screenWidth - contentView.width / 2 - 11.dp.toInt(),
//                    0,
                    -1*(parent.height+70)
                )
            } else {
                showAsDropDown(
                    parent,
                    contentView.width / 2 + 11.dp.toInt(),
                  0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {

        }
    }

}

interface OnChatItemMoreClickListener {
    fun onCopyClick()
    fun onShareClick()
}
