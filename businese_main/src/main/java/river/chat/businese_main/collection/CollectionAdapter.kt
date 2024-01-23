package river.chat.businese_main.collection

import river.chat.businese_common.router.jump2CollectionDetail
import river.chat.businese_common.ui.view.dialog.simplelist.SimpleDialogListBean
import river.chat.businese_common.ui.view.dialog.simplelist.SimpleDialogListConfig
import river.chat.businese_common.ui.view.dialog.simplelist.SimpleListDialog
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.share.ShareDialog
import river.chat.business_main.R
import river.chat.business_main.databinding.ItemCollectionCardBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.activity.BaseActivity
import river.chat.lib_core.view.recycleview.common.ItemViewHolder
import river.chat.lib_core.view.recycleview.common.SimpleListHolderAdapter
import river.chat.lib_resource.model.database.CardMsgBean

/**
 * Created by beiyongChao on 2023/12/29
 * Description:
 */

class CollectionAdapter(override val layoutId: Int = R.layout.item_collection_card) :
    SimpleListHolderAdapter<CardMsgBean, ItemCollectionCardBinding>(CardMsgBean.DIFF) {


    override fun convert(
        binding: ItemCollectionCardBinding,
        item: CardMsgBean,
        holder: ItemViewHolder<ItemCollectionCardBinding>
    ) {
        binding.viewChatCard.refresh(item.questionMsg, item.answerMsg)
        binding.viewRoot.singleClick {
            jump2CollectionDetail(item.questionMsg.id)

        }
        binding.viewChatCard.singleClick {
            jump2CollectionDetail(item.questionMsg.id)
        }
        binding.viewChatCard.setOnLongClickListener {
            SimpleListDialog.builder(topActivity as BaseActivity).config(
                SimpleDialogListConfig()
                    .apply {
                        list.add(SimpleDialogListBean("删除") {
                            MessageCenter.toggleCollectionStatus(item.questionMsg)
                        })
                        list.add(SimpleDialogListBean("分享") {
                            ShareDialog.builder(topActivity).show(item.questionMsg, item.answerMsg)
                        })
                    }).show()
            true
        }
    }


}