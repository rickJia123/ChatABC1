package river.chat.businese_common.dataBase

import river.chat.lib_core.storage.database.BaseBox
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_resource.model.database.AiPictureBean
import river.chat.lib_resource.model.database.AiPictureBean_
import river.chat.lib_resource.model.database.MessageStatus

/**
 * Created by beiyongChao on 2023/3/6
 * Description:
 */
object AiPictureBox : BaseBox<AiPictureBean>() {

    override fun getEntityClass() = AiPictureBean::class.java

    override fun exit() {

    }

    private var maxSize = 50


    /**
     * 按照问答卡片顺序返回
     */
    fun getMsgsOnCard(): MutableList<AiPictureBean> {
        var resultMsg = mutableListOf<AiPictureBean>()
        var allMsgs = getMsgList().sortedBy { it.time }
        allMsgs.filter { it.type == AiPictureBean.TYPE_QUESTION }.forEach { msg ->
            var questionId = msg.id
            resultMsg.add(msg)
            allMsgs.filter { it.type == AiPictureBean.TYPE_ANSWER && it.id == questionId && it.status == MessageStatus.COMPLETE }
                .firstOrNull()
                ?.let {
                    resultMsg.add(it)
                } ?: resultMsg.remove(msg)
            LogUtil.i("rick PictureFragment getMsgOnCard:" + msg.id + "::" + msg.content)
        }
        return resultMsg
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
    fun getAnswerById(id: String): AiPictureBean {
        //是否包含
        var msgList = getMsgList()
        return msgList.firstOrNull { it.id == id && it.type == AiPictureBean.TYPE_ANSWER && it.status == MessageStatus.COMPLETE }
            ?: AiPictureBean()
    }


    fun updateMsg(msg: AiPictureBean): Long {
        return box?.put(msg) ?: 0
    }

    fun saveMsg(msg: AiPictureBean): Long {
        if (getMsgList().size > maxSize) {
            box?.remove(getMsgList()[0].pictureId)
        }
        LogUtil.i("rick PictureFragment saveMsg:" + "::" + GsonKits.toJson(msg))
        return box?.put(msg) ?: 0
    }


    fun deleteAll() {
        box?.removeAll()
    }
}


