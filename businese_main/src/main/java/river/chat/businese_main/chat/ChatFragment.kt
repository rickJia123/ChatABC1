package river.chat.businese_main.chat

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment

class ChatFragment :
    BaseBindingViewModelFragment<FragmentChatBinding, ChatViewModel>() {

    private var mHomeActivityVm: HomeViewModel? = null

    private val userPlugin: UserPlugin by inject()

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
                    mBinding.inputView.clearInput()
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
        viewModel.data.forEach {
            if (it.answerMsg.id == MessageHelper.mDefaultMsgId) {
                hasDefaultMsg = true
                return@forEach
            }
        }
        if (!hasDefaultMsg) {
            checkMsgStatus()
            viewModel.data.addAll(buildCardMsgList(MessageCenter.getHistoryMsg()))
            scrollToBottom(binding)
        }

        MessageCenter.registerReceiveMsg(lifecycleScope) { msg ->
            viewModel.data.clear()
            viewModel.data.addAll(buildCardMsgList(MessageCenter.getHistoryMsg()))
//            var position = viewModel.data.indexOfFirst { it.id == msg.msg?.id }

//            if (position >= 0) {
//                viewModel.data[position] = msg.msg
//            } else {
//                viewModel.data.add(msg.msg)
//            }
            ("ChatFragment receiver msg:" + msg + ":::" + 0 + "::::" + viewModel.data.size).log()

            scrollToBottom(binding)
        }
        binding.inputView.requestFocus()
//        MessageCenter.postReceiveMsg(MessageHelper.buildDefaultMsg())
    }

    private fun scrollToBottom(binding: FragmentChatBinding) {
        binding.recycleView.post {
//            binding.recycleView.scrollTo(viewModel.data.lastIndex)
            binding.recycleView.scrollBy(0, 10000000)
        }
    }

    private fun initRecycleView() {
        mBinding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                MessageCenter.postHideSoftWindow()
            }
        })
    }


    override fun createViewModel(): ChatViewModel {
        mHomeActivityVm = getActivityScopeViewModel(HomeViewModel::class.java)
        return ChatViewModel()
    }


}