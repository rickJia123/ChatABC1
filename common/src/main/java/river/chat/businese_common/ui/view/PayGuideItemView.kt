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
import river.chat.common.databinding.ViewPayGuideBinding
import river.chat.common.databinding.ViewPayGuideItemBinding
import river.chat.lib_core.BR
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2023/12/31
 * Description:
 */
class PayGuideItemView(context: Context, attr: AttributeSet?, defStyleAttr: Int) : LifecycleView(
    context,
    attr,
    defStyleAttr
) {
    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)
    constructor(context: Context) : this(context, null, 0)

    private val viewBinding: ViewPayGuideItemBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_pay_guide_item,
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

    }

    var click: () -> Unit = {}
        set(value) {
            field = value
            setOnClickListener { value() }
        }

    /**
     * 初始化设置条目
     */
    fun action(action: PayItemActionSimple) {
        action.let {
            viewBinding.tvTitle.text= it.name
            click = it.onClickListener
        }
    }


}

data class PayItemActionSimple(
    val name: String,
    val onClickListener: () -> Unit
)