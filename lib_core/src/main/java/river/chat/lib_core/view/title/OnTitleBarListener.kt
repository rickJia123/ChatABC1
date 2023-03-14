package river.chat.lib_core.view.title

/**
 * author : Scott
 * desc   : 标题栏点击监听接口
 */
interface OnTitleBarListener {
    /**
     * 左项被点击
     */
    fun onLeftClick(titleBar: TitleBar) {}

    /**
     * 标题被点击
     */
    fun onTitleClick(titleBar: TitleBar) {}

    /**
     * 右项被点击
     */
    fun onRightClick(titleBar: TitleBar) {}
}