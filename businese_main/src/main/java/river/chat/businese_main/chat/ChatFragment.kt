package river.chat.businese_main.chat

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.home.HomeViewModel
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageCenter.checkMsgStatus
import river.chat.businese_main.message.MessageHelper
import river.chat.businese_main.message.MessageHelper.buildCardMsgList
import river.chat.business_main.databinding.FragmentChatBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment
import river.chat.lib_resource.model.database.CardMsgBean

class ChatFragment :
    BaseBindingViewModelFragment<FragmentChatBinding, ChatViewModel>() {

    private var mHomeActivityVm: HomeViewModel? = null

    private var mMsgList = mutableListOf<CardMsgBean>()
    private var mAdapter: ChatAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() = ChatFragment()
    }

    override fun initDataBinding(binding: FragmentChatBinding) {
        onLoad()
        super.initDataBinding(binding)
        initRecycleView()
        initMsgService(binding)
        initEventListener()


    }

    private fun initEventListener() {
        EventCenter.registerReceiveEvent((context as AppCompatActivity).lifecycleScope) {
            when (it.action) {
                CommonEvent.SEND_MSG_SUCCESS -> {

                }

                CommonEvent.HIDE_SOFT_WINDOW -> {
                    mBinding.inputView.hideSoftInput()
                }

                CommonEvent.COLLECTION_TOGGLE -> {
                    refreshMsg(false)
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

        //rick todo
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

//            var isReloadMsg=MessageHelper.isReloadMsg(historyMsg.filter { it.id==msg.msg?.id }.firstOrNull())
            ("ChatFragment receiver msg:" + msg + ":::是否重新加载消息：" + "::::" + mMsgList.size).log()
            if (isMsgNew) {
                scroll2Bottom()
            }
        }
        binding.inputView.requestFocus()
//        MessageCenter.postReceiveMsg(MessageHelper.buildDefaultMsg())
    }

    private fun refreshMsg(needScroll: Boolean = true) {
        mMsgList.clear()
        checkMsgStatus()
        mMsgList.addAll(buildCardMsgList(MessageCenter.getHistoryMsg()))
        mAdapter?.submitList(mMsgList)
        mAdapter?.notifyDataSetChanged()
        if (needScroll) {
            scroll2Bottom()
//            mBinding.recycleView.scrollBy(0, 1980 * 1000)
        }

    }


//    private fun scrollToBottom() {
//        mBinding.recycleView.postDelayed({
//            mBinding.recycleView.scrollBy(0, 1980 * 1000)
//        }, 100)
//    }
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


    override fun createViewModel(): ChatViewModel {
        mHomeActivityVm = getActivityScopeViewModel(HomeViewModel::class.java)
        return ChatViewModel()
    }


}