package river.chat.businese_main.chat

import androidx.lifecycle.lifecycleScope
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageHelper
import river.chat.business_main.databinding.FragmentChatBinding
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseBindingViewModelFragment

class ChatFragment :
    BaseBindingViewModelFragment<FragmentChatBinding, ChatViewModel>() {

    override fun initDataBinding(binding: FragmentChatBinding) {
        super.initDataBinding(binding)

        MessageCenter.registerReceiveMsg(lifecycleScope) { msg ->
            viewModel.data.add(msg.msg)
            msg.msg.toString().log()
            binding.recycleView.smoothScrollToPosition(10000)
        }
        MessageCenter.postReceiveMsg(MessageHelper.buildDefaultMsg())
    }

    override fun createViewModel() = ChatViewModel()

}