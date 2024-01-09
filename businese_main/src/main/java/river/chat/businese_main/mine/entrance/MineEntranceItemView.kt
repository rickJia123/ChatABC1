package river.chat.businese_main.mine.entrance

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import river.chat.businese_common.router.jump2Collection
import river.chat.businese_common.router.jump2VipOpen
import river.chat.businese_main.constants.MainConstants
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewMineItemEntraceBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2023/11/14
 * Description: 我的页面入口
 */
class MineEntranceItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {
    var mBinding: ViewMineItemEntraceBinding
    var mType = ""
    var mOnJump: (String) -> Unit = {}


    init {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_mine_item_entrace,
            this,
            true
        )
        initClick()
    }


    fun refreshData(type: String, onJump: (String) -> Unit = {}) {
        var iconRes = 0
        var textStr = ""
        mType = type
        mOnJump = onJump
        when (type) {
            MainConstants.ENTRANCE_TYPE_COLLECTION -> {
                iconRes = R.drawable.favorites
                textStr = "收藏"
            }

        }
        mBinding.ivIcon.setImageResource(iconRes)

        mBinding.tvTitle.text = textStr
    }

    private fun initClick() {
        mBinding.clRoot.singleClick {
            when (mType) {
                MainConstants.ENTRANCE_TYPE_COLLECTION -> {
                    jump2Collection()
                }
            }
        }
    }
}