package river.chat.lib_core.view.common

import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import river.chat.lib_core.databinding.ToastSimpleBinding
import river.chat.lib_core.utils.other.CutdownUtils
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment

/**
 * Created by beiyongChao on 2022/12/6
 * Description:
 */
open class ToastTipDialog : BaseBindingDialogFragment<ToastSimpleBinding>() {

    private var cutDownTime = 2 * 1000

    companion object {

        var content: String = ""
        fun launch(
            activity: AppCompatActivity, content: String = ""
        ) {
            Companion.content = content
            val tipDialog = ToastTipDialog()
            tipDialog.show(
                activity.supportFragmentManager, "ToastTipDialog"
            )
        }
    }


    override fun initDataBinding(binding: ToastSimpleBinding) {
        dialog?.window?.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        binding.tvContent.text = content
        startCutDown()
    }


    private fun startCutDown() {
        CutdownUtils.countDownCoroutines(((cutDownTime / 1000)),
            scope = (context as AppCompatActivity).lifecycleScope,
            onFinish = {
                onCanceled()
            },
            onTick = {})
    }


    private fun onCanceled() {
        dismissAllowingStateLoss()

    }

    override fun onShowDialogWindowAnimations() = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCanceledOnTouchOutside(true)

    }

    override fun onSetDialogHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun onSetDialogWidth() = ViewGroup.LayoutParams.WRAP_CONTENT

}