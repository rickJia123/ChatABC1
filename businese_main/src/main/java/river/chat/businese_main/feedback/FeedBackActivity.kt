package river.chat.businese_main.feedback

import com.alibaba.android.arouter.facade.annotation.Route
import org.koin.android.ext.android.inject
import river.chat.business_main.databinding.ActivityFeedbackBinding
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_FEEDBACK)
class FeedBackActivity :
    BaseBindingViewModelActivity<ActivityFeedbackBinding, FeedBackViewModel>() {


    private val homePlugin: HomePlugin by inject()
    private val userPlugin: UserPlugin by inject()


    override fun initDataBinding(binding: ActivityFeedbackBinding) {
        super.initDataBinding(binding)
        binding.toolBar.setTitle("意见反馈")
        initClick(binding)
        observeRequest()
    }

    private fun observeRequest() {
        viewModel.request.feedBackResult.observe(this) {
            if (it.isSuccess) {
                toast("反馈成功")
                finish()
            }
        }

    }

    private fun initClick(binding: ActivityFeedbackBinding) {
        binding.btConfirm.singleClick {
            if (binding.etFeedback.text.toString().isEmpty()) {
                toast("请输入反馈内容")
                return@singleClick
            }
            viewModel.request.confirmFeedback(binding.etFeedback.text.toString(), binding.etPhone.text.toString())
        }
    }


    override fun createViewModel() = FeedBackViewModel()

}