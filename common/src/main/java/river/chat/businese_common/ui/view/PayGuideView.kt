package river.chat.businese_common.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.router.jump2VipExchange
import river.chat.businese_common.router.jump2VipOpen
import river.chat.common.R
import river.chat.common.databinding.ViewPayGuideBinding
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2023/12/31
 * Description:
 */
class PayGuideView(context: Context, attr: AttributeSet?, defStyleAttr: Int) : LifecycleView(
    context,
    attr,
    defStyleAttr
) {
    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)
    constructor(context: Context) : this(context, null, 0)

    private val viewBinding: ViewPayGuideBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_pay_guide,
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
        viewBinding.viewPay.action(PayItemActionSimple("开通会员") {
            jump2VipOpen()
        })
        viewBinding.viewPay.action(PayItemActionSimple("获取兑换码") {
            jump2VipExchange()
        })

    }


}