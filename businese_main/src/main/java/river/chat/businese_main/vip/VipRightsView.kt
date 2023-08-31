package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import river.chat.business_main.databinding.ViewVipRightsBinding
import river.chat.lib_core.BR
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.VipRightsBean

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
//        vipViewModel?.initRightsList()
    }


      fun updateRights(rightsBean: VipRightsBean) {
//        var remainTimes =
//            getPlugin<UserPlugin>().getUser().remainTryTimes
//
        updateVipMsg(
            "普通用户",
            viewBinding.tvNormalType,
            viewBinding.tvNormalTitle,
            "#505050",
            rightsBean.normal
        )
        updateVipMsg(
            "会员",
            viewBinding.tvVipType,
            viewBinding.tvVipTitle,
            "#f98c25",
            rightsBean.vip
        )
          vipViewModel?.vipRightList?.addAll(mutableListOf<String>().apply {
              rightsBean.vip.forEach {
                  add(it)
              }
          })
          vipViewModel?.normalRightList?.addAll(mutableListOf<String>().apply {
              rightsBean.normal.forEach {
                  add(it)
              }
          })
    }

    private fun updateVipMsg(
        type: String,
        tvType: AppCompatTextView,
        tvTitle: AppCompatTextView,
        highColor:String,
        rightsList: MutableList<String>
    ) {
        tvType.text = type
        tvTitle.buildSpannableString {
            addText("共有 ")
            addText(rightsList.size.safeToString()) {
                setColor(highColor)
            }
            addText(" 项权益")
        }
    }

    private fun initClick() {

    }


}