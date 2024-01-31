package river.chat.businese_main.ui.common

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.view.marginBottom
import androidx.databinding.DataBindingUtil
import river.chat.businese_main.vip.VipManager
import river.chat.common.R
import river.chat.common.databinding.ViewInputCommonBinding
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2023/8/31
 * Description:
 */
class CommonChatInputView(context: Context, attr: AttributeSet?, defStyleAttr: Int) : LifecycleView(
    context,
    attr,
    defStyleAttr
) {
    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)
    constructor(context: Context) : this(context, null, 0)

    var onSendListener: (String) -> Unit = { }


    /**
     *是否允许发送
     */

    var mSendEnable = true

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


    private fun getInputText(): String {
        return viewBinding.etInput.text.toString()
    }

    private fun initListener() {
        viewBinding.etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        viewBinding.ivSend.setOnClickListener {
            var content = getInputText()
            if (mSendEnable) {
                if (VipManager.hasRights()) {
                    viewBinding.etInput.setText("")
                }
            }
            onSendListener.invoke(content)
        }
    }


}