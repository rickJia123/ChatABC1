package river.chat.businese_main.message

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.dataBase.MessageBox
import river.chat.businese_common.dataBase.MessageBox.saveMsg
import river.chat.businese_main.constants.MainConstants
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
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_resource.model.database.MessageBean
import river.chat.lib_resource.model.database.MessageReceiveBean
import river.chat.lib_resource.model.database.MessageSource
import river.chat.lib_resource.model.database.MessageStatus

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

    //存储消息请求时间
    var mRequestTimeMap = mutableMapOf<String, Long>()

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
        return msgList.filter { it.source != MessageSource.INVALID }
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
                saveMsg(it)
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
                    distributeMsg(buildAiAnswerMsg(msg))
//                }

                }

            }
        }
    }

    fun onReceiveMsg(flowMsg: MessageBean) {
        var endTime = System.currentTimeMillis()
        var startTime = mRequestTimeMap[flowMsg.id.safeToString()] ?: 0
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

        result.data?.let {
            var msg = it
            ("MessageCenter observerMsg:" + msg).log()
            distributeMsg(buildAiAnswerMsg(msg))
//                }
        }
    }

    fun onComplete() {
        VipManager.onUseVipTimes()
    }


    /**
     * 收到站内发过来的消息，先检查权限，然后请求接口，成功后分发消息消息到各个页面
     * @param isSendLocal 是否只发送到本地列表，不请求接口
     */
    fun postSendMsg(questionMsg: MessageBean) {
        if (questionMsg.source == MessageSource.SINGLE_PAY_TIP) {
            distributeMsg(questionMsg)
        } else
            if (checkPermission()) {
                if (questionMsg.source == MessageSource.FRE_SELF) {
                    distributeMsg(questionMsg)
//                //发送ai回答空消息，用于占位
//                var aiMsgId = createMsgId()
                    distributeMsg(buildAiAnswerEmptyMsg(questionMsg.id))
                    mMsgViewModel?.request?.requestByFlow(
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
                    saveMsg(it)
                }
            }
        }

        if (!needUpdateAiAnswer) {
            //保存消息到数据库
            saveMsg(receiveMsg)
        }

        return resultMsg
    }

    /**
     * 检查消息发送 权限
     */
    private fun checkPermission(): Boolean {
        var hasPermission = true
        //本地发送前先 检查vip权限
        //本次打开app是否已经提示没有会期，第一次会推送到消息列表，第二次会弹窗
        hasPermission = VipManager.checkRightsWithBusiness {
            when (it) {
                MainConstants.RIGHTS_MSG_TIP -> {
                    //最新一条是否是支付提示
                    var isLastPay =
                        getHistoryMsg().firstOrNull()?.source == MessageSource.SINGLE_PAY_TIP
                    if (isLastPay) {
                        VipManager.jump2VipPage()
                    } else {
                        //把之前的支付消息删除再发送新的，保证列表只有一个支付提示
                        getHistoryMsg().forEach {
                            if (it.source == MessageSource.SINGLE_PAY_TIP) {
                                it.source = MessageSource.INVALID
                                saveMsg(it)
                                return@forEach
                            }
                        }
                        postSendMsg(MessageHelper.buildPayMsg())
                    }
                }

                MainConstants.RIGHTS_JUMP_OPEN -> {
                    VipManager.jump2VipPage()
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
            saveMsg(it)
        }
    }

    fun toggleCollectionStatus(msg: MessageBean?, needPost: Boolean = true) {
        msg?.let {
            if (msg.isCollected == true) {
                msg.isCollected = false
                "取消收藏".toastSystem()
            } else {
                msg.isCollected = true
                "收藏成功".toastSystem()
            }
            saveMsg(it)
            if (needPost) {
                EventCenter.postEvent(BaseActionEvent().apply {
                    action = CommonEvent.COLLECTION_TOGGLE
                })
            }

        }
    }

}




