package io.dushu.sharekit.model

import java.io.Serializable

/**
 * @author zhangchao
 * @package io.dushu.sharekit.model
 * @fileName XhsShareContentModel
 * @date 2023/12/25
 * @emial 804827440@qq.com
 * @describe 小红书分享内容model
 * @company 樊登读书
 */
class XhsShareContentModel : Serializable {

    /**
     * 笔记图片本地地址
     */
    var imgLocalPath: String? = null

    /**
     * 笔记主标题
     */
    var mainTitle: String? = null

    /**
     * 笔记正文
     */
    var content: String? = null


}