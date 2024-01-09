package river.chat.businese_main.collection

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.chat.ChatAdapter
import river.chat.businese_main.chat.ChatFragment
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageHelper
import river.chat.business_main.databinding.FragmentCollectionBinding
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment
import river.chat.lib_resource.model.CardMsgBean

class CollectionFragment :
    BaseBindingViewModelFragment<FragmentCollectionBinding, CollectionViewModel>() {

    private var mCollectionVm: CollectionViewModel? = null

    private var mMsgList = mutableListOf<CardMsgBean>()
    private val mAdapter by lazy {
        CollectionAdapter().apply {
            onClickItem = {

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChatFragment()
    }

    override fun initDataBinding(binding: FragmentCollectionBinding) {
        onLoad()
        super.initDataBinding(binding)
        initMsgService()
        initRecycleView()
    }


    /**
     * 初始化消息服务
     */
    private fun initMsgService() {
        //判断当前列表是否已经初始化过
        var hasDefaultMsg = false
        mMsgList.forEach {
            if (it.answerMsg.id == MessageHelper.mDefaultMsgId) {
                hasDefaultMsg = true
                return@forEach
            }
        }
        if (!hasDefaultMsg) {
            MessageCenter.checkMsgStatus()
            mMsgList.addAll(
                MessageHelper.buildCardMsgList(
                    MessageCenter.getHistoryMsg()
                )
            )
            mAdapter.submitList(mMsgList.filter { it.questionMsg.isCollected == true })
        }
    }


    private fun initRecycleView() {
        mBinding.recycleView.adapter = mAdapter
        mBinding.recycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
            }
        mBinding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                MessageCenter.postHideSoftWindow()
            }
        })
        mBinding.recycleView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                mBinding.recycleView.scrollBy(0, 1980 * 1000)
            } else {

            }
        }
    }


    override fun createViewModel(): CollectionViewModel {
        mCollectionVm = getActivityScopeViewModel(CollectionViewModel::class.java)
        return CollectionViewModel()
    }


}