package river.chat.businese_main.picture

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.dataBase.AiPictureBox
import river.chat.businese_common.ui.recycleview.CustomItemDecoration
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.constants.MainConstants
import river.chat.businese_main.message.MessageHelper
import river.chat.businese_main.message.MessageViewModel
import river.chat.businese_main.vip.VipManager
import river.chat.business_main.databinding.FragmentPictureBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_core.utils.exts.dp2px
import river.chat.lib_core.utils.exts.safeToInt
import river.chat.lib_core.utils.exts.safeToLong
import river.chat.lib_core.utils.exts.safeToString
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.toastSystem
import river.chat.lib_core.view.common.ToastTipDialog
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment
import river.chat.lib_resource.model.database.AiPictureBean
import river.chat.lib_resource.model.database.MessageStatus

class PictureFragment :
    BaseBindingViewModelFragment<FragmentPictureBinding, MessageViewModel>() {

    private val mAdapter by lazy {
        PictureAdapter(activity).apply {

        }
    }

    //是否可以发送消息
    private var mIsCanSend = true

    private var mPicMsgList = mutableListOf<AiPictureBean>()
    private var inputMethodManager: InputMethodManager? = null
    private var mAnswerPre = "answerId:"

    companion object {
        @JvmStatic
        fun newInstance() = PictureFragment()
    }

    override fun initDataBinding(binding: FragmentPictureBinding) {
        onLoad()
        super.initDataBinding(binding)

        mPicMsgList.addAll(AiPictureBox.getMsgsOnCard().apply {
            add(0, AiPictureBean().apply {
                this.type = AiPictureBean.TYPE_AI
            })
        })
        mAdapter.submitList(mPicMsgList) {
            mAdapter.notifyDataSetChanged()
            mBinding.recycleView.post {
                scroll2Bottom()
            }

        }
        initEventListener()
        observeRequest()
        initClick()
        initRecycleView()
        inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    private fun observeRequest() {
        viewModel.request.pictureResult.observe(this) {
            mBinding.inputView.mSendEnable = true
            if (it.isSuccess) {
                it.data?.let {
                    onMsgUpdate(it.apply {
                        this.type = AiPictureBean.TYPE_ANSWER
                        this.status = MessageStatus.COMPLETE
                        this.id = it.id
                    })
                }
            } else {
                onMsgUpdate(AiPictureBean().apply {
                    this.type = AiPictureBean.TYPE_ANSWER
                    this.status = MessageStatus.FAIL_COMMON
                    this.failMsg = it.errorMsg
                })
            }
        }

        mBinding.inputView.onSendListener = {
            if (mBinding.inputView.mSendEnable) {
                tryPostMsg(it)
            } else {
                "请等待当前回答结束".toastSystem()
            }

        }
    }

    private fun scroll2Bottom() {
        mBinding.recycleView.scrollToPosition(mPicMsgList.size - 1)
    }

    private fun tryPostMsg(content: String) {
        var hasPermission = VipManager.checkRightsWithBusiness {
            when (it) {
                MainConstants.RIGHTS_MSG_TIP -> {
                    onMsgUpdate(AiPictureBean().apply {
                        this.type = AiPictureBean.TYPE_TIP
                        this.status = MessageStatus.FAIL_LIMIT
                    })
                }

                MainConstants.RIGHTS_JUMP_OPEN -> {
                    VipManager.jump2VipPage()

                }
            }
        }
        if (hasPermission) {
            var id = MessageHelper.createMsgId()
            var picMsg = AiPictureBean().apply {
                this.content = content
                this.status = MessageStatus.COMPLETE
                this.type = AiPictureBean.TYPE_QUESTION
                this.id = id.safeToString()
            }
            onMsgUpdate(picMsg)

            //空消息
            onMsgUpdate(AiPictureBean().apply {
                this.type = AiPictureBean.TYPE_ANSWER
                this.status = MessageStatus.LOADING
                this.id = id.safeToString()
            })
            mBinding.inputView.mSendEnable = false
            viewModel.request.requestPicture(content, id.safeToString())
        }
    }

    private fun onMsgUpdate(picMsg: AiPictureBean) {
        if (picMsg.type == AiPictureBean.TYPE_QUESTION && picMsg.content.isNullOrEmpty()) {
            return
        }

        LogUtil.i("rick PictureFragment onMsgUpdate begin:" + picMsg.id + "::" + picMsg.content + ":::")

        //删除该问题的load回答占位
        mPicMsgList.removeIf { it.type == AiPictureBean.TYPE_ANSWER && (it.id.safeToLong() > 0) && it.id == picMsg.id }

        AiPictureBox.saveMsg(picMsg.apply {
            this.pictureId = MessageHelper.createMsgId()
            this.time = System.currentTimeMillis()
        })
        mPicMsgList.add(picMsg)

        LogUtil.i(
            "rick PictureFragment onMsgUpdate:" + picMsg.id + ":::" + picMsg.type + "::" + GsonKits.toJson(
                picMsg
            )
        )
        mAdapter.submitList(mPicMsgList) {
            mAdapter.notifyDataSetChanged()
            scroll2Bottom()
        }

    }


    private fun initClick() {

    }


    private fun initRecycleView() {
        mBinding.recycleView.adapter = mAdapter
        mBinding.recycleView.addItemDecoration(
            CustomItemDecoration(5f.dp2px())
        )
        mBinding.recycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
//                this.stackFromEnd = true
            }

    }

    private fun initEventListener() {
        EventCenter.registerReceiveEvent((context as AppCompatActivity).lifecycleScope) {
            when (it.action) {
                CommonEvent.SEND_MSG_SUCCESS -> {

                }

                CommonEvent.HIDE_SOFT_WINDOW -> {

                }

                CommonEvent.COLLECTION_TOGGLE -> {

                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        hideSoftInput()
    }

    fun hideSoftInput() {
        inputMethodManager?.hideSoftInputFromWindow(mBinding.inputView.windowToken, 0)
    }

    override fun createViewModel(): MessageViewModel {
        return MessageViewModel()
    }


}