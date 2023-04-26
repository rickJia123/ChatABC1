package river.chat.lib_core.view.common

import androidx.appcompat.app.AppCompatActivity
import river.chat.lib_core.databinding.FragmentLoadingBinding
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment

/**
 * Created by beiyongChao on 2023/3/15
 * Description:
 */
class LoadingDialog : BaseBindingDialogFragment<FragmentLoadingBinding>() {


    companion object {
        private var dialog: LoadingDialog? = null
        fun launch(
            activity: AppCompatActivity
        ) {
            dialog = LoadingDialog()
            dialog?.show(
                activity.supportFragmentManager, "ToastTipDialog"
            )
        }

        fun disMiss() {
            dialog?.dismiss()
        }
    }


    override fun initDataBinding(binding: FragmentLoadingBinding) {
        binding.ivLoading.animate().rotation(360f)
    }


    fun showDialog() {

    }

}