package river.chat.businese_main.home

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import river.chat.businese_main.message.MessageCenter
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewHomeTabBinding
import river.chat.lib_core.utils.exts.getColor
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2023/6/29
 * Description:
 */
class HomeTabView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    var mBinding: ViewHomeTabBinding
    var mActivity: AppCompatActivity? = null
    var mCurrentPosition = 0
    var mTabList = mutableListOf<AppCompatTextView>()
    private var mOnTabClick: (Int) -> Unit = {}

    companion object {

    }

    init {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_home_tab,
            this,
            true
        )
        initViews()
        initClick()

    }

    private fun initViews() {
        mTabList.clear()
        mTabList.add(mBinding.tvChat)
        mTabList.add(mBinding.tvSquare)
        mTabList.add(mBinding.tvMine)
    }

    fun initClick() {
        mTabList.forEachIndexed { index, tv ->
            tv.singleClick {
                onTabClick(index, tv)
            }
        }
    }

    /**
     * 选项卡点击时
     */
    private fun onTabClick(position: Int, textView: AppCompatTextView) {
        //选中项是否是当前所在项
        if (position != mCurrentPosition) {
            mCurrentPosition = position
            var tabBgX = textView.x
            mBinding.clBg.animate().x(tabBgX).setDuration(100).start()
            updateStatus(position)
            mOnTabClick.invoke(position)
        }
        MessageCenter.postHideSoftWindow()
    }


    fun setTabClickListener(onTabClick: (Int) -> Unit) {
        mOnTabClick = onTabClick
    }

    fun setTabPosition(position: Int, hasAnim: Boolean = false) {
        mTabList.forEachIndexed { index, textView ->
            if (index == position) {
                mCurrentPosition = position
                var tabBgX: Float = textView.x
                mBinding.clBg.animate().x(tabBgX).setDuration(if (hasAnim) 100 else 0).start()
                updateStatus(position)
                textView.setTextColor(R.color.white.getColor())
                textView.textSize = 18f
                textView.typeface = Typeface.DEFAULT_BOLD
            } else {
                textView.setTextColor(R.color.iconDisableColor.getColor())
                textView.textSize = 16f
                textView.typeface = Typeface.DEFAULT
            }
        }
    }

    private fun updateStatus(position: Int) {
        if (position == 0 || position == 1) {
//            mBinding.clRoot.setBackgroundColor(R.color.front_1_BgColor.getColor())
            mBinding.clRoot.setTopRadius(0f)
            mBinding.clRoot.setBottomRadius(10f)
        } else {
//            mBinding.clRoot.setBackgroundColor(0)
            mBinding.clRoot.setRadius(10f)
        }

    }


}




