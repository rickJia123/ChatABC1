package river.chat.businese_main.ui.input

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import river.chat.businese_main.chat.hot.HotTipItemBean
import river.chat.businese_main.chat.hot.HotTipViewModel
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageHelper
import river.chat.business_main.databinding.ViewInputBinding
import river.chat.lib_core.BR
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.base.LifecycleView


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

    private val viewBinding: ViewInputBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        river.chat.business_main.R.layout.view_input,
        this,
        true
    )

    private var hotTipViewModel: HotTipViewModel? = null

    init {
        initListener()
//        commentViewBinding.etWriteReply.postDelayed({
//            showSoftInput()
//        }, 150)

        if (keyboardChangeListener == null) {
            keyboardChangeListener = KeyboardChangeListener(context as AppCompatActivity)
            keyboardChangeListener?.setKeyBoardListener(this)
        }
        hotTipViewModel = HotTipViewModel()
        viewBinding.setVariable(BR.vm, hotTipViewModel)
        loadHotQuestion()
        observerMsg()
    }

    private fun loadHotQuestion() {
        hotTipViewModel?.request?.requestHotQuestion()
//        hotTipViewModel?.data?.addAll(mutableListOf<HotTipItemBean?>().apply {
//            add(HotTipItemBean("特朗皮"))
//            add(HotTipItemBean("梅德韦杰夫"))
//            add(HotTipItemBean("金牌值多少钱"))
//        })
    }


    /**
     * 监听接口消息请求
     */
    private fun observerMsg() {
        (context as AppCompatActivity).let {
            hotTipViewModel?.request?.hotQuestionResult?.observe(it) {
                if (it.isSuccess) {
                    ("ChatInputView observerMsg:" + it.data).log()
                    hotTipViewModel?.data?.clear()
                    it.data?.let { it1 -> hotTipViewModel?.data?.addAll(it1) }
                }
            }
        }
    }

    // 弹出软键盘
    private fun showSoftInput() {

        viewBinding.etWriteReply.requestFocus()

        inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.showSoftInput(
            viewBinding.etWriteReply,
            InputMethodManager.SHOW_FORCED
        )
    }

    fun hideSoftInput() {
        inputMethodManager?.hideSoftInputFromWindow(viewBinding.etWriteReply.windowToken, 0)
    }

    private fun initListener() {
        viewBinding.etWriteReply.addTextChangedListener(object : SimpleTextWatcher() {
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
        viewBinding.ivSend.singleClick {
            var content = viewBinding.etWriteReply.text.toString().trim()
            content.log()
            MessageCenter.postReceiveMsg(MessageHelper.buildSelfMsg(content))
            viewBinding.etWriteReply.setText("")
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