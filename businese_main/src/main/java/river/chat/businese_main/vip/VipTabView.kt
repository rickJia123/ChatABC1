package river.chat.businese_main.vip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import river.chat.common.R
import river.chat.common.databinding.ViewVipTabBinding
import river.chat.lib_core.utils.exts.getDrawable
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2023/6/29
 * Description:
 */
class VipTabView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    var mBinding: ViewVipTabBinding
    var mActivity: AppCompatActivity? = null
    var mCurrentPosition = -1
    var mTabList = mutableListOf<Pair<View, ConstraintLayout>>()
    private var mOnTabClick: (Int) -> Unit = {}

    private var payList: MutableList<VipTabBean> = mutableListOf()

    companion object {
    }

    init {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_vip_tab,
            this,
            true
        )
        initViews()
        initClick()
        updateData(getPayList())
        onTabClick(0)
    }


    private fun initViews() {
        mTabList.clear()
        mTabList.add(Pair(mBinding.includeMonth.viewClick, mBinding.includeMonth.clRoot))
        mTabList.add(Pair(mBinding.includeQuarter.viewClick, mBinding.includeQuarter.clRoot))


    }

    fun initClick() {
        mTabList.forEachIndexed { index, tv ->
            tv.first.singleClick {
                onTabClick(index)
            }
        }
    }

    /**
     * 选项卡点击时
     */
    private fun onTabClick(position: Int) {
        //选中项是否是当前所在项
        if (position != mCurrentPosition) {
            mOnTabClick.invoke(position)
            setTabPosition(position)
        }
    }


    fun setTabClickListener(onTabClick: (Int) -> Unit) {
        mOnTabClick = onTabClick
    }

    /**
     * 设置选中项
     */
    private fun setTabPosition(position: Int) {
        mTabList.forEachIndexed { index, tab ->
            if (index == position) {
                mCurrentPosition = position
                tab.second.background = R.drawable.bg_vip_tab_sel.getDrawable()
            } else {
                tab.second.background = R.drawable.bg_vip_tab_nosel.getDrawable()
            }
        }
    }

    /**
     * 获取支付列表
     */
    private fun getPayList(): MutableList<VipTabBean> {
        return mutableListOf<VipTabBean>().apply {
            add(VipTabBean("1个月", 45f, 1f, false))
            add(VipTabBean("3个月", 99f, 0.73f, false))
        }
    }

    private fun updateData(payList: MutableList<VipTabBean>) {
        this.payList = payList
        mTabList.forEachIndexed { index, tabView ->
            var tvPrice = tabView.second.findViewById<AppCompatTextView>(R.id.tvPrice)
            var tvDuration = tabView.second.findViewById<AppCompatTextView>(R.id.tvDuration)
            var tvMonthPrice = tabView.second.findViewById<AppCompatTextView>(R.id.tvMonthPrice)
            var vipTabBean = payList.get(index)

            setData(vipTabBean, tvDuration, tvPrice, tvMonthPrice)
        }
    }

    private fun setData(
        bean: VipTabBean,
        tvDuration: AppCompatTextView,
        tvPrice: AppCompatTextView,
        tvMothPrice: AppCompatTextView
    ) {
        tvDuration.text = bean.duration
        tvPrice.buildSpannableString {
            addText("￥", method = {
                setColor("#030303")
                setSize(11)
            })
            addText(bean.price.toString(), method = {
                setColor("#030303")
                setSize(16)
            })
        }
    }
}


data class VipTabBean(
    var duration: String = "",
    var price: Float = 0.0f,
    //折扣
    var discount: Float = 0.0f,
    var isSelect: Boolean = false
)




