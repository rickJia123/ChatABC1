package river.chat.businese_common.ui.view.dialog

import river.chat.lib_core.databinding.DialogSimpleTipBinding
import river.chat.lib_core.utils.exts.actionVisible
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.main.activity.BaseActivity
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment

/**
 * Created by beiyongChao on 2023/6/9
 * Description:
 */
class SimpleDialog(var dialogActivity: BaseActivity) : BaseBindingDialogFragment<DialogSimpleTipBinding>() {

    private var mConfig: SimpleDialogConfig = SimpleDialogConfig(
        title = null,
        des = null,
        leftButtonStr = null,
        rightButtonStr = null,
        leftClick = {},
        rightClick = {}
    )

    companion object {
        @JvmStatic
        fun builder(activity: BaseActivity): SimpleDialog = SimpleDialog(activity)
    }

    /**
     * 初始化设置条目
     */
    fun config(config: SimpleDialogConfig): SimpleDialog {
        "SimpleDialog config".log()
        mConfig = config
        return this
    }


    override fun initDataBinding(binding: DialogSimpleTipBinding) {
        "SimpleDialog initDataBinding".log()
        binding.tvTitle.text = mConfig.title ?: ""
        binding.tvDes.text = mConfig.des ?: ""
        binding.tvLButton.text = mConfig.leftButtonStr ?: ""
        binding.tvRButton.text = mConfig.rightButtonStr ?: ""
        binding.tvTitle.actionVisible(mConfig.title?.isNotEmpty() == true)
        binding.tvDes.actionVisible(mConfig.des?.isNotEmpty() == true)
        binding.tvLButton.actionVisible(mConfig.leftButtonStr?.isNotEmpty() == true)
        binding.tvRButton.actionVisible(mConfig.rightButtonStr?.isNotEmpty() == true)
        binding.tvLButton.singleClick {
            closeDialog()
            mConfig.leftClick.invoke()
        }
        binding.tvRButton.singleClick {
            closeDialog()
            mConfig.rightClick.invoke()
        }
    }

    private fun closeDialog() {
        dismiss()
    }

    fun show() {
        dialogActivity.supportFragmentManager?.let { show(it, "SimpleDialog") }
    }

}

