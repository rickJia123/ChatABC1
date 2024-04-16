package river.chat.businese_main.home

import android.os.Bundle
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
import river.chat.businese_common.constants.ModelEvent
import river.chat.businese_common.update.AppUpdateManager
import river.chat.businese_main.chat.ChatFragment
import river.chat.businese_main.constants.ChatConstants
import river.chat.businese_main.creation.HomeCreationFragment
import river.chat.businese_main.message.MessageCenter
import river.chat.businese_main.mine.SettingsFragment
import river.chat.businese_main.picture.PictureFragment
import river.chat.businese_main.vip.ActivitiesDialog
import river.chat.business_main.databinding.ActivityHomeBinding
import river.chat.lib_core.event.BaseActionEvent
import river.chat.lib_core.event.EventCenter
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.common.SoftKeyboardStateHelper
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
        ServiceConfigManager.loadAllConfig(
            onBaseConfigLoaded = { ActivitiesDialog().showActivityDialog(this) },
            onAppDateConfigLoaded = { AppUpdateManager.showUpdateAppDialog(this) }
        )
        userPlugin.refreshInfo()
        loadPreService()
        observerPreServices()
        LogUtil.i("viewModel ChatFragment:" + viewModel)
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

                CommonEvent.LOGIN_CHANGE -> {
                    if (!userPlugin.isLogin()) {
                        mBinding.toolBar.update()
                    }
                }
            }
        }
    }


    private fun initFragment() {
        val fragmentChat = ChatFragment.newInstance(ChatConstants.CHAT_MODE_NORMAl)
        mFragments.add(fragmentChat as BaseFragment)
        val fragmentPicture = PictureFragment.newInstance()
        mFragments.add(fragmentPicture)
        val fragmentCreation = HomeCreationFragment.newInstance()
        mFragments.add(fragmentCreation)
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
//                if (position == 1) {
//                    mBinding.toolBar.visibility = View.INVISIBLE
//                } else {
//                    mBinding.toolBar.visibility = View.VISIBLE
//                }
                mBinding.toolBar.onSelection(position)
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
        addSoftKeyboardHelper(mBinding.root)
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


    override fun createViewModel() = getActivityScopeViewModel(HomeViewModel::class.java)


    /**
     * 请求需要预加载的接口
     */
    fun loadPreService() {
        viewModel.vipRequest.getPaySku()
        viewModel.vipRequest.getVipRights()
    }

    /**
     * 监听接口消息请求
     */
    private fun observerPreServices() {
        viewModel.vipRequest.paySkuResult.observe(this) {
            if (it.isSuccess) {

            }
        }
        viewModel.vipRequest.vipRightResult.observe(this) {
            if (it.isSuccess) {
                it.data?.let {

                }
            }
        }
    }

    /**
     * 添加软键盘监听
     */
    private fun addSoftKeyboardHelper(view: View) {
        val helper = SoftKeyboardStateHelper(view)
        helper.addSoftKeyboardStateListener(object :
            SoftKeyboardStateHelper.SoftKeyboardStateListener {
            override fun onSoftKeyboardOpened(keyboardHeightInPx: Int) {
                EventCenter.postEvent(BaseActionEvent().apply {
                    action = ModelEvent.EVENT_SOFT_OPEN
                })
            }

            override fun onSoftKeyboardClosed() {
//                viewModel.postEvent(ModelEvent.EVENT_SOFT_CLOSE)
                EventCenter.postEvent(BaseActionEvent().apply {
                    action = ModelEvent.EVENT_SOFT_CLOSE
                })
            }

        })
    }
}