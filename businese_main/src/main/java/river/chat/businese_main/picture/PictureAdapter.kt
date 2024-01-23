package river.chat.businese_main.picture

import androidx.databinding.ViewDataBinding
import river.chat.business_main.R
import river.chat.business_main.databinding.ItemPictureAnswerBinding
import river.chat.business_main.databinding.ItemPictureQuestionBinding
import river.chat.lib_core.utils.exts.dp2px
import river.chat.lib_core.utils.longan.screenWidth
import river.chat.lib_core.view.recycleview.common.ItemMultiViewHolder
import river.chat.lib_core.view.recycleview.common.MultiListAdapter
import river.chat.lib_resource.model.database.AiPictureBean

/**
 * Created by beiyongChao on 2023/12/29
 * Description:
 */

class PictureAdapter :
    MultiListAdapter<AiPictureBean>(AiPictureBean.DIFF) {

    /**
     * 点击回调
     */
    var onClickItem: (AiPictureBean) -> Unit = {}


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isAnswer) {
            R.layout.item_picture_answer
        } else {
            R.layout.item_picture_question
        }
    }

    override fun convert(
        holder: ItemMultiViewHolder,
        binding: ViewDataBinding,
        item: AiPictureBean
    ) {
        if (item.isAnswer) {
            var answerBingDing = holder.binding as ItemPictureAnswerBinding
//            answerBingDing.ivPicture.load()
        } else {
            var questionBingDing = holder.binding as ItemPictureQuestionBinding
            questionBingDing.tvQuestion.maxWidth = screenWidth - 20f.dp2px()
            questionBingDing.tvQuestion.text = item.content
        }


    }


}