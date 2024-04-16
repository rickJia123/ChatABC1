package river.chat.businese_main.chat

import androidx.databinding.ViewDataBinding
import river.chat.business_main.R
import river.chat.business_main.databinding.ItemMsgAiBinding
import river.chat.business_main.databinding.ItemMsgCardBinding
import river.chat.business_main.databinding.ItemMsgPayTipBinding
import river.chat.lib_core.view.recycleview.common.ItemMultiViewHolder
import river.chat.lib_core.view.recycleview.common.MultiListAdapter
import river.chat.lib_resource.model.database.CardMsgBean
import river.chat.lib_resource.model.database.MessageSource

/**
 * Created by beiyongChao on 2023/12/29
 * Description:
 */

class ChatAdapter() :
    MultiListAdapter<CardMsgBean>(CardMsgBean.DIFF) {

    /**
     * 点击回调
     */
    var onClickItem: (CardMsgBean) -> Unit = {}


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).questionMsg.source) {
            MessageSource.SINGLE_AI_DEFAULT -> R.layout.item_msg_ai
            MessageSource.SINGLE_PAY_TIP -> R.layout.item_msg_pay_tip
            else -> R.layout.item_msg_card
        }
    }

    override fun convert(holder: ItemMultiViewHolder, binding: ViewDataBinding, item: CardMsgBean) {
        var position=holder.adapterPosition
        var totalCount=itemCount
        var isLast=position==totalCount-1
        when (item.questionMsg.source) {
            MessageSource.SINGLE_AI_DEFAULT -> {
                var aiBingDing = holder.binding as ItemMsgAiBinding
                aiBingDing.tvContent.text = item.questionMsg.content
            }

            MessageSource.SINGLE_PAY_TIP -> {
                var payBingDing = holder.binding as ItemMsgPayTipBinding
            }
            else -> {
                var msdBingDing = holder.binding as ItemMsgCardBinding
                msdBingDing.viewChatCard.refresh(item.questionMsg, item.answerMsg,isLast)
            }
        }

    }


}