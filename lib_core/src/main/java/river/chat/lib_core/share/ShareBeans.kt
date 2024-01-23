package river.chat.lib_core.share

import com.umeng.socialize.bean.SHARE_MEDIA
import river.chat.lib_umeng.common.RIVER_SHARE_MEDIA


data class SharePlatformBean(
    var name: String = "",
    var icon: Int = 0,
    var platform: RIVER_SHARE_MEDIA? = null
)
