package river.chat.businese_main.vip

import android.content.Context
import android.os.UserManager
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewVipStatusBinding
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.utils.exts.view.loadCircle
import river.chat.lib_core.utils.exts.view.loadSimple
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.vip.VipType


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
        update()
    }


    fun update() {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()
        var remainTimes =
            user.remainTryTimes
        var vipExpireTimeStr = user.vipExpireTimeStr
        var type = user.vipType
        when (type) {
            VipType.TRIAL.value -> {
                viewBinding.tvRemainTimes.buildSpannableString {
                    addText("免费次数还剩")
                    addText(remainTimes.safeToString()) {
                        setColor("#FA601F")
                    }
                    addText("次")
                }
            }

            VipType.VIP.value -> {
                viewBinding.tvRemainTimes.text = vipExpireTimeStr
            }

            else -> {
                viewBinding.tvRemainTimes.text = "开通会员畅享ChatGPT"
            }
        }
        if (!userPlugin.isLogin()) {
            viewBinding.tvName.text = "未登录"
            viewBinding.ivAvatar.visibility = GONE
        }

        viewBinding.ivAvatar.loadCircle(if (user.headImg == null) R.drawable.avator_default else user.headImg)
        viewBinding.viewClick.singleClick {
            userPlugin.check2Login {
            }
        }

    }


}