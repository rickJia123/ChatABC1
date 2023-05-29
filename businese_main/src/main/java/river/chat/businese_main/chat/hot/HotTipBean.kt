package river.chat.businese_main.chat.hot

import io.objectbox.annotation.Entity

/**
 * Created by beiyongChao on 2023/3/2
 * Description:
 */

@Entity
data class HotTipBean(


    var hotQuestion: String = ""



) : java.io.Serializable {

}
