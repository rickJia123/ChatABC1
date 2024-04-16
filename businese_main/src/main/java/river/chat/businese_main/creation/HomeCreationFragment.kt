package river.chat.businese_main.creation

import android.view.View
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.chat.ChatFragment
import river.chat.business_main.databinding.FragmentHomeCreationBinding
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment

/**
 * 首页-创作
 */
class HomeCreationFragment :
    BaseBindingViewModelFragment<FragmentHomeCreationBinding, CreationViewModel>() {

    private var mChatFragment: ChatFragment? = null
    private var mCreationModelsHomeFragment: CreationModelsHomeFragment? = null


    companion object {
        @JvmStatic
        fun newInstance() = HomeCreationFragment()
    }

    override fun initDataBinding(binding: FragmentHomeCreationBinding) {
        onLoad()
        super.initDataBinding(binding)
        mChatFragment =
            childFragmentManager.findFragmentByTag("fragmentCreationChat") as? ChatFragment
        mCreationModelsHomeFragment =
            childFragmentManager.findFragmentByTag("fragmentCreationModelList") as? CreationModelsHomeFragment
        initClick()

        toggle(false)


        var test=CreationHelper.getSelectedModels()
        test
    }



    //切换到创作页面
    private fun toggle(isChat: Boolean) {
        if (isChat) {
            mBinding.fragmentCreationModelList.visibility = View.GONE
            mBinding.fragmentCreationChat.visibility = View.VISIBLE
        } else {
            mBinding.fragmentCreationModelList.visibility = View.VISIBLE
            mBinding.fragmentCreationChat.visibility = View.GONE
        }

    }


    private fun initClick() {

    }


    override fun onPause() {
        super.onPause()
    }


    override fun createViewModel(): CreationViewModel {
        return CreationViewModel()
    }


}