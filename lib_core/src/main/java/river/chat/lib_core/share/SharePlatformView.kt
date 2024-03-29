package river.chat.lib_core.share

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.umeng.socialize.bean.SHARE_MEDIA
import river.chat.lib_core.BR
import river.chat.lib_core.R
import river.chat.lib_core.databinding.ViewSharePlatformBinding
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_umeng.common.RIVER_SHARE_MEDIA

/**
 * Created by beiyongChao on 2023/3/15
 * Description:
 */
class SharePlatformView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    private var mViewModel: ShareViewModel? = null
    var mOnPlatformClick: ((SharePlatformBean) -> Unit)? = null
        set(value) {
            field = value
            mViewModel?.onPlatformClick = value
        }

    var mOnCancelClick: (() -> Unit)? = null


    private val viewBinding: ViewSharePlatformBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.view_share_platform,
        this,
        true
    )


    init {
        viewBinding.recycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mViewModel = ShareViewModel()
        viewBinding.setVariable(BR.vm, mViewModel)
        initPlatforms()
        viewBinding.tvCancel.singleClick {
            mOnCancelClick?.invoke()
        }
    }

    private fun initPlatforms() {
        var platforms = mutableListOf<SharePlatformBean>()
        platforms.add(
            SharePlatformBean(
                "微信",
                R.drawable.umeng_socialize_wechat,
                RIVER_SHARE_MEDIA.WEIXIN
            )
        )
        platforms.add(
            SharePlatformBean(
                "小红书",
                R.mipmap.share_platform_xhs,
                RIVER_SHARE_MEDIA.XIAOHONGSHU
            )
        )
        platforms.add(
            SharePlatformBean(
                "朋友圈",
                R.drawable.umeng_socialize_wxcircle,
                RIVER_SHARE_MEDIA.WEIXIN_CIRCLE
            )
        )
        platforms.add(
            SharePlatformBean(
                "复制回答",
                R.drawable.copy,
                RIVER_SHARE_MEDIA.MORE
            )
        )
        mViewModel?.data?.addAll(platforms)
    }

    /**
     * 设置分享渠道
     */
    public fun setPictureChannels() {
        var platforms = mutableListOf<SharePlatformBean>()
        platforms.add(
            SharePlatformBean(
                "微信",
                R.drawable.umeng_socialize_wechat,
                RIVER_SHARE_MEDIA.WEIXIN
            )
        )
        platforms.add(
            SharePlatformBean(
                "小红书",
                R.mipmap.share_platform_xhs,
                RIVER_SHARE_MEDIA.XIAOHONGSHU
            )
        )
        platforms.add(
            SharePlatformBean(
                "朋友圈",
                R.drawable.umeng_socialize_wxcircle,
                RIVER_SHARE_MEDIA.WEIXIN_CIRCLE
            )
        )
        platforms.add(
            SharePlatformBean(
                "下载",
                R.drawable.download_black,
                RIVER_SHARE_MEDIA.MORE
            )
        )
        mViewModel?.data?.clear()
        mViewModel?.data?.addAll(platforms)
    }


}