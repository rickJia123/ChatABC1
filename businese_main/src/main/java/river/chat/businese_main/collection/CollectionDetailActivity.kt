package river.chat.businese_main.collection

import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.utils.onLoad
import river.chat.business_main.databinding.ActivityCollectionBinding
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/12/17
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_COLLECTION_DETAIL)
class CollectionDetailActivity :
    BaseBindingViewModelActivity<ActivityCollectionBinding, CollectionViewModel>() {


    override fun initDataBinding(binding: ActivityCollectionBinding) {
        onLoad()
        super.initDataBinding(binding)
        binding.toolBar.setTitle("收藏")
    }


    override fun createViewModel() = getActivityScopeViewModel(CollectionViewModel::class.java)

}