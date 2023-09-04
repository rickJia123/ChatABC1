package river.chat.businese_common.ui.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import river.chat.common.R
import river.chat.common.databinding.ViewInputCommonBinding
import river.chat.lib_core.BR
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2023/8/31
 * Description:
 */
class CommonInputView(context: Context, attr: AttributeSet?, defStyleAttr: Int) : LifecycleView(
    context,
    attr,
    defStyleAttr
) {
    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)
    constructor(context: Context) : this(context, null, 0)





    private val viewBinding: ViewInputCommonBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_input_common,
        this,
        true
    )


    init {
//        commentViewBinding.etWriteReply.postDelayed({
//            showSoftInput()
//        }, 150)

        initListener()
    }


    private fun initListener() {
        viewBinding.etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {


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


    }


}