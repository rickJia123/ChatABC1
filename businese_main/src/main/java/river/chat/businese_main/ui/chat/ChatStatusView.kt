package river.chat.businese_main.ui.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.utils.logChat
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewChatStatusBinding
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.ifEmptyOrBlank
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.mainThread
import river.chat.lib_core.utils.longan.workThread
import river.chat.lib_core.utils.other.CutdownUtils
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageStatus


class ChatStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {


    private var mIsLoading = false
    private var mLoadingJop: Job? = null
    private val viewBinding: ViewChatStatusBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        river.chat.business_main.R.layout.view_chat_status,
        this,
        true
    )

    init {

    }


    /**
     * 刷新数据
     */
    fun refresh(
        questionMsg: MessageBean,
        answerMsg: MessageBean,
        answerParent: ConstraintLayout,
        answerText: AppCompatTextView,
        reloadView: AppCompatImageView
    ) {
        ("聊天状态 问题：" + questionMsg + "" + "     答案状态 statusMsg：" + answerMsg).logChat()
        var status = answerMsg.status
        var failFlag = answerMsg.failFlag
        var failMsg = answerMsg.failMsg.ifEmptyOrBlank("加载失败")
        answerText.setTextColor(R.color.chatATv.getColor())
        mIsLoading = false
        when (status) {
            MessageStatus.COMPLETE -> {
                answerText.setTextColor(river.chat.lib_resource.R.color.chatATv.getColor())
                answerParent.visibility = VISIBLE
                viewBinding.loadingView.visibility = GONE
                visibility = GONE
                reloadView.visibility = VISIBLE
                stopLoading()
            }

            MessageStatus.LOADING -> {
                answerParent.visibility = View.VISIBLE
                reloadView.visibility = GONE
                visibility = VISIBLE
//                viewBinding.loadingView.visibility = VISIBLE
                viewBinding.viewPayGuide.visibility = GONE
                mIsLoading = true
                beginLoading(answerText)
            }


            //失败情况分两种：显示特定状态栏，直接在回答框显示异常信息
            MessageStatus.FAIL_COMMON -> {
                visibility = VISIBLE
                viewBinding.loadingView.visibility = GONE
                if (failMsg.contains("权益")) {
                    reloadView.visibility = GONE
                    answerParent.visibility = View.INVISIBLE
                    viewBinding.viewPayGuide.visibility = VISIBLE
                    answerParent.visibility = GONE
                    viewBinding.loadingView.visibility = GONE
                } else {
                    reloadView.visibility = VISIBLE
                    answerParent.visibility = VISIBLE
                    viewBinding.viewPayGuide.visibility = GONE
                    answerText.text = failMsg
                    answerText.setTextColor(river.chat.lib_core.R.color.red.getColor())
                    reloadView.visibility = VISIBLE
                }
                stopLoading()
            }
        }
        reloadView.singleClick {
//            reloadView.animate().rotationBy(360f).
            MessageCenter.beginReload(questionMsg) {
                reloadView.clearAnimation()
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
                        answerTxt.text = "正在思考中哦。。。"
                    } else if (it % 3 == 1) {
                        answerTxt.text = "正在思考中哦。。   "
                    } else {
                        answerTxt.text = "正在思考中哦。        "
                    }
                }
            }, delayTime = 500
        )
    }


    private fun stopLoading() {
        mLoadingJop?.cancel()
    }


}