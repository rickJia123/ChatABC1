package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewVipPayBinding
import river.chat.lib_core.utils.exts.autoIntOr1Str
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.vip.VipSkuBean

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
        var dayPrice: Float = tabBean.price / (tabBean.duration * 31)
        mBinding.tvTip.text = "每天约" + dayPrice.autoIntOr1Str() + "元"
    }

    private fun initViews() {


    }

    fun initClick() {

    }


    private fun updateData(payList: MutableList<VipSkuBean>) {
        this.payList = payList

    }


}







