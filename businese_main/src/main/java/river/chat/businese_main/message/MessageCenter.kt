package river.chat.businese_main.message

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.dataBase.MessageBox
import river.chat.businese_main.home.HomeActivity
import river.chat.businese_main.message.MessageHelper.buildAiAnswerEmptyMsg
import river.chat.businese_main.message.MessageHelper.buildAiAnswerMsg
import river.chat.businese_main.utils.logChat
import river.chat.businese_main.vip.VipManager
import river.chat.lib_core.event.BaseActionEvent
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.net.request.RequestResult
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.exts.safeToInt
import river.chat.lib_core.utils.exts.safeToLong
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_resource.model.MessageBean
import river.chat.lib_resource.model.MessageReceiveBean
import river.chat.lib_resource.model.MessageSource
import river.chat.lib_resource.model.MessageStatus
import river.chat.lib_core.utils.longan.log
import river.chat.lib_resource.model.MessageFlowBean

/**
 * Created by beiyongChao on 2023/3/12
 * Description:
 */
object MessageCenter {
    var msgReceiver = MutableSharedFlow<MessageBean>()
    var mActivity: HomeActivity? = null
    var mMsgViewModel: MessageViewModel? = null

    //本次打开app是否已经提示没有会期，第一次会推送到消息列表，第二次会弹窗
    private var mIsTipNoVip = false

    /**
     * 消息超时时间，超时的loading状态改为失败
     */
    var mMessageVaildTime = 30 * 1000L

    //存储消息请求时间
    var mRequestTimeMap = mutableMapOf<Long, Long>()

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
        var historyList = MessageBox.getMsgList()
        historyList.forEach {
            var msg = it
            if (msg.status == MessageStatus.LOADING) {
                msg.status = MessageStatus.FAIL_COMMON
                MessageBox.saveMsg(it)
            }
        }


    }

    /**
     * 监听接口消息请求
     */
    private fun observerMsg() {
        mActivity?.let {
            mMsgViewModel?.request?.chatRequestResult?.observe(it) { result ->
//                if (it.isSuccess) {
                result.data?.let {
                    var msg = it
//                    ("MessageCenter observerMsg:" + msg).log()
                    distributeMsg(buildAiAnswerMsg(msg))
//                }
                    //请求成功后，更新vip状态
                    if (result.isSuccess) {
                        VipManager.onUseVipTimes()
                    }
                }

            }
        }
    }

    fun onReceiveMsg(flowMsg: MessageBean) {
        var endTime = System.currentTimeMillis()
        var startTime = mRequestTimeMap[flowMsg.id] ?: 0
        LogUtil.i("requestAiByFlow 请求结束 总时间:" + (endTime - startTime))
        LogUtil.i("receiveEventStream MessageCenter :" + GsonKits.toJson(flowMsg))
        var result: RequestResult<MessageBean>? = null
        result = RequestResult(isSuccess = true, data = MessageBean().apply {
            this.content = flowMsg.content
            this.parentId = flowMsg.id
            this.time = if (flowMsg.time ?: 0 > 0) flowMsg.time else System.currentTimeMillis()
            this.status =
                if (flowMsg.failFlag == true) MessageStatus.FAIL_COMMON else MessageStatus.COMPLETE
            this.failFlag = flowMsg.failFlag
            this.failMsg = flowMsg.failMsg
        })

        result?.data?.let {
            var msg = it
            ("MessageCenter observerMsg:" + msg).log()
            distributeMsg(buildAiAnswerMsg(msg))
//                }
            //请求成功后，更新vip状态
            if (result.isSuccess) {
                VipManager.onUseVipTimes()
            }
        }
    }


    /**
     * 收到站内发过来的消息，先检查权限，然后请求接口，成功后分发消息消息到各个页面
     * @param isSendLocal 是否只发送到本地列表，不请求接口
     */
    fun postSendMsg(questionMsg: MessageBean) {
        if (checkPermission(questionMsg)) {
            if (questionMsg.source == MessageSource.FRE_SELF) {
                distributeMsg(questionMsg)
//                //发送ai回答空消息，用于占位
//                var aiMsgId = createMsgId()
                distributeMsg(buildAiAnswerEmptyMsg(questionMsg.id))
                mMsgViewModel?.request?.requestAi(
                    questionMsg.content ?: "", questionMsg.id.safeToString()
                )
            }
        }
    }

    /**
     * 分发消息(发送和接收)
     */
    private fun distributeMsg(msg: MessageBean) {
        GlobalScope.launch() {
//            ("MessageCenter distributeMsg:" + msg).log()
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
     * 收到消息后处理(发送和接收)
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
                    it.failMsg = receiveMsg.failMsg
                    it.failFlag = receiveMsg.failFlag
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
        var user = getPlugin<UserPlugin>()
        var isLogin = user.isLogin()
        if (!isLogin) {
            hasPermission = false
            getPlugin<UserPlugin>().check2Login { }
        }
        //本地发送前先 检查vip权限
        else {
            //本次打开app是否已经提示没有会期，第一次会推送到消息列表，第二次会弹窗
            //还没有推送到消息中
            if (!mIsTipNoVip) {
                if (!VipManager.isVip()) {
                    mIsTipNoVip = true
                }
            } else {
                //已经推送到消息中
                if (!VipManager.check2Vip()) {
                    hasPermission = false
                }
            }
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
//        if (canReload) {
        canReload = false
        postSendMsg(msg)
        GlobalScope.launch {
            ("MessageCenter beginReload 可以重试了：" + msg).logChat()
            delay(reloadTimeLimit.toLong())
            onReloadLimitFinish.invoke()
            canReload = true
        }
//        }
    }

    fun postHideSoftWindow() {
        EventCenter.postEvent(BaseActionEvent().apply {
            action = CommonEvent.HIDE_SOFT_WINDOW
        })
    }

    fun updateMsg(msg: MessageBean?) {
        msg?.let {
            MessageBox.saveMsg(it)
        }
    }


}




