package river.chat.businese_main.vip

import androidx.databinding.ObservableArrayList
import river.chat.lib_core.beans.VipRightsBeans
import river.chat.lib_core.view.main.BaseViewModel

class VipOpenViewModel : BaseViewModel() {

    val data = ObservableArrayList<VipRightsBeans>()


    /*
    权益表(后续可以从接口获取)
     */
    fun initRightsList() {

        var monthQuarterRight = mutableListOf(1, 2)
        var quarterRight = mutableListOf(1, 2)
        var rightsList = mutableListOf<VipRightsBeans>().apply {
            add(VipRightsBeans("会员期内不限次数回复", monthQuarterRight))
            add(VipRightsBeans("每周抽取好礼", monthQuarterRight))
            add(VipRightsBeans("免费赠送一个ChatGPT账户", quarterRight))
        }
        data.addAll(rightsList)
    }
}