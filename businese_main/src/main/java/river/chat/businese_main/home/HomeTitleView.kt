package river.chat.businese_main.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import river.chat.businese_main.vip.VipManager
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewHomeTitleBinding
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.base.LifecycleView


class HomeTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {


    private val viewBinding: ViewHomeTitleBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_home_title,
        this,
        true
    )

    init {
        update()
        initClick()
    }

    private fun initClick() {
        viewBinding.viewVip.singleClick {
            VipManager.jump2VipPage()
        }
    }


    fun update() {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        var remainTimes =
            user.trialBalance
    }




}