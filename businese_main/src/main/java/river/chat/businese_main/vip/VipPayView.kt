package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.pay.PayCenter
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewVipPayBinding
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.CreateOrderRequestBean
import river.chat.lib_resource.model.VipSkuBean

/**
 * Created by beiyongChao on 2023/8/19
 * Description:
 */
class VipPayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    var mBinding: ViewVipPayBinding
    var mActivity: AppCompatActivity? = null
    private var mOnTabClick: (Int) -> Unit = {}

    private var payList: MutableList<VipSkuBean> = mutableListOf()

    private var mTabBean: VipSkuBean? = null

    companion object {
    }

    init {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_vip_pay,
            this,
            true
        )
        initViews()
        initClick()

    }

    fun update(tabBean: VipSkuBean) {
        mTabBean = tabBean
        var priceColor = "#FA601F"
        mBinding.tvPrice.buildSpannableString {
            addText("￥", method = {
                setColor(priceColor)
                setSize(11)
            })
            addText(tabBean.price.toString(), method = {
                setColor(priceColor)
                setSize(21)
            })
        }

        mBinding.tvTip.text = tabBean.promoText3
        mBinding.tvPay.text = if (VipManager.isOfficalVip()) "立即续费" else "立即开通"
    }

    private fun initViews() {


    }

    private fun initClick() {
        mBinding.clPay.singleClick {
            postTrack(
                TrackerEventName.CLICK_VIP,
                TrackNode(TrackerKeys.CLICK_TYPE to "支付按钮:" + mTabBean?.skuName)
            )
            PayCenter.pay(CreateOrderRequestBean().apply {
                skuId = mTabBean?.skuId ?: ""
                price = mTabBean?.price.toString()
            })
        }
    }


}







