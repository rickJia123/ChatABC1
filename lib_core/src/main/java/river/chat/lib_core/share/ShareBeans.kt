package river.chat.lib_core.share

import com.umeng.socialize.bean.SHARE_MEDIA


data class SharePlatformBean(
    var name: String = "",
    var icon: Int = 0,
    var platform: SHARE_MEDIA? = null
)
