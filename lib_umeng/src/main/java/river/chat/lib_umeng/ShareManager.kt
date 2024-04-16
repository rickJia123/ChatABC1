package river.chat.lib_umeng

import android.app.Activity
import android.widget.Toast
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import com.umeng.socialize.shareboard.ShareBoardConfig
import com.xingin.xhssharesdk.callback.XhsShareCallback
import com.xingin.xhssharesdk.core.XhsShareSdk
import com.xingin.xhssharesdk.model.sharedata.XhsImageInfo
import com.xingin.xhssharesdk.model.sharedata.XhsImageResourceBean
import com.xingin.xhssharesdk.model.sharedata.XhsNote
import io.dushu.sharekit.model.XhsShareContentModel
import river.chat.lib_umeng.common.RIVER_SHARE_MEDIA
import river.chat.lib_umeng.common.RiverShareContent
import river.chat.lib_umeng.common.save2Local
import java.io.File
import java.lang.ref.WeakReference

/**
 * Created by beiyongChao on 2023/6/6
 * Description: 分享管理类
 */
object ShareManager {
    private var mShareListener: UMShareListener? = null
    private var mShareAction: ShareAction? = null
    private var mShareMedia: RIVER_SHARE_MEDIA? = null

    private var mShareContent = RiverShareContent()

    //软引用
    private var mActivityRef: WeakReference<Activity>? = null


    fun update(shareContent: RiverShareContent, shareMedia: RIVER_SHARE_MEDIA): ShareManager {
        mShareContent = shareContent
        mShareMedia = shareMedia
        return this
    }


    fun shareImageLocal(activity: Activity) {
        mActivityRef = WeakReference(activity)
        val imageLocal = UMImage(getActivity(), mShareContent.mBitmap)
        imageLocal.setThumb(UMImage(getActivity(), mShareContent.mBitmap))
        ShareAction(getActivity()).withMedia(imageLocal)
            .beginShare(activity)
    }

    fun shareUrl(activity: Activity) {
        mActivityRef = WeakReference(activity)
        val web = UMWeb(mShareContent.mUrl)
        web.title = mShareContent.mTitle
        web.setThumb(UMImage(getActivity(), R.mipmap.logo_trans))
        web.description = mShareContent.mDescription
        ShareAction(getActivity()).withMedia(web)
            .beginShare(activity)
    }


    fun shareTextAndImage(activity: Activity) {
        mActivityRef = WeakReference(activity)
        val imageLocal = UMImage(getActivity(), mShareContent.mBitmap)
        imageLocal.setThumb(UMImage(getActivity(), R.mipmap.logo_trans))
        ShareAction(getActivity()).withText(mShareContent.mText)
            .beginShare(activity)
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


    fun ShareAction.beginShare(activity: Activity) {
        mShareMedia?.let {
            if (it == RIVER_SHARE_MEDIA.XIAOHONGSHU) {
                shareXhs(activity, XhsShareContentModel().apply {
                    this.content = mShareContent.mDescription
                    this.imgLocalPath = mShareContent.mBitmap?.save2Local(activity)
                })
            } else {
                this.setPlatform(
                    convertShareModel(it)
                )
                    .setCallback(
                        mShareListener ?: mActivityRef?.get()?.let { buildShareListener(it) })
                    .share()
            }

        }
    }


    private fun buildShareAction(activity: Activity) {
        /*增加自定义按钮的分享面板*/mShareAction = ShareAction(activity).setDisplayList(
            SHARE_MEDIA.WEIXIN,
            SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.SINA,
            SHARE_MEDIA.FLICKR,
            SHARE_MEDIA.FOURSQUARE,
        )
            .addButton(
                "复制答案文本",
                "复制答案文本",
                "umeng_socialize_copy",
                "umeng_socialize_copy"
            )
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

    fun buildShareListener(activity: Activity): UMShareListener {
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

            }
        }
        return mShareListener!!
    }

    /**
     * 分享到小红书 (图文笔记)
     */
    private fun shareXhs(activity: Activity?, shareModel: XhsShareContentModel) {

        var shareTitle = "我在GPTEvery 与AI对话"


        /**
         * 把xhsTagList里面的标签加上#拼接成字符串tagStr
         */
        var tagStr = "最懂你的AI工具  " + "   \n#这个回答有意思" + " #我在GPTEvery与AI对话"

        var tagPrefixTag = "ChatGpt"

        var shareContent =
            "#$tagPrefixTag   #$tagStr"

        val xhsNote = XhsNote().apply {
            this.title = shareTitle
            this.content = shareContent
            imageInfo = XhsImageInfo(listOf(XhsImageResourceBean(File(shareModel.imgLocalPath))))
//            imageInfo = XhsImageInfo(listOf(XhsImageResourceBean.fromUrl("https://t7.baidu.com/it/u=4036010509,3445021118&fm=193&f=GIF")))
        }
        XhsShareSdk.shareNote(activity, xhsNote)
        XhsShareSdk.setShareCallback(object : XhsShareCallback {
            override fun onSuccess(sessionId: String?) {
                XhsShareSdk.setShareCallback(null)
            }

            override fun onError(p0: String, p1: Int, p2: String, p3: Throwable?) {

            }


            // 1.1.0 版本后提供的新错误回调，内含新的错误码
            override fun onError2(
                sessionId: String,
                newErrorCode: Int,
                oldErrorCode: Int,
                errorMessage: String,
                throwable: Throwable?
            ) {
                XhsShareSdk.setShareCallback(null)
            }

        })
    }


    private fun getActivity() = mActivityRef?.get()


    private fun convertShareModel(shareMedia: RIVER_SHARE_MEDIA): SHARE_MEDIA {
        return when (shareMedia) {
            RIVER_SHARE_MEDIA.WEIXIN -> SHARE_MEDIA.WEIXIN
            RIVER_SHARE_MEDIA.WEIXIN_CIRCLE -> SHARE_MEDIA.WEIXIN_CIRCLE
            RIVER_SHARE_MEDIA.SINA -> SHARE_MEDIA.SINA
            RIVER_SHARE_MEDIA.MORE -> SHARE_MEDIA.MORE
            RIVER_SHARE_MEDIA.FOURSQUARE -> SHARE_MEDIA.FOURSQUARE
            else -> SHARE_MEDIA.WEIXIN
        }
    }
}

