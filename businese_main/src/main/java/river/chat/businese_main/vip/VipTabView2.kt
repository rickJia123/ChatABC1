package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.VIPTracker
import river.chat.businese_common.utils.onReport
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewVipTab2Binding
import river.chat.lib_core.view.base.LifecycleView
import river.chat.lib_resource.model.VipSkuBean

/**
 * Created by beiyongChao on 2023/6/29
 * Description:
 */
class VipTabView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    var mBinding: ViewVipTab2Binding
    var mActivity: AppCompatActivity? = null
    var mCurrentPosition = -1
    var mTabList = mutableListOf<Pair<View, ConstraintLayout>>()

    private var mOnTabClick: (VipSkuBean) -> Unit = { tabBean ->

    }
    private val mAdapter by lazy {
        VipTabAdapter(selPosition = mDefaultTabPosition).apply {
            this.onClickItem = {
                notifyDataSetChanged()
                onReport(
                    TrackerEventName.CLICK_VIP,
                    VIPTracker.KEY_SKU_CHOOSE to it.skuName
                )
                mOnTabClick.invoke(it)

            }
        }
    }

    private var payList: MutableList<VipSkuBean> = mutableListOf()

    //默认选中按钮
    private var mDefaultTabPosition = 1


    init {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_vip_tab2,
            this,
            true
        )
        initViews()
        initRecycleView()
    }


    fun setTabClickListener(onTabClick: (VipSkuBean) -> Unit) {
        mOnTabClick = onTabClick
    }


    fun updateData(payList: MutableList<VipSkuBean>) {
        this.payList = payList
        mAdapter.submitList(payList)

    }


    private fun initViews() {


    }

    private fun initRecycleView() {
        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.layoutManager =
            GridLayoutManager(context, 3).apply {
            }
    }


}
