package river.chat.businese_main.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.router.jump2VipOpen
import river.chat.businese_main.vip.VipManager
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewUserStatusBinding
import river.chat.business_main.databinding.ViewVipStatusBinding
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.utils.exts.view.loadCircle
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.VipType


class UserStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {


    private val viewBinding: ViewUserStatusBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_user_status,
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
        var type = user.vipType
        when (type) {

            VipType.VIP.value -> {
                viewBinding.tvVipTitle.text = user.vipName
                viewBinding.tvVipExpire.text = user.vipExpireTimeStr
            }

            else -> {
                viewBinding.tvVipTitle.text = "您还不是超级会员"
            }
        }
        if (!userPlugin.isLogin()) {
            viewBinding.tvName.text = "登录/注册"
        } else {
            viewBinding.tvName.text = user.nickName
        }

        viewBinding.ivAvatar.loadCircle(if (user.headImg == null) R.drawable.avator_default else user.headImg)
        viewBinding.viewClick.singleClick {
            viewBinding.viewClick.postTrack(
                TrackerEventName.CLICK_SETTING,
                TrackNode(TrackerKeys.CLICK_TYPE to "个人区域-查看权益")
            )
//            VipManager.jump2VipPage()
            jump2VipOpen()
        }
        viewBinding.tvName.singleClick {
            userPlugin.check2Login {
                if (!it) {
                    viewBinding.viewClick.postTrack(
                        TrackerEventName.CLICK_SETTING,
                        TrackNode(TrackerKeys.CLICK_TYPE to "个人区域-跳转登录")
                    )
                }
            }
        }

    }


}