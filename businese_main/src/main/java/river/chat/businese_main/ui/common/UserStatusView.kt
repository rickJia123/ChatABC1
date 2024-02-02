package river.chat.businese_main.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.config.ServiceConfigManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.router.jump2VipOpen
import river.chat.businese_common.utils.exts.loadAvatar
import river.chat.businese_main.manager.MainCommonHelper
import river.chat.businese_main.vip.VipManager
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewUserStatusBinding
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.utils.exts.getString
import river.chat.lib_core.utils.exts.singleClick
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
        if (ServiceConfigManager.isNeedHideVip()&&ServiceConfigManager.isNeedHideExchange()) {
            viewBinding.root.visibility = View.GONE
        }
    }


    fun update() {
        var userPlugin = getPlugin<UserPlugin>()
        var user = userPlugin.getUser()

        var type = user.getVipType()
        when (type) {
            VipType.VIP.value, VipType.TRIAL.value -> {
                viewBinding.tvVipTitle.text = user.rightsName
            }

            else -> {
                viewBinding.tvVipTitle.text = R.string.vip_no.getString()
            }
        }
        viewBinding.tvVipExpire.text = VipManager.getRemainTimeStr()
        viewBinding.ivRight.visibility = if (userPlugin.isLogin()) GONE else View.VISIBLE
        if (!userPlugin.isLogin()) {
            viewBinding.tvName.text = "登录/注册"

        } else {
            viewBinding.tvName.text = user.nickName
        }


        viewBinding.ivAvatar.loadAvatar(user.headImg)
//        viewBinding.ivAvatar.setImageResource(river.chat.lib_core.R.drawable.avator_default)
        viewBinding.viewClick.singleClick {
            viewBinding.viewClick.postTrack(
                TrackerEventName.CLICK_SETTING,
                TrackNode(TrackerKeys.CLICK_TYPE to "个人区域-查看权益")
            )
            VipManager.jump2VipPage()
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