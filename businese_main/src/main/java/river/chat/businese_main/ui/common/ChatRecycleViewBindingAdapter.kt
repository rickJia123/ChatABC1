package river.chat.businese_main.ui.common


import androidx.databinding.BindingAdapter
import river.chat.businese_main.collection.CollectionSimpleCardView
import river.chat.businese_main.ui.chat.ChatCardView
import river.chat.lib_resource.model.database.CardMsgBean

/**
 *Author: loongwind
 *Time: 2019-09-04
 *Description: Data binding Adapter with recycler view
 */
@BindingAdapter("cardMsg")
fun setCardMsg(
    charCardView: ChatCardView,
    cardMsg: CardMsgBean
) {
    charCardView.refresh(cardMsg.questionMsg, cardMsg.answerMsg)
}
@BindingAdapter("cardMsg")
fun setCardMsg(
    charCardView: CollectionSimpleCardView,
    cardMsg: CardMsgBean
) {
    charCardView.refresh(cardMsg.questionMsg, cardMsg.answerMsg)
}



