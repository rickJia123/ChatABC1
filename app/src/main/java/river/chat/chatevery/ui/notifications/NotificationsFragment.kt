package river.chat.chatevery.ui.notifications

import river.chat.businese_common.dataBase.UserBox
import river.chat.chatevery.databinding.FragmentNotificationsBinding
import river.chat.lib_core.storage.database.model.User
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.main.BaseBindingViewModelFragment

class NotificationsFragment :
    BaseBindingViewModelFragment<FragmentNotificationsBinding, NotificationsViewModel>() {

    override fun createViewModel() = NotificationsViewModel()


    var defaultId = 1
    override fun initDataBinding(binding: FragmentNotificationsBinding) {
        super.initDataBinding(binding)
        binding.tvAdd.singleClick {
            UserBox.add(createUser())
        }
        binding.tvDelete.singleClick {
            UserBox.remove(defaultId.toLong())
        }
        binding.tvDeleteAll.singleClick {
            UserBox.removeAll()
        }
        binding.tvUpdate.singleClick {
            UserBox.add(User().apply {
                id = defaultId.toLong()
                name = "rick 更新"
            })
        }
        binding.tvQuery.singleClick {
            var msg = ""
            UserBox.all.forEach {
                msg += it.toString()
            }
            binding.tvMsg.text = UserBox.all.size.toString() + "  信息：" + msg
        }
    }

    private fun createUser() = User().apply {
        id = (UserBox.all.size + 1).toLong()
        name = "rick" + id
    }

}