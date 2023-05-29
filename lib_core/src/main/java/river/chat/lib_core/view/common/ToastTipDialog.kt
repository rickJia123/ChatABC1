package river.chat.lib_core.view.common

import android.graphics.Color
import android.os.Build
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
        var window = dialog?.window
//        window?.decorView?.systemUiVisibility = getSystemUiVisibility()
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window?.statusBarColor = Color.TRANSPARENT
        var layoutParams = window?.attributes;//获取dialog布局的参数
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT//全屏
        layoutParams?.height = WindowManager.LayoutParams.MATCH_PARENT//全屏
        //设置导航栏颜
        window?.navigationBarColor = Color.TRANSPARENT
        //内容扩展到导航栏
        window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
        if (Build.VERSION.SDK_INT >= 28) {
            layoutParams?.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window?.attributes = layoutParams

    }

    override fun onSetDialogHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun onSetDialogWidth() = ViewGroup.LayoutParams.WRAP_CONTENT

}