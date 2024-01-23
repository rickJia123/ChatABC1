package river.chat.businese_main.picture

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.utils.BusinessUtils.base64ToBitmap
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.message.MessageViewModel
import river.chat.business_main.databinding.FragmentPictureBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.view.main.fragment.BaseBindingViewModelFragment
import river.chat.lib_resource.model.database.AiPictureBean

class PictureFragment :
    BaseBindingViewModelFragment<FragmentPictureBinding, MessageViewModel>() {


    companion object {
        @JvmStatic
        fun newInstance() = PictureFragment()
    }

    override fun initDataBinding(binding: FragmentPictureBinding) {
        onLoad()
        super.initDataBinding(binding)

        initEventListener()
        observeRequest()
        initClick()
    }

    private fun observeRequest() {
        viewModel.request.pictureResult.observe(this) {

            if (it.isSuccess) {
                drawBitmap(it.data)
            }
        }

    }

    private fun drawBitmap(picture: AiPictureBean?) {
        var data = picture?.content
        if (data?.isNotEmpty() == true) {
        var bitmap = base64ToBitmap(data ?: "")
        LogUtil.i("PictureFragment drawBitmap data:" + ":::" + (data == null) + ":::" + (bitmap == null))
        LogUtil.i("PictureFragment drawBitmap bitmap:" + ":::" + bitmap)
        mBinding.ivPicture.setImageBitmap(bitmap)
    }
    }

    private fun initClick() {
        mBinding.btConfirm.singleClick {
//            viewModel.request.requestPicture(mBinding.inputView.getInputText())

            //rick todo
            viewModel.request.requestPicture("画一个小图")
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


    override fun createViewModel(): MessageViewModel {
        return MessageViewModel()
    }


}