package river.chat.businese_main.message

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import river.chat.businese_common.dataBase.MessageBox
import river.chat.businese_main.home.HomeActivity
import river.chat.businese_main.message.MessageHelper.buildAiAnswerEmptyMsg
import river.chat.businese_main.message.MessageHelper.buildAiAnswerMsg
import river.chat.businese_main.utils.logChat
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.storage.database.model.MessageBean
import river.chat.lib_core.storage.database.model.MessageReceiveBean
import river.chat.lib_core.storage.database.model.MessageSource
import river.chat.lib_core.storage.database.model.MessageStatus
import river.chat.lib_core.utils.longan.log

/**
 * Created by beiyongChao on 2023/3/12
 * Description:
 */
object MessageCenter {
    var msgReceiver = MutableSharedFlow<MessageBean>()
    var mActivity: HomeActivity? = null
    var mMsgViewModel: MessageViewModel? = null

    /**
     * 消息超时时间，超时的loading状态改为失败
     */
    var mMessageVaildTime = 30 * 1000L

    init {

    }

    /**
     * 注册消息中心
     */
    fun registerMsgCenter(activity: HomeActivity) {
        mActivity = activity
        mMsgViewModel = activity.getActivityScopeViewModel(MessageViewModel::class.java)
        observerMsg()
    }

    /**
     * 每次打开获取机器人打招呼消息和历史消息
     */
    fun getHistoryMsg(): List<MessageBean> {
        var msgList = MessageBox.getMsgList()
        msgList.add(0, MessageHelper.buildDefaultMsg())
        return msgList
    }


    /**
     * 检查历史消息状态
     */
    fun checkMsgStatus() {
        var historyList=MessageBox.getMsgList()
        historyList.forEach {
            var msg=it
            if (msg.status==MessageStatus.LOADING) {
                msg.status = MessageStatus.FAIL
                MessageBox.saveMsg(it)
            }
        }


    }

    /**
     * 监听接口消息请求
     */
    private fun observerMsg() {
        mActivity?.let {
            mMsgViewModel?.request?.chatRequestResult?.observe(it) {
//                if (it.isSuccess) {
                ("MessageCenter observerMsg:" + it.data).log()
                distributeMsg(buildAiAnswerMsg(it.data ?: MessageBean()))
//                }
            }
        }
    }


    /**
     * 收到站内发过来的消息，先检查权限，然后请求接口，成功后分发消息消息到各个页面
     * @param isSendLocal 是否只发送到本地列表，不请求接口
     */
    fun postReceiveMsg(questionMsg: MessageBean) {
        if (checkPermission(questionMsg)) {
            if (questionMsg.source == MessageSource.FRE_SELF) {
                distributeMsg(questionMsg)
//                //发送ai回答空消息，用于占位
//                var aiMsgId = createMsgId()
                distributeMsg(buildAiAnswerEmptyMsg(questionMsg.id))
                mMsgViewModel?.request?.requestAi(
                    questionMsg.content ?: "", questionMsg.id.toString()
                )
            }
        }
    }

    /**
     * 分发消息
     */
    private fun distributeMsg(msg: MessageBean) {
        GlobalScope.launch() {
            ("MessageCenter distributeMsg:" + msg).log()
            msgReceiver.emit(msg)
        }
    }

    /**
     * 注册监听收到分发过来的消息，目标页面注册即可接收
     */
    fun registerReceiveMsg(scope: CoroutineScope, msgCallback: (MessageReceiveBean) -> Unit = {}) {
        scope.launch {
            msgReceiver.filter {
                true
                //过滤消息
//                    !it.content.isNullOrEmpty()
            }.map { handleReceiveMsg(scope, it) }.onEach { }.flowOn(Dispatchers.Default)
                .collect {
                    ("MessageCenter msgReceiver:" + it).log()
                    MessageReceiveBean(it).apply(msgCallback)
                }
        }
    }

    /**
     * 收到消息后处理
     */
    private fun handleReceiveMsg(scope: CoroutineScope, receiveMsg: MessageBean): MessageBean {
        //是否需要更新ai发送消息
        var needUpdateAiAnswer = false
        var resultMsg = receiveMsg
        //如果ai消息并且状态不是发送中，更新消息
        if (receiveMsg.source == MessageSource.FROM_AI) {
            if (receiveMsg.status != MessageStatus.LOADING) {
                resultMsg = MessageBox.getMsgById(receiveMsg.id)
                ("MessageCenter 匹配到空消息:" + resultMsg).log()
                needUpdateAiAnswer = true
                resultMsg.let {
                    it.status = receiveMsg.status
                    it.content = receiveMsg.content
                    it.time = receiveMsg.time
                    it.id = receiveMsg.id
                    it.parentId = receiveMsg.parentId
                    MessageBox.saveMsg(it)
                }
            }
        }

        if (!needUpdateAiAnswer) {
            //保存消息到数据库
            MessageBox.saveMsg(receiveMsg)
        }

        return resultMsg
    }

    /**
     * 检查消息发送 权限
     */
    private fun checkPermission(msg: MessageBean): Boolean {
        var hasPermission = true
        var isLogin = getPlugin<UserPlugin>().isLogin()
        if (!isLogin) {
            hasPermission = false
            getPlugin<UserPlugin>().check2Login { }
        }
        return hasPermission
    }


    ///////////////////////////////////////////////////////////////////////////
    // 消息重试 开始
    ///////////////////////////////////////////////////////////////////////////

    //是否可以再次重试
    var canReload = true

    //重试最小间隔时间-20s
    var reloadTimeLimit = 10 * 10000


    /**
     * 重新发送消息
     */
    fun beginReload(msg: MessageBean, onReloadLimitFinish: () -> Unit = {}) {
        if (canReload) {
            canReload = false
            postReceiveMsg(msg)
            GlobalScope.launch {
                ("MessageCenter beginReload 可以重试了：" + msg).logChat()
                delay(reloadTimeLimit.toLong())
                onReloadLimitFinish.invoke()
                canReload = true
            }
        }
    }

}




