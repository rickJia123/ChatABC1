package river.chat.businese_main.chat

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.home.HomeViewModel
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageCenter.checkMsgStatus
import river.chat.businese_main.message.MessageHelper
import river.chat.businese_main.message.MessageHelper.buildCardMsgList
import river.chat.business_main.databinding.FragmentChatBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment
import river.chat.lib_resource.model.CardMsgBean

class ChatFragment :
    BaseBindingViewModelFragment<FragmentChatBinding, ChatViewModel>() {

    private var mHomeActivityVm: HomeViewModel? = null

    private var mMsgList = mutableListOf<CardMsgBean>()
    private val mAdapter by lazy {
        ChatAdapter().apply {
            onClickItem = {

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChatFragment()
    }

    override fun initDataBinding(binding: FragmentChatBinding) {
        onLoad()
        super.initDataBinding(binding)
        initMsgService(binding)
        initEventListener()
        initRecycleView()
    }

    private fun initEventListener() {
        EventCenter.registerReceiveEvent((context as AppCompatActivity).lifecycleScope) {
            when (it.action) {
                CommonEvent.SEND_MSG_SUCCESS -> {

                }

                CommonEvent.HIDE_SOFT_WINDOW -> {
                    mBinding.inputView.hideSoftInput()
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
        mMsgList.forEach {
            if (it.answerMsg.id == MessageHelper.mDefaultMsgId) {
                hasDefaultMsg = true
                return@forEach
            }
        }
        if (!hasDefaultMsg) {
            checkMsgStatus()
            mMsgList.addAll(buildCardMsgList(MessageCenter.getHistoryMsg()))
            mAdapter.submitList(mMsgList)
            scrollToBottom()
        }

        MessageCenter.registerReceiveMsg(lifecycleScope) { msg ->
            var historyMsg=MessageCenter.getHistoryMsg()
            ("ChatFragment receiver msg:" + msg + ":::" + historyMsg.size).log()
            var newList = buildCardMsgList(historyMsg)
            var isMsgNew = newList.size > mMsgList.size

            mMsgList.clear()
            mMsgList.addAll(newList)
            mAdapter.submitList(mMsgList)
            mAdapter.notifyDataSetChanged()
//            var isReloadMsg=MessageHelper.isReloadMsg(historyMsg.filter { it.id==msg.msg?.id }.firstOrNull())
            ("ChatFragment receiver msg:" + msg + ":::是否重新加载消息："  + "::::" + mMsgList.size).log()
            if (isMsgNew) {
                scrollToBottom()
            }
        }
        binding.inputView.requestFocus()
//        MessageCenter.postReceiveMsg(MessageHelper.buildDefaultMsg())
    }


    private fun scrollToBottom() {
        mBinding.recycleView.postDelayed({
            mBinding.recycleView.scrollBy(0, 1980 * 1000)
        }, 100)
    }

    private fun initRecycleView() {
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
                mBinding.recycleView.scrollBy(0, 1980 * 1000)
            } else {

            }
        }
    }


    override fun createViewModel(): ChatViewModel {
        mHomeActivityVm = getActivityScopeViewModel(HomeViewModel::class.java)
        return ChatViewModel()
    }


}