package river.chat.chatevery.ui.dashboard

import river.chat.chatevery.databinding.FragmentDashboardBinding
import river.chat.chatevery.databinding.ItemTestBinding
import river.chat.lib_core.view.main.BaseBindingViewModelFragment

class DashboardFragment :
    BaseBindingViewModelFragment<FragmentDashboardBinding, DashboardViewModel>() {


    override fun initDataBinding(binding: FragmentDashboardBinding) {
        super.initDataBinding(binding)

        viewModel.loadOrderList()
    }

    override fun createViewModel() = DashboardViewModel()

}