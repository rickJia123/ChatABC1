package river.chat.businese_common.ui.view.dialog.simplelist

import androidx.recyclerview.widget.LinearLayoutManager
import river.chat.businese_common.ui.view.dialog.SimpleDialogConfig
import river.chat.lib_core.databinding.DialogSimpleListBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.main.activity.BaseActivity
import river.chat.lib_core.view.main.dialog.BaseBindingDialogFragment

/**
 * Created by beiyongChao on 2024/1/9
 * Description:
 */
class SimpleListDialog(var dialogActivity: BaseActivity) :
    BaseBindingDialogFragment<DialogSimpleListBinding>() {

    private var mConfig: SimpleDialogListConfig = SimpleDialogListConfig(

    )
    var mAdapter: SimpleListAdapter? = null

    companion object {
        @JvmStatic
        fun builder(activity: BaseActivity): SimpleListDialog = SimpleListDialog(activity)
    }

    /**
     * 初始化设置条目
     */
    fun config(config: SimpleDialogListConfig): SimpleListDialog {
        "SimpleDialog config".log()
        mConfig = config
        return this
    }


    override fun initDataBinding(binding: DialogSimpleListBinding) {
        initRecycleView(binding)
        binding.viewRoot.singleClick {
            closeDialog()
        }
    }

    private fun initRecycleView(binding: DialogSimpleListBinding) {
        mAdapter = SimpleListAdapter(totalCount = mConfig.list.size)
        mAdapter?.onClickItem = { item ->
            mConfig.list.forEach {
                if (it.content == item.content) {
                    it.click.invoke()
                    closeDialog()
                }
            }
        }
        binding.recycleView.adapter = mAdapter
        binding.recycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
            }
        mAdapter?.submitList(mConfig.list)
    }


    private fun closeDialog() {
        dismiss()
    }


    override fun onShowDialogWindowAnimations() = false

    fun show() {
        dialogActivity.supportFragmentManager?.let { show(it, "SimpleDialog") }
    }

}

