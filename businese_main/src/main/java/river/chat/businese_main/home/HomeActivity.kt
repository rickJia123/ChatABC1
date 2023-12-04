package river.chat.businese_main.home

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import com.alibaba.android.arouter.facade.annotation.Route
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import river.chat.businese_common.config.ServiceConfigManager
import river.chat.businese_common.constants.CommonEvent
import river.chat.businese_common.constants.CommonVmEvents
import river.chat.businese_common.update.AppUpdateManager
import river.chat.businese_main.chat.ChatFragment
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.setting.SettingsFragment
import river.chat.businese_main.square.SquareFragment
import river.chat.businese_main.vip.VipManager
import river.chat.business_main.databinding.ActivityHomeBinding
import river.chat.business_main.databinding.FragmentHomeBinding
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity
import river.chat.lib_core.view.main.fragment.BaseFragment


/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_MAIN)
class HomeActivity : BaseBindingViewModelActivity<ActivityHomeBinding, HomeViewModel>() {

    var userPlugin = getPlugin<UserPlugin>()
    private val mFragments = mutableListOf<Fragment>()

    /**
     * viewPager adapter
     */
    private val mFragmentAdapter: FragmentStateAdapter by lazy {
        object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return mFragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return mFragments[position]
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOnHomeActivity()
        //请求配置信息
        ServiceConfigManager.loadAllConfig()
        userPlugin.refreshInfo()

        AppUpdateManager.showUpdateAppDialog(this)
    }

    /**
     * 主Activity 初始化
     */
    private fun initOnHomeActivity() {
        MessageCenter.registerMsgCenter(this)
    }

    private fun initEventListener() {
        EventCenter.registerReceiveEvent(lifecycleScope) {
            when (it.action) {
                CommonEvent.UPDATE_USER -> {
                    mBinding.toolBar.update()
                }
            }
        }
    }


    private fun initFragment() {
        val fragmentChat = ChatFragment.newInstance()
        mFragments.add(fragmentChat as BaseFragment)
//        val fragmentSquare = SquareFragment.newInstance()
//        mFragments.add(fragmentSquare as BaseFragment)
        val fragmentSettings = SettingsFragment.newInstance()
        mFragments.add(fragmentSettings as BaseFragment)

    }

    private fun initView() {
        //设置adapter
        mBinding.viewPager.adapter = mFragmentAdapter
        //设置viewpage的滑动方向
        mBinding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //禁止滑动
        // binding.viewpager.isUserInputEnabled = false
        //设置显示的页面，0：是第一页
        //binding.viewpager.currentItem = 1
        //设置缓存页
        mBinding.viewPager.offscreenPageLimit = mFragments.size
        //设置viewPage2切换效果
        //binding.viewpager.setPageTransformer(TransFormer())

        //一屏多个fragment
//        val recyclerView:RecyclerView = binding.viewpager.getChildAt(0) as RecyclerView
//        val padding = resources.getDimensionPixelOffset(R.dimen.app_icon_size) + resources.getDimensionPixelOffset(R.dimen.app_icon_size)
//        recyclerView.setPadding(padding, 0, padding, 0)
//        recyclerView.clipToPadding = false

        //设置滑动时fragment之间的间距
        // binding.viewpager.setPageTransformer( MarginPageTransformer(100))
        //同时设置多个动画
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(100))
        mBinding.viewPager.setPageTransformer(compositePageTransformer)

        //设置选中事件
        mBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mBinding.viewTabView.setTabPosition(position, true)
                LogUtil.d("lyy", "当前fragment的位置-----: $position")
                if (position == 1) {
                    mBinding.toolBar.visibility = View.INVISIBLE
                } else {
                    mBinding.toolBar.visibility = View.VISIBLE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                //禁止滑动
                mBinding.viewPager.isUserInputEnabled = !(state == SCROLL_STATE_DRAGGING)
            }
        })

        mBinding.viewTabView.setTabClickListener { position ->
            mBinding.viewPager.currentItem = position
        }

        initEventListener()
    }


    override fun initDataBinding(binding: ActivityHomeBinding) {
        super.initDataBinding(binding)
//        binding.toolBar.setTitle("GPTEvery")
//        binding.toolBar.rightClick = {
//            jump2Settings()
//        }
//        binding.toolBar.leftClick = {
//            VipManager.jump2VipPage()
//        }
        initEventListener()
        initFragment()
        initView()
    }


    override fun onEvent(eventId: Int) {
        when (eventId) {
            CommonVmEvents.LOADING -> {
//                LoadingDialog().showDialog(this)
            }
        }
    }


    override fun createViewModel() = HomeViewModel()

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String?) {
        // Do something
    }


}