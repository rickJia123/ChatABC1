package river.chat.businese_main.utils

import river.chat.lib_core.utils.log.LogUtil

fun String.logChat() =
  LogUtil.i("river Chat:$this")