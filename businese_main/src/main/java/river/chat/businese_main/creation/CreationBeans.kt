package river.chat.businese_main.creation

import androidx.recyclerview.widget.DiffUtil
import river.chat.lib_resource.model.database.CardMsgBean


/**
 * 模版bean
 */
data class CreationBeans(
    var tabName: String = "",
    var templates: MutableList<CreationItemsBeans> = mutableListOf(),

    ) : java.io.Serializable

data class CreationItemsBeans(
    var id: Long = 0,
    var coverImg: String = "",
    var descr: String = "",
    var subTitle: String = "",
    var name: String = "",
    var examples: MutableList<String> = mutableListOf(),

    ) : java.io.Serializable
{
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<CreationItemsBeans>() {
            override fun areItemsTheSame(
                oldItem: CreationItemsBeans,
                newItem: CreationItemsBeans
            ): Boolean {
//                return oldItem.questionMsg.id == newItem.questionMsg.id
                return false
            }

            override fun areContentsTheSame(
                oldItem: CreationItemsBeans,
                newItem: CreationItemsBeans
            ): Boolean {
//                return oldItem == newItem
                return false
            }
        }
    }
}

