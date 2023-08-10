package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import com.umeng.socialize.bean.SHARE_MEDIA
import river.chat.businese_main.chat.hot.HotTipViewModel
import river.chat.businese_main.share.ShareDialog
import river.chat.businese_main.utils.logChat
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewChatCardBinding
import river.chat.business_main.databinding.ViewVipRightsBinding
import river.chat.business_main.databinding.ViewVipStatusBinding
import river.chat.lib_core.BR
import river.chat.lib_core.beans.VipRightsBeans
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.getViewBitmap
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.utils.longan.copyToClipboard
import river.chat.lib_core.utils.longan.dp
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.utils.system.DisplayUtil.getScreenWidth
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_umeng.ShareManager
import river.chat.lib_umeng.common.RiverShareContent

/**
 * vip 权益
 */
class VipRightsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    private var vipViewModel: VipViewModel? = null
    private val viewBinding: ViewVipRightsBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        river.chat.business_main.R.layout.view_vip_rights,
        this,
        true
    )


    init {
        vipViewModel = VipViewModel()
        viewBinding.setVariable(BR.vm, vipViewModel)
        vipViewModel?.initRightsList()
        updateRights()
    }


    private fun updateRights() {
//        var remainTimes =
//            getPlugin<UserPlugin>().getUser().remainTryTimes
//
        updateVipMsg(
            "普通用户",
            viewBinding.tvNormalType,
            viewBinding.tvNormalTitle,
            "#9ce0b8",
            vipViewModel?.normalRightList
        )
        updateVipMsg(
            "会员",
            viewBinding.tvVipType,
            viewBinding.tvVipTitle,
            "#f5cc19",
            vipViewModel?.vipRightList
        )
    }

    private fun updateVipMsg(
        type: String,
        tvType: AppCompatTextView,
        tvTitle: AppCompatTextView,
        highColor:String,
        rightsList: List<VipRightsBeans>?
    ) {
        tvType.text = type
        tvTitle.buildSpannableString {
            addText("共有")
            addText(rightsList?.size.safeToString()) {
                setColor(highColor)
            }
            addText("项权益")
        }
    }

    private fun initClick() {

    }


}