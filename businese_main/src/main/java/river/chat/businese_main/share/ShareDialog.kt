package river.chat.businese_main.share


import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import river.chat.businese_main.api.ChatAnswerBean
import river.chat.business_main.databinding.DialogShareBinding
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment

/**
 * Created by beiyongChao on 2023/6/9
 * Description:
 */
class ShareDialog(var dialogActivity: AppCompatActivity) :
    BaseBindingDialogFragment<DialogShareBinding>() {

    private var questionMsg: MessageBean? = null
    private var answerMsg: MessageBean? = null

    companion object {
        @JvmStatic
        fun builder(activity: AppCompatActivity): ShareDialog = ShareDialog(activity)
    }


    override fun initDataBinding(binding: DialogShareBinding) {
        binding.tvQuestion.text = questionMsg?.content ?: ""
        binding.tvAnswer.text = answerMsg?.content ?: ""

    }

    private fun closeDialog() {
        dismiss()
    }

    fun show(questionMsg: MessageBean? = null, answerMsg: MessageBean? = null) {
        this.questionMsg = questionMsg
        this.answerMsg = answerMsg
        dialogActivity.supportFragmentManager.let {
            show(it, "ShareDialog")
        }
    }


    protected override fun onSetDialogWidth(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }

    protected override fun onSetDialogHeight(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }

}
