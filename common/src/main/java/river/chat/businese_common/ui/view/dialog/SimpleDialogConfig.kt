package river.chat.businese_common.ui.view.dialog

data class SimpleDialogConfig(
    val title: String?,
    val des: String?,
    val leftButtonStr: String?,
    val rightButtonStr: String?,
    val leftClick: () -> Unit,
    val rightClick: () -> Unit
)