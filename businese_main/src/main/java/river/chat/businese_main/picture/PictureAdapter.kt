package river.chat.businese_main.picture

import android.content.Context
import android.graphics.Bitmap
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.coroutines.Job
import river.chat.businese_common.router.jump2PicturePreView
import river.chat.businese_common.utils.BusinessUtils
import river.chat.businese_main.utils.getBitmap
import river.chat.business_main.R
import river.chat.business_main.databinding.ItemPictureAnswerBinding
import river.chat.business_main.databinding.ItemPictureQuestionBinding
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.exts.dp2px
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.loadCircle
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.screenWidth
import river.chat.lib_core.utils.other.CutdownUtils
import river.chat.lib_core.view.recycleview.common.ItemMultiViewHolder
import river.chat.lib_core.view.recycleview.common.MultiListAdapter
import river.chat.lib_resource.model.database.AiPictureBean
import river.chat.lib_resource.model.database.MessageStatus

/**
 * Created by beiyongChao on 2023/12/29
 * Description:
 */

class PictureAdapter(var context: FragmentActivity?) :
    MultiListAdapter<AiPictureBean>(AiPictureBean.DIFF) {

    /**
     * 点击回调
     */
    var onClickItem: (AiPictureBean) -> Unit = {}

    private var mIsLoading = false

    var mLoadingJop: Job? = null
    override fun getItemViewType(position: Int): Int {
        when (getItem(position).type) {

            AiPictureBean.TYPE_QUESTION -> {
                return R.layout.item_picture_question
            }

            AiPictureBean.TYPE_ANSWER -> {
                return R.layout.item_picture_answer
            }

            AiPictureBean.TYPE_TIP -> {
                return R.layout.item_msg_pay_tip
            }

            AiPictureBean.TYPE_AI -> {
                return R.layout.item_picture_ai
            }

            else -> {
                return R.layout.item_picture_question
            }

        }
    }

    override fun convert(
        holder: ItemMultiViewHolder,
        binding: ViewDataBinding,
        item: AiPictureBean
    ) {
        LogUtil.i("rick PictureFragment convert:" + GsonKits.toJson(item))
        when (item.type) {
            AiPictureBean.TYPE_QUESTION -> {
                var questionBingDing = holder.binding as ItemPictureQuestionBinding
                questionBingDing.tvQuestion.maxWidth = screenWidth - 20f.dp2px()
                questionBingDing.tvQuestion.text = item.content
            }

            AiPictureBean.TYPE_ANSWER -> {
                var answerBingDing = holder.binding as ItemPictureAnswerBinding
                answerBingDing.tvLoading.visibility = GONE
                answerBingDing.ivPicture.visibility = GONE
                mIsLoading = false
                mLoadingJop?.cancel()
                when (item.status) {
                    MessageStatus.COMPLETE -> {
                        answerBingDing.ivPicture.setImageBitmap(item.getBitmap())
                        answerBingDing.ivPicture.visibility = VISIBLE
                        answerBingDing.ivPicture.singleClick {
                           jump2PicturePreView(item.id?:"")
                        }
                    }

                    MessageStatus.FAIL_COMMON -> {
                        answerBingDing.tvLoading.visibility = VISIBLE
                        answerBingDing.tvLoading.text = item.failMsg
                    }

                    MessageStatus.LOADING -> {
                        mIsLoading = true
                        answerBingDing.tvLoading.visibility = VISIBLE
                        beginLoading(answerBingDing.tvLoading)
                    }
                }
            }

            AiPictureBean.TYPE_TIP -> {

            }
        }

    }



    private fun beginLoading(answerTxt: AppCompatTextView) {
        mLoadingJop = CutdownUtils.countDownCoroutines(
            60 * 1000,
            scope = (context as AppCompatActivity).lifecycleScope,
            onTick = {
                if (mIsLoading) {
                    if (it % 3 == 0) {
                        answerTxt.text = "正在努力绘制中哦。。。"
                    } else if (it % 3 == 1) {
                        answerTxt.text = "正在努力绘制中哦。。   "
                    } else {
                        answerTxt.text = "正在努力绘制中哦。        "
                    }
                }
            }, delayTime = 500
        )
    }

}