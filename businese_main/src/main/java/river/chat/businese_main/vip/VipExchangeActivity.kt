package river.chat.businese_main.vip

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.report.TrackerEventName
import river.chat.businese_common.report.TrackerKeys
import river.chat.businese_common.report.VIPTracker
import river.chat.businese_common.router.jump2Main
import river.chat.businese_common.ui.view.dialog.SimpleDialog
import river.chat.businese_common.ui.view.dialog.SimpleDialogConfig
import river.chat.businese_common.utils.onLoad
import river.chat.business_main.R
import river.chat.business_main.databinding.ActivityVipExchangeBinding
import river.chat.lib_core.router.plugin.module.HomeRouterConstants
import river.chat.lib_core.tracker.TrackNode
import river.chat.lib_core.tracker.postTrack
import river.chat.lib_core.utils.exts.getDrawable
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.longan.topActivity
import river.chat.lib_core.view.main.activity.BaseActivity
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity


/**
 * Created by beiyongChao on 2023/8/31
 * Description:vip 兑换
 */
@Route(path = HomeRouterConstants.VIP_EXCHANGE)
class VipExchangeActivity :
    BaseBindingViewModelActivity<ActivityVipExchangeBinding, VipViewModel>() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initDataBinding(binding: ActivityVipExchangeBinding) {
        onLoad()
        super.initDataBinding(binding)
        binding.toolBar.setTitle("会员免费兑换")
        mBinding.btExchange.singleClick {
            it.postTrack(
                TrackerEventName.CLICK_VIP,
                TrackNode(VIPTracker.KEY_EXCHANGE_CONTENT to "兑换码: ${mBinding.inputView.text}")
            )
            viewModel.request.exchangeVip(mBinding.inputView.text.toString())
        }
        loadDesData()
        observerMsg()
        binding.inputView.requestFocus()
    }

    /**
     * 监听接口消息请求
     */
    private fun observerMsg() {
        viewModel.request.exChangeResult.observe(this) {
            if (it.isSuccess) {
                SimpleDialog.builder(topActivity as BaseActivity).config(
                    SimpleDialogConfig()
                        .apply {
                            title = "兑换成功"
                            des = "恭喜您，已经为您兑换了VIP会员\n快去体验ChatGpt吧！"
                            leftButtonStr = "取消"
                            rightButtonStr = "去体验"
                            rightClick = {
                                jump2Main()
                            }
                        }).show()
            }
        }
        mBinding.inputView.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
               var inputStr = s.toString()
                if (inputStr.isEmpty()) {
                    mBinding.btExchange.background= R.drawable.back_input_corner.getDrawable()
                }
                else{
                    mBinding.btExchange.background= R.drawable.back_input_corner.getDrawable()
                }

                mBinding.btExchange.isEnabled = inputStr.isNotEmpty()
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
        })
    }

    override fun onEvent(eventId: Int) {
        when (eventId) {

        }
    }

    private fun loadDesData() {
        //rick todo
        viewModel.vipExchangeTips.addAll(mutableListOf<String>().apply {
            "直接联系管理员(企业微信)支付获取".also { add(it) }
            "小红书/公众号关注官方账号后，截图发给企微管理员,获取7天vip兑换码".also { add(it) }
            "兑换成功后，会员权益立即生效".also { add(it) }
        })
    }


    override fun createViewModel() = VipViewModel()


}