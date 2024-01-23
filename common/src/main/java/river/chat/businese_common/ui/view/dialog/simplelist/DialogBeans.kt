package river.chat.businese_common.ui.view.dialog.simplelist

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by beiyongChao on 2024/1/10
 * Description:
 */

data class SimpleDialogListConfig(
    var list: MutableList<SimpleDialogListBean> = mutableListOf(),
)



data class SimpleDialogListBean(
    var content: String,
    var click: () -> Unit = {}
) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<SimpleDialogListBean>() {
            override fun areItemsTheSame(
                oldItem: SimpleDialogListBean,
                newItem: SimpleDialogListBean
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: SimpleDialogListBean,
                newItem: SimpleDialogListBean
            ): Boolean {
                return false
            }
        }
    }
}