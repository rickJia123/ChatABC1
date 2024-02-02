package river.chat.businese_main.picture

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.dataBase.AiPictureBox
import river.chat.businese_common.utils.exts.getBitmap
import river.chat.businese_common.utils.onLoad
import river.chat.businese_main.share.SharePictureDialog
import river.chat.business_main.databinding.ActivityPictureBinding
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.saveBitmapToMediaStore
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity
import river.chat.lib_resource.model.database.AiPictureBean

/**
 * Created by beiyongChao on 2024/3/7
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_PICTURE_PREVIEW)
class PicturePreviewActivity :
    BaseBindingViewModelActivity<ActivityPictureBinding, PictureViewModel>() {


    @Autowired(name = HomeRouterConstants.Params.PICTURE_PRELOAD_ID)
    @JvmField
    var mPreviewId: String = ""

    var mPicture: AiPictureBean? = null


    override fun initDataBinding(binding: ActivityPictureBinding) {
        super.initDataBinding(binding)
        onLoad()
        mPicture = AiPictureBox.getAnswerById(mPreviewId)
        mPicture.getBitmap()?.let {
            binding.ivPicture.setImageBitmap(it)
        }
        initClick()
        observeRequest()
    }

    private fun observeRequest() {


    }

    private fun initClick() {
        mBinding.ivPicture.singleClick {
            finish()
        }
        mBinding.ivDownload.singleClick {
            mPicture.getBitmap()?.let {
                saveBitmapToMediaStore(applicationContext, it)
            }
        }
        mBinding.ivShare.singleClick {
            mPicture.getBitmap()?.let {
                SharePictureDialog.builder(topActivity)
                    .show(mPreviewId ?: "", mPicture?.question ?: "")
            }
        }
    }


    override fun createViewModel() = PictureViewModel()

}