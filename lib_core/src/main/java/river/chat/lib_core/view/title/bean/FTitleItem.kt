package river.chat.lib_core.view.title.bean

/**
 * @author Scott
 * Web导航栏按钮实体
 */
data class FTitleItem(
    //标题类型[text]文字按钮[img]图片按钮
    val viewType: String? = null,
    //默认图片或者文字颜色
    var from: String? = null,
    //显示文本
    val title: String? = null,
    //渐变后图片或者文字颜色
    var to: String? = null,
    //点击时的H5方法
    val method: String? = null,
    //角标，如果值大于0，显示数字角标；值等于0，显示红色小点；小于0不显示角
    val badge: String? = null
)