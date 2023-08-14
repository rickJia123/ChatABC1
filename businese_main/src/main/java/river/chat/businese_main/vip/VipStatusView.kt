package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.business_main.databinding.ViewVipStatusBinding
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.view.base.LifecycleView


class VipStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {


    private val viewBinding: ViewVipStatusBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        river.chat.business_main.R.layout.view_vip_status,
        this,
        true
    )


    init {

        updateStatus()

    }


    fun update() {
        updateStatus()
    }

    private fun updateStatus() {
        var remainTimes =
            getPlugin<UserPlugin>().getUser().remainTryTimes
        viewBinding.tvTitle.buildSpannableString {
            addText("免费次数还剩")
            addText(remainTimes.safeToString()) {
                setColor("#FA601F")
            }
            addText("次")
        }
    }

    private fun initClick() {

    }


}