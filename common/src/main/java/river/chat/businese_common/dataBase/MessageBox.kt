package river.chat.businese_common.dataBase

import river.chat.lib_core.storage.database.BaseBox
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.storage.database.model.MessageBean_
import river.chat.lib_core.storage.database.model.MessageSource
import river.chat.lib_core.utils.longan.log

/**
 * Created by beiyongChao on 2023/3/6
 * Description:
 */
object MessageBox : BaseBox<MessageBean>() {

    override fun getEntityClass() = MessageBean::class.java

    override fun exit() {

    }


    /**
     * 获取全部消息记录
     */
    fun getMsgList(): MutableList<MessageBean> {
        return box.query().order(MessageBean_.time).build().find()
    }

    /**
     * 根据id获取消息
     */
    fun getMsgById(id: Long): MessageBean {
        //是否包含
        return box?.get(if (id<=0) 1 else id) ?: MessageBean()
    }


//    /**
//     * 根据id匹配获取问答
//     * 来源是ai答案
//     */
//    private fun getMsgPairMsg(msg: MessageBean): MessageBean {
//        return box?.query()?.equal(MessageBean_.id, msg.parentId)?.build()?.find()?.first()
//            ?: MessageBean()
//    }


//    /**
//     * 获取自己提问过并且成功的问题
//     */
//    fun getSuccessMsgFromSelf(): List<MessageBean> {
//        return getMsgListBySource(MessageSource.FRE_SELF)
//    }
//
//    /**
//     * 根据来源获取消息记录
//     */
//    private fun getMsgListBySource(source: Int): List<MessageBean> {
//        return box?.query()?.equal(MessageBean_.source, source)?.build()?.find() ?: emptyList()
//    }


    fun updateMsg(msg: MessageBean): Long {
        return box?.put(msg) ?: 0
    }

    fun saveMsg(msg: MessageBean): Long {
        ("MessageBox saveMsg:$msg").log()
        return box?.put(msg) ?: 0
    }

    fun deleteAll() {
        box?.removeAll()
    }
}


