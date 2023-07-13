package river.chat.lib_umeng

import android.app.Activity
import android.widget.Toast
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import com.umeng.socialize.shareboard.ShareBoardConfig
import river.chat.lib_umeng.common.RiverShareContent
import java.lang.ref.WeakReference

/**
 * Created by beiyongChao on 2023/6/6
 * Description: 分享管理类
 */
object ShareManager {
    private var mShareListener: UMShareListener? = null
    private var mShareAction: ShareAction? = null
    private var mShareMedia: SHARE_MEDIA? = null

    private var mShareContent = RiverShareContent()

    //软引用
    private var mActivityRef: WeakReference<Activity>? = null


    fun update(shareContent: RiverShareContent, shareMedia: SHARE_MEDIA) :ShareManager{
        mShareContent = shareContent
        mShareMedia = shareMedia
        return this
    }

    fun shareText() {
        ShareAction(getActivity()).withText(mShareContent.mText)
            .beginShare()
    }


    fun shareImageLocal() {
        val imageLocal = UMImage(getActivity(), mShareContent.mBitmap)
        imageLocal.setThumb(UMImage(getActivity(), R.mipmap.logo_trans))
        ShareAction(getActivity()).withMedia(imageLocal)
            .beginShare()
    }

    fun shareUrl() {
        val web = UMWeb(mShareContent.mUrl)
        web.title = mShareContent.mTitle
        web.setThumb(UMImage(getActivity(), R.mipmap.logo_trans))
        web.description = mShareContent.mDescription
        ShareAction(getActivity()).withMedia(web)
            .beginShare()
    }


    fun shareTextAndImage(activity: Activity) {
        mActivityRef = WeakReference(activity)
        val imageLocal = UMImage(getActivity(), mShareContent.mBitmap)
        imageLocal.setThumb(UMImage(getActivity(), R.mipmap.logo_trans))
        ShareAction(getActivity()).withText(mShareContent.mText)
            .beginShare()
    }


    fun launchShareBoard(activity: Activity): ShareManager {
        val config = ShareBoardConfig()
        mActivityRef = WeakReference(activity)
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER)
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR)
        buildShareAction(activity)
        mShareAction?.open(config)
        return this
    }


    fun ShareAction.beginShare() {
        this.setPlatform(mShareMedia)
            .setCallback(mShareListener).share()
    }


    private fun buildShareAction(activity: Activity) {
        /*增加自定义按钮的分享面板*/mShareAction = ShareAction(activity).setDisplayList(
            SHARE_MEDIA.WEIXIN,
            SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.SINA,
            SHARE_MEDIA.FLICKR,
            SHARE_MEDIA.FOURSQUARE,
        )
            .addButton("复制答案文本", "复制答案文本", "umeng_socialize_copy", "umeng_socialize_copy")
            .addButton("复制链接", "复制链接", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
            .setShareboardclickCallback { snsPlatform, share_media ->
                if (snsPlatform.mShowWord == "复制文本") {
                    Toast.makeText(
                        activity,
                        "复制文本按钮",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (snsPlatform.mShowWord == "复制链接") {
                    Toast.makeText(
                        activity,
                        "复制链接按钮",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val web = UMWeb("http://www.baidu.com")
                    web.title = "来自分享面板标题"
                    web.description = "来自分享面板内容"
                    web.setThumb(UMImage(activity, R.mipmap.logo_trans))
                    ShareAction(activity).withMedia(web)
                        .setPlatform(share_media)
                        .setCallback(mShareListener)
                        .share()
                }
            }
    }

    fun buildShareListener(activity: Activity) {
        mShareListener = object : UMShareListener {
            override fun onResult(p0: SHARE_MEDIA?) {
                Toast.makeText(activity, " 分享成功", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel(p0: SHARE_MEDIA?) {
                Toast.makeText(activity, " 分享取消", Toast.LENGTH_SHORT).show()
            }

            override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                Toast.makeText(activity, " 分享失败", Toast.LENGTH_SHORT).show()
            }

            override fun onStart(p0: SHARE_MEDIA?) {
                Toast.makeText(activity, " 分享开始", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getActivity() = mActivityRef?.get()
}

