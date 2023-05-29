package river.chat.businese_main.chat

import androidx.lifecycle.lifecycleScope
import org.koin.android.ext.android.inject
import river.chat.businese_main.home.HomeViewModel
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageHelper
import river.chat.businese_main.message.MessageHelper.buildCardMsgList
import river.chat.business_main.databinding.FragmentChatBinding
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment

class ChatFragment :
    BaseBindingViewModelFragment<FragmentChatBinding, ChatViewModel>() {

    private var mHomeActivityVm: HomeViewModel? = null

    private val userPlugin: UserPlugin by inject()

    override fun initDataBinding(binding: FragmentChatBinding) {
        super.initDataBinding(binding)
        initMsgService(binding)
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
            msg.msg.toString().log()

            scrollToBottom(binding)
        }
//        MessageCenter.postReceiveMsg(MessageHelper.buildDefaultMsg())
    }

    private fun scrollToBottom(binding: FragmentChatBinding) {
        binding.recycleView.post {
//            binding.recycleView.scrollTo(viewModel.data.lastIndex)
            binding.recycleView.scrollBy(0, 10000000)
        }
    }


    override fun createViewModel(): ChatViewModel {
        mHomeActivityVm = getActivityScopeViewModel(HomeViewModel::class.java)
        return ChatViewModel()
    }


}