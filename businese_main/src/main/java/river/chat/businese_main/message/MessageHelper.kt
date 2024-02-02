package river.chat.businese_main.message


import river.chat.businese_common.dataBase.MessageBox
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_resource.model.database.CardMsgBean
import river.chat.lib_resource.model.database.MessageBean
import river.chat.lib_resource.model.database.MessageSource
import river.chat.lib_resource.model.database.MessageStatus
import java.util.*

/**
 * Created by beiyongChao on 2023/3/12
 * Description:
 */
object MessageHelper {

    const val mDefaultMsgId = 12345L

    const val CHAT_TIP_LOADING = "正在思考中哦。。。"

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
        source = MessageSource.SINGLE_AI_DEFAULT
        avatar = river.chat.lib_resource.R.drawable.avatar_ai
        id = mDefaultMsgId
        this.time = System.currentTimeMillis()
        content =
            "Hello ！我是您的 Al 智能小伙伴\n我叫 GPTEvery ，您可以询问我任何问题，我将知无不言。我还能【 AI 创作】，你输入关键词，我可以为你写作文、写诗歌、写大纲、写情诗、写祝福、写检讨、写文案、写总结概要、写社交动态，优化文章，智能翻译、作业解答哦～\n希望我们可以一起成长，祝您玩得开心！"
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
        this.status = answerMsg.status
        this.time = answerMsg.time
        this.failMsg = answerMsg.failMsg
        this.failFlag = answerMsg.failFlag
//        user = buildAiUser()
    }

    /**
     * 创建付费消息
     */
    fun buildPayMsg() = MessageBean().apply {
        this.source = MessageSource.SINGLE_PAY_TIP
        this.id = createMsgId()
        this.time = System.currentTimeMillis()
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
            addAll(msgList.filter { it.source == MessageSource.FRE_SELF || it.source == MessageSource.SINGLE_AI_DEFAULT || it.source == MessageSource.SINGLE_PAY_TIP })
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
            //如果是用户发出的则寻找答案配对
            if (question.source == MessageSource.FRE_SELF) {
                var matchAnswer =
                    answerSourceList.firstOrNull { it.parentId == question.id }
                        ?: MessageBean()
                if (matchAnswer.status != MessageStatus.FAIL_LIMIT) {
                    cardMsgList.add(CardMsgBean(question, matchAnswer))
                }
            }
            //如果是单独消息，直接添加
            else {
                cardMsgList.add(CardMsgBean(question, MessageBean()))
            }

        }


        return cardMsgList
    }

    //判断是否是重新加载的消息，如果是，只刷新当前item
    fun isReloadMsg(answerMsg: MessageBean?): Boolean {
        var isLastSuccess =
            MessageBox.getMsgById(answerMsg?.id ?: 100).status == MessageStatus.COMPLETE
//        var isLastSuccess = this.answerMsg?.status == MessageStatus.COMPLETE
        var isCurrentSuccess = answerMsg?.status == MessageStatus.COMPLETE

        LogUtil.i("rick MessageHelper isReloadMsg:" + (isLastSuccess && !isCurrentSuccess))
        return isLastSuccess && !isCurrentSuccess
    }


}