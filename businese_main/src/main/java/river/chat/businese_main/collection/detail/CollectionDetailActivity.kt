package river.chat.businese_main.collection.detail

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.collection.CollectionViewModel
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageHelper
import river.chat.businese_main.share.ShareDialog
import river.chat.business_main.R
import river.chat.business_main.databinding.ActivityCollectionDetailBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity
import river.chat.lib_resource.model.database.CardMsgBean
import river.chat.lib_resource.model.database.MessageBean

/**
 * Created by beiyongChao on 2023/12/17
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_COLLECTION_DETAIL)
class CollectionDetailActivity :
    BaseBindingViewModelActivity<ActivityCollectionDetailBinding, CollectionViewModel>() {


    @JvmField
    @Autowired(name = HomeRouterConstants.Params.KEY_QUESTION_ID)
    var mQuestionId = 0L

    var mCurrentMsg: CardMsgBean? = null

    override fun initDataBinding(binding: ActivityCollectionDetailBinding) {
        onLoad()
        super.initDataBinding(binding)
        binding.toolBar.setTitle("收藏详情")
        updateMsg()
        initEventListener()
        initClick()
    }


    private fun initClick() {
        mBinding.ivCollection.singleClick {
            MessageCenter.toggleCollectionStatus(mCurrentMsg?.questionMsg)
        }
        mBinding.ivShare.singleClick {
            ShareDialog.builder(topActivity).show(mCurrentMsg?.questionMsg, mCurrentMsg?.answerMsg)
        }
    }

    private fun updateCollectionStatus(questionMsg: MessageBean? = null) {
        mBinding.ivCollection.setImageResource(
            if (questionMsg?.isCollected == true) {
                R.drawable.collection_sel
            } else {
                R.drawable.collection_nosel
            }
        )
    }

    private fun initEventListener() {
        EventCenter.registerReceiveEvent(lifecycleScope) {
            when (it.action) {
                CommonEvent.COLLECTION_TOGGLE -> {
                    updateMsg()
                }

            }
        }
    }


    private fun updateMsg() {
        mCurrentMsg = MessageHelper.buildCardMsgList(
            MessageCenter.getHistoryMsg()
        ).first { it.questionMsg.id == mQuestionId }

        mBinding.tvQuestion.text = mCurrentMsg?.questionMsg?.content
        mBinding.tvAnswer.text = mCurrentMsg?.answerMsg?.content
        updateCollectionStatus(mCurrentMsg?.questionMsg)
    }


    override fun createViewModel() = getActivityScopeViewModel(CollectionViewModel::class.java)

}