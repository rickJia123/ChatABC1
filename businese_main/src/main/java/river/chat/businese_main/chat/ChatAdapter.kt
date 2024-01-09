package river.chat.businese_main.chat

import river.chat.business_main.R
import river.chat.business_main.databinding.ItemMsgCardBinding
import river.chat.lib_core.view.recycleview.common.ItemViewHolder
import river.chat.lib_core.view.recycleview.common.SimpleListHolderAdapter
import river.chat.lib_resource.model.CardMsgBean

/**
 * Created by beiyongChao on 2023/12/29
 * Description:
 */

class ChatAdapter(override val layoutId: Int = R.layout.item_msg_card) :
    SimpleListHolderAdapter<CardMsgBean, ItemMsgCardBinding>(CardMsgBean.DIFF) {

    /**
     * 点击回调
     */
    var onClickItem: (CardMsgBean) -> Unit = {}

    override fun convert(
        binding: ItemMsgCardBinding,
        item: CardMsgBean,
        holder: ItemViewHolder<ItemMsgCardBinding>
    ) {
        binding.viewChatCard.refresh(item.questionMsg, item.answerMsg)

    }


}