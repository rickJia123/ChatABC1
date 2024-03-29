package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.config.ServiceConfigManager
import river.chat.businese_common.utils.exts.loadAvatar
import river.chat.businese_main.manager.MainCommonHelper
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewVipStatusBinding
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.getString
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.VipType


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
        if (ServiceConfigManager.isNeedHideVip()&&ServiceConfigManager.isNeedHideExchange()) {
            viewBinding.root.visibility = View.GONE
        }
        update()
    }


    fun update() {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()


        var type = user.getVipType()
        when (type) {
            VipType.VIP.value,VipType.TRIAL.value -> {
                viewBinding.tvRemainTimes.text = VipManager.getRemainTimeStr()
            }

            else -> {
                viewBinding.tvRemainTimes.text = R.string.vip_no.getString()
            }
        }
        if (!userPlugin.isLogin()) {
            viewBinding.tvName.text = "登录/注册"
        } else {
            viewBinding.tvName.text = user.nickName
        }

        viewBinding.ivAvatar.loadAvatar(user.headImg)
        viewBinding.viewClick.singleClick {
            userPlugin.check2Login {
            }
        }

    }


}