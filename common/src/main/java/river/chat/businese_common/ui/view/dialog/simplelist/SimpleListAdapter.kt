package river.chat.businese_common.ui.view.dialog.simplelist

import river.chat.common.R
import river.chat.common.databinding.ItemSimpleListBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.recycleview.common.ItemViewHolder
import river.chat.lib_core.view.recycleview.common.SimpleListHolderAdapter

/**
 * Created by beiyongChao on 2023/12/29
 * Description:
 */

class SimpleListAdapter(
    override val layoutId: Int = R.layout.item_simple_list,
    var totalCount: Int
) :
    SimpleListHolderAdapter<SimpleDialogListBean, ItemSimpleListBinding>(SimpleDialogListBean.DIFF) {


    /**
     * 点击回调
     */
    var onClickItem: (SimpleDialogListBean) -> Unit = {}

    override fun convert(
        binding: ItemSimpleListBinding,
        item: SimpleDialogListBean,
        holder: ItemViewHolder<ItemSimpleListBinding>
    ) {
        binding.tvContent.text = item.content
        binding.viewRoot.singleClick {
            onClickItem.invoke(item)
        }
        var position = holder.adapterPosition
        if (position != totalCount - 1) {
            binding.viewLine.visibility = android.view.View.VISIBLE
        } else {
            binding.viewLine.visibility = android.view.View.GONE
        }
    }


}