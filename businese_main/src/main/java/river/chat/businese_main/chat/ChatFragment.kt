package river.chat.businese_main.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.constants.ModelEvent
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.constants.ChatConstants
import river.chat.businese_main.home.HomeViewModel
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageCenter.checkMsgStatus
import river.chat.businese_main.message.MessageHelper
import river.chat.businese_main.message.MessageHelper.buildCardMsgList
import river.chat.business_main.databinding.FragmentChatBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.utils.common.SoftKeyboardStateHelper
import river.chat.lib_core.utils.exts.height
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.pxToDp
import river.chat.lib_core.view.ktx.bind
import river.chat.lib_core.view.ktx.newInstance
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment
import river.chat.lib_resource.model.database.CardMsgBean

class ChatFragment :
    BaseBindingViewModelFragment<FragmentChatBinding, HomeViewModel>() {


    private var mMsgList = mutableListOf<CardMsgBean>()
    private var mAdapter: ChatAdapter? = null

    private var holderHeight = 0

    //当前聊天模式
    private var mCurrentMode = ChatConstants.CHAT_MODE_NORMAl

    companion object {
        @JvmStatic
        fun newInstance(currentMode: String) = ChatFragment().newInstance(currentMode)
    }

    override fun initDataBinding(binding: FragmentChatBinding) {
        onLoad()
        super.initDataBinding(binding)
        initRecycleView()
        initMsgService(binding)
        initEventListener()
        viewModel.bind(this)
        mBinding.inputView.post {
            holderHeight = mBinding.inputView.height
            mBinding.viewHolder.height(holderHeight)
        }
    }

    override fun initParams(params: Array<Any?>?) {
        super.initParams(params)
        mCurrentMode = params?.getOrNull(0) as? String ?: ""
    }

    private fun initEventListener() {
        EventCenter.registerReceiveEvent((context as AppCompatActivity).lifecycleScope) {
            when (it.action) {
                CommonEvent.RECEIVE_MSG_INPUT -> {
                    mBinding.inputView.setInput(it.simpleValue)
                }

                CommonEvent.HIDE_SOFT_WINDOW -> {
                    mBinding.inputView.hideSoftInput()
                }

                CommonEvent.COLLECTION_TOGGLE -> {
                    refreshMsg(false)
                }

                ModelEvent.EVENT_SOFT_OPEN -> {
                    var disY = SoftKeyboardStateHelper.lastSoftKeyboardHeightInPx * 1f
                    mBinding.viewHolder.height(disY.toInt().pxToDp() - 100)
                }

                ModelEvent.EVENT_SOFT_CLOSE -> {
                    mBinding.viewHolder.height(holderHeight)
                }
            }
        }
    }


    /**
     * 初始化消息服务
     */
    private fun initMsgService(binding: FragmentChatBinding) {
        //判断当前列表是否已经初始化过
        var hasDefaultMsg = false

        //判断是否有ai的第一条消息
        mMsgList.forEach {
            if (it.answerMsg.id == MessageHelper.mDefaultMsgId) {
                hasDefaultMsg = true
                return@forEach
            }
        }
        if (!hasDefaultMsg) {
            refreshMsg()
        }

        MessageCenter.registerReceiveMsg(lifecycleScope) { msg ->
            var historyMsg = MessageCenter.getHistoryMsg()
            ("ChatFragment receiver msg:" + msg + ":::" + historyMsg.size).log()
            var newList = buildCardMsgList(historyMsg)
            var isMsgNew = newList.size > mMsgList.size

            mMsgList.clear()
            mMsgList.addAll(newList)

            mAdapter?.submitList(mMsgList) {
                mAdapter?.notifyDataSetChanged()
                mBinding.recycleView.post {
                    scroll2Bottom()
                }
            }

            ("ChatFragment receiver msg:" + msg + ":::是否重新加载消息：" + "::::" + mMsgList.size).log()
            if (isMsgNew) {
                scroll2Bottom()
            }
        }
        binding.inputView.requestFocus()
    }

    private fun refreshMsg(needScroll: Boolean = true) {
        mMsgList.clear()
        checkMsgStatus()
        mMsgList.addAll(buildCardMsgList(MessageCenter.getHistoryMsg()))
        mAdapter?.submitList(mMsgList)
        mAdapter?.notifyDataSetChanged()
        if (needScroll) {
            scroll2Bottom()
        }

    }

    private fun scroll2Bottom() {
        mBinding.recycleView.scrollToPosition(mMsgList.size - 1)
    }

    private fun initRecycleView() {
        mAdapter = ChatAdapter()
        mBinding.recycleView.adapter = mAdapter
        mBinding.recycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
//                this.stackFromEnd = true
            }
        mBinding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                MessageCenter.postHideSoftWindow()
            }
        })
        mBinding.recycleView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                scroll2Bottom()
            } else {

            }
        }
    }

    override fun onPause() {
        super.onPause()
        mBinding.inputView.hideSoftInput()
    }


    override fun createViewModel() = getActivityScopeViewModel(HomeViewModel::class.java)


}