package river.chat.lib_resource.model.database

import androidx.recyclerview.widget.DiffUtil
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by beiyongChao on 2024/1/17
 * Description:
 */
@Entity
data class AiPictureBean(

    @Id(assignable = true)
    var pictureId: Long = 0,//消息id

    //用于匹配问答的id
    var id: String? = "",
    var content: String? = "",
    var time: Long? = 0,
    var failFlag: Boolean? = false,
    var failMsg: String? = "",

    //消息类型：0 问题 1 答案 2 提示
    var type: Int = 0,

    var status: Int? = MessageStatus.COMPLETE,
    var question: String? = "",
) {
    companion object {
        const val TYPE_QUESTION = 0
        const val TYPE_ANSWER = 1
        const val TYPE_TIP = 2

        //AI 首次消息
        const val TYPE_AI = 3
        val DIFF = object : DiffUtil.ItemCallback<AiPictureBean>() {
            override fun areItemsTheSame(
                oldItem: AiPictureBean,
                newItem: AiPictureBean
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: AiPictureBean,
                newItem: AiPictureBean
            ): Boolean {
                return false
            }
        }
    }
}