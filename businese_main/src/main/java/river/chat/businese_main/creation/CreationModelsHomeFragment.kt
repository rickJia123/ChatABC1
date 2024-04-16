package river.chat.businese_main.creation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.creation.itemfragment.CreationModelsItemFragment
import river.chat.business_main.databinding.FragmentCreationModelsBinding
import river.chat.lib_core.view.ktx.newInstance
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment


/**
 * 首页-创作-模版选择
 */
class CreationModelsHomeFragment :
    BaseBindingViewModelFragment<FragmentCreationModelsBinding, CreationViewModel>() {


    private val mFragments: MutableList<CreationModelsItemFragment> = mutableListOf()

    /**
     * viewPager adapter
     */
    private val mFragmentAdapter: FragmentStateAdapter by lazy {
        object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return mFragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return mFragments[position]
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreationModelsHomeFragment().newInstance()
    }

    override fun initDataBinding(binding: FragmentCreationModelsBinding) {
        onLoad()
        super.initDataBinding(binding)
        viewModel.mainCommonRequest.getModelList()
        initClick()
        observeRequest()
    }

    private fun observeRequest() {
        viewModel.mainCommonRequest.modelList.observe(this) {
            it.data?.let {
                viewModel.mModels = it
                updateData()

            }
        }
    }


    private fun updateData() {
        mBinding.tabLayout.removeAllTabs()
        mFragments.clear()
        for (model in viewModel.mModels) {
            val tab = mBinding.tabLayout.newTab()
            tab.text = model.tabName
            mBinding.tabLayout.addTab(tab)
            CreationModelsItemFragment.newInstance(model.tabName)?.let {
                mFragments.add(
                    it
                )
            }
        }
        //设置adapter
        mBinding.viewpager.adapter = mFragmentAdapter



        var mediator =   TabLayoutMediator( mBinding.tabLayout,  mBinding.viewpager) { tab, position ->
            tab.text = viewModel.mModels[position].tabName
        }
        mBinding.viewpager.setCurrentItem(2, false)
        mediator.attach()


    }


    private fun initClick() {

    }


    override fun onPause() {
        super.onPause()
    }


    override fun createViewModel() = getActivityScopeViewModel(CreationViewModel::class.java)


}