package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import coil.load
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewVipBinding
import river.chat.lib_core.utils.exts.view.loadSimple
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.vip.VipType

/**
 * Created by beiyongChao on 2023/8/19
 * Description:
 */
class VipView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    var mBinding: ViewVipBinding

    companion object {
    }

    init {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_vip,
            this,
            true
        )
        updateViews()
    }

    /**
     * 刷新
     */
    fun refresh() {
        updateViews()
    }

    private fun updateViews() {
        var vipType = VipManager.getVipType()
        var vipIcon = when (vipType) {
            VipType.TRIAL.value, VipType.VIP.value -> {
                R.drawable.vip
            }

            else -> {
                R.drawable.vip_dis
            }
        }
        mBinding.clVip.loadSimple(vipIcon)
    }


}







