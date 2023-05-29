package river.chat.businese_main.message

import river.chat.businese_main.chat.ChatRequest
import river.chat.lib_core.view.main.BaseViewModel

class MessageViewModel : BaseViewModel() {

    val request = ChatRequest(this)

}