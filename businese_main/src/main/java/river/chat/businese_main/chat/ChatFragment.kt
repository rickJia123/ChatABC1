package river.chat.businese_main.chat

import androidx.lifecycle.lifecycleScope
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import river.chat.businese_common.constants.CommonVmEvents
import river.chat.businese_common.router.UserPlugin
import river.chat.businese_main.home.HomeViewModel
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageHelper
import river.chat.business_main.databinding.FragmentChatBinding
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment

class ChatFragment :
    BaseBindingViewModelFragment<FragmentChatBinding, ChatViewModel>() {

    private var mHomeActivityVm: HomeViewModel? = null

    private val userPlugin: UserPlugin by inject()

    override fun initDataBinding(binding: FragmentChatBinding) {
        super.initDataBinding(binding)

        MessageCenter.registerReceiveMsg(lifecycleScope) { msg ->
            viewModel.data.add(msg.msg)
            msg.msg.toString().log()
            binding.recycleView.smoothScrollToPosition(10000)
        }
        MessageCenter.postReceiveMsg(MessageHelper.buildDefaultMsg())
    }


    override fun createViewModel(): ChatViewModel {
        mHomeActivityVm = getActivityScopeViewModel(HomeViewModel::class.java)
        return ChatViewModel()
    }



}