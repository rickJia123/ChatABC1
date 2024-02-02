package river.chat.businese_common.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.config.ServiceConfigManager
import river.chat.businese_common.report.ChatTracker
import river.chat.businese_common.report.ReportManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
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
        initView()
        initListener()
    }


    private fun initView() {
        if (ServiceConfigManager.isNeedHideVip()) {
            viewBinding.viewPay.visibility = GONE
        }
        if (ServiceConfigManager.isNeedHideExchange()) {
            viewBinding.viewShare.visibility = GONE
        }

        if (ServiceConfigManager.isNeedHideVip() && ServiceConfigManager.isNeedHideExchange()) {
            viewBinding.root.visibility = GONE
        }
    }

    private fun initListener() {
        viewBinding.viewPay.action(PayItemActionSimple("开通会员") {
            jump2VipOpen()
            ReportManager.reportEvent(
                TrackerEventName.CLICK_CHAT,
                mutableMapOf(
                    ChatTracker.CLICK_ACTION to "开通会员："
                )
            )
        })
        viewBinding.viewShare.action(PayItemActionSimple("兑换会员") {
            jump2VipExchange()
            ReportManager.reportEvent(
                TrackerEventName.CLICK_CHAT,
                mutableMapOf(
                    ChatTracker.CLICK_ACTION to "兑换会员："
                )
            )
        })

    }


}