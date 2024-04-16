package river.chat.businese_main.creation.itemfragment

import river.chat.businese_main.creation.CreationItemsBeans
import river.chat.business_main.R
import river.chat.business_main.databinding.ItemCreationItemBinding
import river.chat.lib_core.utils.exts.view.loadCircle
import river.chat.lib_core.view.recycleview.common.ItemViewHolder
import river.chat.lib_core.view.recycleview.common.SimpleListHolderAdapter

/**
 * Created by beiyongChao on 2023/12/29
 * Description:
 */

class CreationItemAdapter(override val layoutId: Int = R.layout.item_creation_item) :
    SimpleListHolderAdapter<CreationItemsBeans, ItemCreationItemBinding>(CreationItemsBeans.DIFF) {

    private var iconItemBgRes = mutableListOf<Int>().apply {
        add(R.drawable.gradient_model_pink)
        add(R.drawable.gradient_model_blue)
        add(R.drawable.gradient_model_green)
    }

    override fun convert(
        binding: ItemCreationItemBinding,
        item: CreationItemsBeans,
        holder: ItemViewHolder<ItemCreationItemBinding>
    ) {
        binding.ivIcon.loadCircle(item.coverImg)
        binding.tvTitle.text = item.name
        binding.tvSubTitle.text = item.descr

        binding.ivBg.setImageResource(iconItemBgRes[holder.adapterPosition % iconItemBgRes.size])
    }


}