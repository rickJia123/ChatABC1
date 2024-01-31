package river.chat.businese_main.picture

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import org.koin.android.ext.android.inject
import river.chat.businese_common.dataBase.AiPictureBox
import river.chat.businese_main.utils.getBitmap
import river.chat.business_main.databinding.ActivityPictureBinding
import river.chat.lib_core.router.plugin.module.HomePlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.common.TimeUtils
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.saveBitmapToMediaStore
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

    var mPicture : AiPictureBean ?= null
    private val homePlugin: HomePlugin by inject()
    private val userPlugin: UserPlugin by inject()


    override fun initDataBinding(binding: ActivityPictureBinding) {
        super.initDataBinding(binding)
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
                val fileName = "AI绘图" + TimeUtils.montageSystemTime() + ".jpg"
                saveBitmapToMediaStore(applicationContext, it,fileName)
            }

        }
    }


    override fun createViewModel() = PictureViewModel()

}