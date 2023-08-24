package river.chat.businese_main.vip

import androidx.databinding.ObservableArrayList
import river.chat.businese_main.api.MainCommonRequest
import river.chat.businese_main.chat.ChatRequest
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_resource.model.vip.VipRightsBeans

class VipViewModel : BaseViewModel() {


    val request = MainCommonRequest(this)

    val normalRightList = ObservableArrayList<String>()
    val vipRightList = ObservableArrayList<String>()


    /*
    权益表(后续可以从接口获取)
     */
    fun initRightsList() {
        normalRightList.clear()

        normalRightList.addAll(mutableListOf<String>().apply {
            add("ChatGPT专业回复")
            add("每天5次")

        })

        vipRightList.clear()
        vipRightList.addAll(mutableListOf<String>().apply {
            //rick todo
            add("ChatGpt专业回复")
            add("不限次数")
            add("可写论文")
            add("季卡赠送一个ChatGPT账户")
        })
    }


}

