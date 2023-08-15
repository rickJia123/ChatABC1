//package river.chat.businese_main.message
//
//
//import river.chat.lib_resource.model.MessageBean
//import river.chat.lib_resource.model.MessageSource
//import river.chat.lib_resource.model.MessageStatus
//import java.util.*
//
///**
// * Created by beiyongChao on 2023/3/12
// * Description:
// */
//object MessageActionHelper {
//
//    const val mDefaultMsgId = 12345L
//
//    /**
//     * 生成消息id
//     */
//    fun createMsgId(): Long {
//        return UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
////        return  10
//    }
//
//    /**
//     * 创建初始消息
//     */
//    fun buildDefaultMsg() = MessageBean().apply {
//        source = MessageSource.FROM_AI
//        avatar = river.chat.lib_resource.R.drawable.avatar_ai
//        id = mDefaultMsgId
//        this.time=System.currentTimeMillis()
//        content =
//            "Hello ！我是您的 Al 智能小伙伴，我叫 ChatMe ，您可以询问我任何问题，我将知无不言。我还能【 AI 创作】，你输入关键词，我可以为你写作文、写诗歌、写大纲、写情诗、写祝福、写检讨、写文案、写总结概要、写社交动态，优化文章，智能翻译、作业解答哦～希望我们可以一起成长，祝您玩得开心！\n"
//    }
//
////    /**
////     * 根据接口返回。创建AI 回复真实消息
////     */
////    fun buildAiAnswerMsg(msgId:Long,answer: String) = MessageBean().apply {
////        id = msgId
////        content = answer ?: ""
////        avatar = river.chat.lib_resource.R.drawable.avatar_ai
////        status= MessageStatus.COMPLETE
////        this.time=System.currentTimeMillis()
//////        user = buildAiUser()
////    }
//
//    /**
//     * 创建AI 回复空消息
//     */
//    fun buildAiAnswerEmptyMsg(msgId: Long,parentId: Long) = MessageBean().apply {
//        source = MessageSource.FROM_AI
//        id = msgId
//        this.parentId = parentId
//        status= MessageStatus.LOADING
//        avatar = river.chat.lib_resource.R.drawable.avatar_ai
//        content = "正在思考中哦。。。"
////        user = buildAiUser()
//    }
//
//
//    /**
//     * 创建本人发出的消息
//     */
//    fun buildSelfMsg(content: String) = MessageBean().apply {
//        this. source = MessageSource.FRE_SELF
//        this.  id = createMsgId()
//        this.content = content
//        this.time=System.currentTimeMillis()
//    }
//
//
////    /**
////     * 创建ai 角色
////     */
////    fun buildAiUser() = User().apply {
////        headImg = river.chat.lib_resource.R.drawable.avatar_ai
////    }
//
//
//}