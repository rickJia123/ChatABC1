package river.chat.businese_common.ui.view.dialog

data class SimpleDialogConfig(
    var title: String = "",
    var des: String = "",
    var leftButtonStr: String = "",
    var rightButtonStr: String = "",
    var leftClick: () -> Unit = {},
    var rightClick: () -> Unit = {}
)