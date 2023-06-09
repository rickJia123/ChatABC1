package river.chat.businese_main.message


import river.chat.lib_core.storage.database.model.CardMsgBean
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.storage.database.model.MessageSource
import river.chat.lib_core.storage.database.model.MessageStatus
import java.util.*

/**
 * Created by beiyongChao on 2023/3/12
 * Description:
 */
object MessageHelper {

    const val mDefaultMsgId = 12345L

    const val CHAT_TIP_LOADING="正在思考中哦。。。"
    const val CHAT_TIP_FAIL="ChatGpt 服务异常，请稍后再试！"

    /**
     * 生成消息id
     */
    fun createMsgId(): Long {
        return UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
//        return  10
    }

    /**
     * 创建初始消息
     */
    fun buildDefaultMsg() = MessageBean().apply {
        source = MessageSource.FROM_AI
        avatar = river.chat.lib_resource.R.drawable.avatar_ai
        id = mDefaultMsgId
        this.time = System.currentTimeMillis()
        content =
            "Hello ！我是您的 Al 智能小伙伴，我叫 ChatMe ，您可以询问我任何问题，我将知无不言。我还能【 AI 创作】，你输入关键词，我可以为你写作文、写诗歌、写大纲、写情诗、写祝福、写检讨、写文案、写总结概要、写社交动态，优化文章，智能翻译、作业解答哦～希望我们可以一起成长，祝您玩得开心！\n"
    }



    /**
     * 创建AI 回复空消息
     */
    fun buildAiAnswerEmptyMsg(parentId: Long) = MessageBean().apply {
        source = MessageSource.FROM_AI
        id = parentId - 100
        this.parentId = parentId
        status = MessageStatus.LOADING
        avatar = river.chat.lib_resource.R.drawable.avatar_ai
        content = CHAT_TIP_LOADING
//        user = buildAiUser()
    }


    /**
     * 创建本人发出的消息
     */
    fun buildSelfMsg(content: String) = MessageBean().apply {
        this.source = MessageSource.FRE_SELF
        this.id = createMsgId()
        this.content = content
        this.time = System.currentTimeMillis()
    }

    /**
     * 根据接口返回。创建AI 回复真实消息
     */
    fun buildAiAnswerMsg(answerMsg: MessageBean) = MessageBean().apply {
        id = answerMsg.parentId - 100
        this.content = answerMsg.content
        this.parentId = answerMsg.parentId
        this.avatar = river.chat.lib_resource.R.drawable.avatar_ai
        this.status =answerMsg.status
        this.time = answerMsg.time
//        user = buildAiUser()
    }


    /**
     * 把消息列表组装成卡片内成对的消息
     */
    fun buildCardMsgList(msgList: List<MessageBean>): MutableList<CardMsgBean> {
        var cardMsgList = mutableListOf<CardMsgBean>()

        /**
         * 问题列表
         */
        var questionSourceList = mutableListOf<MessageBean>().apply {
            addAll(msgList.filter { it.source == MessageSource.FRE_SELF })
        }

        /**
         * 回答列表
         */
        var answerSourceList = mutableListOf<MessageBean>().apply {
            addAll(msgList.filter { it.source == MessageSource.FROM_AI })
        }

        /**
         * 遍历问题，找到对应的回答
         */
        questionSourceList.forEach { question ->
            var matchAnswer =
                answerSourceList.firstOrNull { it.parentId == question.id }
                    ?: MessageBean()
            cardMsgList.add(CardMsgBean(question, matchAnswer))
        }

        return cardMsgList
    }


}