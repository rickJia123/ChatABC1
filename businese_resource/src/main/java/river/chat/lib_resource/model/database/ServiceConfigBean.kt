package river.chat.lib_resource.model.database

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 接口获取的配置信息(数据库)
 */
@Entity
data class ServiceConfigBean(
    @Id
    var id: Long = 0,

    //下载h5页面
    var appDownUrl: String = "",

    //app下载链接
    var appDownLink: String = "",
    var appPolicyUrl: String = "",
    var appPrivacyPolicy: String = "",
    var appShareBg: String = "",
    var value: String? = "",

    //更新时间
    var updateTime: Long = 0,


    var closeModulesStr: String? = "",

    /**
     * 活动标题
     */
    var activityTitle: String? = "",

    /**
     * 活动结束时间
     */
    var activityEndTime: Long = 0,

    ) : java.io.Serializable {
    companion object {
    }
}

/**
 * 接口获取的配置信息
 */
data class ServiceConfigServiceBean(

    var id: Long = 0,

    //下载h5页面
    var appDownUrl: String = "",

    //app下载链接
    var appDownLink: String = "",
    var appPolicyUrl: String = "",
    var appPrivacyPolicy: String = "",
    var appShareBg: String = "",
    var value: String? = "",

    //更新时间
    var updateTime: Long = 0,

    //关闭的模块
    var closeModules: MutableList<String> = mutableListOf(),

    /**
     * 活动标题
     */
    var activityTitle: String? = "",

    /**
     * 活动结束时间
     */
    var activityEndTime: Long = 0,

    ) : java.io.Serializable {
}