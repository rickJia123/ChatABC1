package river.chat.businese_common.dataBase

import river.chat.lib_core.storage.database.BaseBox
import river.chat.lib_resource.model.database.AiPictureBean
import river.chat.lib_resource.model.database.AiPictureBean_

/**
 * Created by beiyongChao on 2023/3/6
 * Description:
 */
object AiPictureBox : BaseBox<AiPictureBean>() {

    override fun getEntityClass() = AiPictureBean::class.java

    override fun exit() {

    }


    /**
     * 获取全部消息记录
     */
    fun getMsgList(): MutableList<AiPictureBean> {
        return box.query().order(AiPictureBean_.time).build().find()
    }

    /**
     * 根据id获取消息
     */
    fun getMsgById(id: Long): AiPictureBean {
        //是否包含
        return box?.get(if (id<=0) 1 else id) ?: AiPictureBean()
    }



    fun updateMsg(msg: AiPictureBean): Long {
        return box?.put(msg) ?: 0
    }

    fun saveMsg(msg: AiPictureBean): Long {
//        ("MessageBox saveMsg:$msg").log()
        return box?.put(msg) ?: 0
    }

    fun deleteAll() {
        box?.removeAll()
    }
}


