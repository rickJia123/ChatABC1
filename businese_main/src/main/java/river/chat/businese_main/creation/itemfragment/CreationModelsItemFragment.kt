package river.chat.businese_main.creation.itemfragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.collection.CollectionAdapter
import river.chat.businese_main.creation.CreationBeans
import river.chat.businese_main.creation.CreationItemsBeans
import river.chat.businese_main.creation.CreationViewModel
import river.chat.businese_main.message.MessageCenter
import river.chat.business_main.databinding.FragmentCreationModelsItemBinding
import river.chat.lib_core.view.ktx.newInstance
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment

/**
 * 首页-创作-模版选择
 */
class CreationModelsItemFragment :
    BaseBindingViewModelFragment<FragmentCreationModelsItemBinding, CreationViewModel>() {

    private var mModel: CreationBeans? = null
    private var mTabName = ""

    private val mAdapterLeft by lazy {
        CreationItemAdapter().apply {

        }
    }
    private val mAdapterRight by lazy {
        CreationItemAdapter().apply {

        }
    }


    companion object {
        @JvmStatic
        fun newInstance(name: String) = CreationModelsItemFragment().newInstance(name)
    }

    override fun initDataBinding(binding: FragmentCreationModelsItemBinding) {
        onLoad()
        mModel = viewModel.mModels.findLast { it.tabName == mTabName }
        super.initDataBinding(binding)
        initClick()
        initRecycleView()
        observeRequest()
        update()
    }

    private fun update() {

        //左边奇数，右边偶数
        mAdapterLeft.submitList(mModel?.templates?.filterIndexed { index, _ ->
            index % 2 == 0
        })
        mAdapterRight.submitList(mModel?.templates?.filterIndexed { index, _ ->
            index % 2 == 1
        })
    }

    private fun observeRequest() {
        viewModel.mainCommonRequest.modelList.observe(this) {

        }
    }

    override fun initParams(params: Array<Any?>?) {
        super.initParams(params)
        mTabName = params?.getOrNull(0) as? String ?: ""
    }


    private fun initClick() {

    }


    private fun initRecycleView() {
        mBinding.recycleViewLeft.adapter = mAdapterLeft
        mBinding.recycleViewLeft.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
            }
        mBinding.recycleViewLeft.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                MessageCenter.postHideSoftWindow()
            }
        })

        mBinding.recycleViewRight.adapter = mAdapterRight
        mBinding.recycleViewRight.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
            }
        mBinding.recycleViewRight.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    mBinding.recycleViewLeft.scrollBy(dx, dy)
                }
            }
        })
        mBinding.recycleViewLeft.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    mBinding.recycleViewRight.scrollBy(dx, dy)
                }

            }
        })
    }


    override fun createViewModel() = getActivityScopeViewModel(CreationViewModel::class.java)


}