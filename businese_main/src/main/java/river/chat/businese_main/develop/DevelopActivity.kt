package river.chat.businese_main.develop

import com.alibaba.android.arouter.facade.annotation.Route
import com.tencent.bugly.crashreport.CrashReport
import river.chat.businese_common.utils.onLoad
import river.chat.business_main.databinding.ActivityDevelopBinding
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2024/3/7
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_DEVELOP)
class DevelopActivity :
    BaseBindingViewModelActivity<ActivityDevelopBinding, DevelopModel>() {


    override fun initDataBinding(binding: ActivityDevelopBinding) {
        super.initDataBinding(binding)
        onLoad()

        initClick()
        observeRequest()
    }


    private fun observeRequest() {


    }

    private fun initClick() {
        mBinding.title.singleClick {
            CrashReport.testJavaCrash()
        }
    }


    override fun createViewModel() = DevelopModel()

}