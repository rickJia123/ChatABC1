package river.chat.businese_main.vip

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewVipTabBinding
import river.chat.lib_core.utils.exts.autoIntOr2Str
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
    private var mOnTabClick: (Int, VipTabBean) -> Unit = { position, tabBean ->

    }

    /**
     * 单月不打折的价格
     */
    private var mSingleMonthPrice = 0f
    private var payList: MutableList<VipTabBean> = mutableListOf()
    private var mDefaultTabPosition = 1

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

    }


    private fun initViews() {
        mTabList.clear()
        mTabList.add(Pair(mBinding.includeMonth.viewClick, mBinding.includeMonth.clRoot))
        mTabList.add(Pair(mBinding.includeQuarter.viewClick, mBinding.includeQuarter.clRoot))
        mTabList.add(Pair(mBinding.includeYear.viewClick, mBinding.includeYear.clRoot))

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
            mCurrentPosition = position
            mOnTabClick.invoke(position, payList.get(position))
            updateData(payList)
        }
    }

    fun update() {
        updateData(getPayList())
        onTabClick(mDefaultTabPosition)
    }

    fun setTabClickListener(onTabClick: (Int, VipTabBean) -> Unit) {
        mOnTabClick = onTabClick
    }


    /**
     * 获取支付列表
     */
    private fun getPayList(): MutableList<VipTabBean> {
        return mutableListOf<VipTabBean>().apply {
            add(VipTabBean(1, "1个月", 45f))
            add(VipTabBean(3, "3个月", 99f))
            add(VipTabBean(12, "1年", 324f))
        }
    }

    private fun updateData(payList: MutableList<VipTabBean>) {
        //获取单月价格
        payList.forEach {
            if (it.duration == 1) {
                mSingleMonthPrice = it.price
            }
        }
        this.payList = payList
        mTabList.forEachIndexed { index, tabView ->
            var vipTabBean = payList.get(index)
            setData(index, vipTabBean, tabView)
            if (index == mCurrentPosition) {
                tabView.second.background = R.drawable.bg_vip_tab_sel.getDrawable()
            } else {
                tabView.second.background = R.drawable.bg_vip_tab_nosel.getDrawable()
            }
        }

    }


    @SuppressLint("SetTextI18n")
    private fun setData(
        index: Int,
        bean: VipTabBean,
        tabView: Pair<View, ConstraintLayout>,
    ) {
        var tvPrice = tabView.second.findViewById<AppCompatTextView>(R.id.tvPrice)
        var tvDuration = tabView.second.findViewById<AppCompatTextView>(R.id.tvDuration)
        var tvMonthPrice = tabView.second.findViewById<AppCompatTextView>(R.id.tvMonthPrice)
        var tvDiscount = tabView.second.findViewById<AppCompatTextView>(R.id.tvDiscount)



        tvDuration.text = bean.durationStr
        var monthPrice = bean.price / bean.duration
        tvMonthPrice.text = "¥" + monthPrice + "元/月"

        var disCount = monthPrice / mSingleMonthPrice
        tvDiscount.text = disCount.autoIntOr2Str() + "折"
        if (disCount >= 1) {
            tvDiscount.visibility = View.GONE
        } else {
            tvDiscount.visibility = View.VISIBLE
        }
        setPrice(tvPrice, bean, if (index != mCurrentPosition) "#030303" else "#FA601F")

    }

    private fun setPrice(tvPrice: AppCompatTextView, bean: VipTabBean, color: String) {
        tvPrice.buildSpannableString {
            addText("￥", method = {
                setColor(color)
                setSize(11)
            })
            addText(bean.price.toString(), method = {
                setColor(color)
                setSize(21)
            })
        }
    }
}


data class VipTabBean(
    var duration: Int = 0,
    var durationStr: String = "",
    var price: Float = 0.0f,


    )




