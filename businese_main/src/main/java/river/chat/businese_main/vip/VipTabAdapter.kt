package river.chat.businese_main.vip

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import river.chat.business_main.R
import river.chat.business_main.databinding.ItemVipTabBinding
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.view.recycleview.common.ItemViewHolder
import river.chat.lib_core.view.recycleview.common.SimpleListHolderAdapter
import river.chat.lib_resource.model.VipSkuBean

/**
 * Created by beiyongChao on 2023/12/29
 * Description:
 */

class VipTabAdapter(override val layoutId: Int = R.layout.item_vip_tab, var selPosition: Int = 0) :
    SimpleListHolderAdapter<VipSkuBean, ItemVipTabBinding>(VipSkuBean.DIFF) {

    /**
     * 点击回调
     */
    var onClickItem: (VipSkuBean) -> Unit = {}

    override fun convert(
        binding: ItemVipTabBinding,
        item: VipSkuBean,
        holder: ItemViewHolder<ItemVipTabBinding>
    ) {
        var position = holder.adapterPosition

        binding.tvDuration.text = item.skuName
        binding.tvMonthPrice.text = item.promoText2


        binding.tvDiscount.text = item.promoText1
        if (item.promoText1.isNullOrEmpty()) {
            binding.tvDiscount.visibility = View.GONE
        } else {
            binding.tvDiscount.visibility = View.VISIBLE
        }

        var isSelected = position == selPosition
        binding.tvDuration.setTextColor(if (isSelected) R.color.defaultTitleColor.getColor() else R.color.defaultDarkTitleColor.getColor())
        binding.tvMonthPrice.setTextColor(if (isSelected) R.color.defaultTitleColor.getColor() else R.color.defaultDarkTitleColor.getColor())
        setPrice(binding.tvPrice, item, if (isSelected) "#030303" else "#FA601F")
    }

    private fun setPrice(tvPrice: AppCompatTextView, bean: VipSkuBean, color: String) {
        tvPrice.buildSpannableString {
            addText("￥", method = {
                setColor(color)
                setSize(11)
            })
            addText(bean.price.toString(), method = {
                setColor(color)
                setSize(21)
            })
        }
    }

}