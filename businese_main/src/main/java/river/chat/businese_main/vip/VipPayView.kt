package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewVipPayBinding
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2023/8/19
 * Description:
 */
class VipPayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    var mBinding: ViewVipPayBinding
    var mActivity: AppCompatActivity? = null
    private var mOnTabClick: (Int) -> Unit = {}

    private var payList: MutableList<VipTabBean> = mutableListOf()

    companion object {
    }

    init {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_vip_pay,
            this,
            true
        )
        initViews()
        initClick()
    }


    private fun initViews() {


    }

    fun initClick() {

    }


    private fun updateData(payList: MutableList<VipTabBean>) {
        this.payList = payList

    }


}







