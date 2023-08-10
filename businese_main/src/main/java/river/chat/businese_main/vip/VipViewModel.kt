package river.chat.businese_main.vip

import androidx.databinding.ObservableArrayList
import river.chat.businese_main.chat.ChatRequest
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.message.MessageHelper
import river.chat.lib_core.beans.VipRightsBeans
import river.chat.lib_core.utils.longan.toast
import river.chat.lib_core.view.main.BaseViewModel

class VipViewModel : BaseViewModel() {


    val request = ChatRequest(this)

    val normalRightList = ObservableArrayList<VipRightsBeans>()
    val vipRightList = ObservableArrayList<VipRightsBeans>()


    /*
    权益表(后续可以从接口获取)
     */
    fun initRightsList() {
        normalRightList.clear()
        normalRightList.addAll(mutableListOf<VipRightsBeans>().apply {
            add(VipRightsBeans("ChatGPT专业回复"))
            add(VipRightsBeans("每天5次"))

        })

        vipRightList.clear()
        vipRightList.addAll(mutableListOf<VipRightsBeans>().apply {
            //rick todo
            add(VipRightsBeans("ChatGpt专业回复"))
            add(VipRightsBeans("不限次数"))
            add(VipRightsBeans("可写论文"))
            add(VipRightsBeans("季卡赠送一个ChatGPT账户"))
        })
    }


}

