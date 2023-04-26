package river.chat.businese_main.message


import river.chat.lib_core.storage.database.model.User
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageSource

/**
 * Created by beiyongChao on 2023/3/12
 * Description:
 */
object MessageHelper {

    /**
     * 创建初始消息
     */
    fun buildDefaultMsg() = MessageBean().apply {
        source = MessageSource.FROM_AI
        user = buildAiUser()
        id = 0
        msg =
            "Hello ！我是您的 Al 智能小伙伴，我叫 ChatMe ，您可以询问我任何问题，我将知无不言。我还能【 AI 创作】，你输入关键词，我可以为你写作文、写诗歌、写大纲、写情诗、写祝福、写检讨、写文案、写总结概要、写社交动态，优化文章，智能翻译、作业解答哦～希望我们可以一起成长，祝您玩得开心！\n"
    }

    /**
     * 创建AI 自动回复消息
     */
    fun buildAiReceiveMsg() = MessageBean().apply {
        source = MessageSource.FROM_AI
        id = 0
        msg =
            "91网址我不知道哦"
        user = buildAiUser()
    }

    /**
     * 创建本人发出的消息
     */
    fun buildSelfMsg(content: String) = MessageBean().apply {
        source = MessageSource.FRE_SELF
        id = 0
        msg = content
    }


    /**
     * 创建ai 角色
     */
    fun buildAiUser() = User().apply {
        headImg = river.chat.lib_resource.R.drawable.avatar_ai
    }


}