package river.chat.businese_main.ui.input

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageHelper
import river.chat.common.R
import river.chat.common.databinding.ViewInputBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.base.LifecycleView

/**
 * CreateDate: 2022/1/11
 * CreateTime: 2:51 PM
 *
 * Author : zang.peng
 * Email : zangp_hq@163.com
 * Version : 1.0
 */
class ChatInputView(context: Context, attr: AttributeSet?, defStyleAttr: Int) : LifecycleView(
    context,
    attr,
    defStyleAttr
), KeyboardChangeListener.KeyBoardListener {
    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)
    constructor(context: Context) : this(context, null, 0)


    // 评论限制总字数
    var textTotalNum = 500

    private var inputMethodManager: InputMethodManager? = null
    var keyboardChangeListener: KeyboardChangeListener? = null

    private val commentViewBinding: ViewInputBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_input,
        this,
        true
    )


    init {
        initListener()
//        commentViewBinding.etWriteReply.postDelayed({
//            showSoftInput()
//        }, 150)

        if (keyboardChangeListener == null) {
            keyboardChangeListener = KeyboardChangeListener(context as AppCompatActivity)
            keyboardChangeListener?.setKeyBoardListener(this)
        }

    }

    // 弹出软键盘
    private fun showSoftInput() {

        commentViewBinding.etWriteReply.requestFocus()

        inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(
            commentViewBinding.etWriteReply,
            InputMethodManager.SHOW_FORCED
        )
    }

    fun hideSoftInput() {
        inputMethodManager?.hideSoftInputFromWindow(commentViewBinding.etWriteReply.windowToken, 0)
    }

    private fun initListener() {
        commentViewBinding.etWriteReply.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {

                val remindNum = textTotalNum - s.toString().length

//                if (remindNum > 10) {
//                    commentViewBinding.tvRemindNum.visibility = GONE
//                } else {
//                    if (remindNum < 0) {
//                        commentViewBinding.tvRemindNum.isEnabled = false
//                        commentViewBinding.tvSendReply.isEnabled = false
//                    } else {
//                        commentViewBinding.tvSendReply.isEnabled = true
//                        commentViewBinding.tvRemindNum.isEnabled = true
//                    }
//                    commentViewBinding.tvRemindNum.visibility = VISIBLE
//                    commentViewBinding.tvRemindNum.text = remindNum.toString()
//                }

            }
        })

        // 发送内容
        commentViewBinding.ivSend.singleClick {

            var content = commentViewBinding.etWriteReply.text.toString().trim()
            content.log()
            MessageCenter.postReceiveMsg(MessageHelper.buildSelfMsg(content))
            commentViewBinding.etWriteReply.setText("")

        }


    }

    private fun dismissView() {
        keyboardChangeListener?.destroy()
        keyboardChangeListener = null
    }


    override fun onKeyboardChange(isShow: Boolean, keyboardHeight: Int) {
        if (!isShow) {
            dismissView()
        }
    }

}