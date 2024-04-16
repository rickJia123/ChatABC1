package river.chat.businese_main.vip

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import river.chat.businese_main.manager.MainCommonHelper
import river.chat.business_main.R
import river.chat.business_main.databinding.ViewVipActivityBinding
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.utils.common.TimeUtils
import river.chat.lib_core.utils.exts.singleClick
import river.chat.lib_core.utils.exts.view.buildSpannableString
import river.chat.lib_core.utils.other.CutdownUtils
import river.chat.lib_core.view.base.LifecycleView

/**
 * Created by beiyongChao on 2024/2/29
 * Description:支付活动view
 */
class VipActivityView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LifecycleView(context, attrs, defStyleAttr) {

    var mBinding: ViewVipActivityBinding
    var mActivity: AppCompatActivity? = null
    var finishText = "0小时" + "0分钟" + "0秒"

    val DELAY_TIME = 500L
    private var mCurrentRemainTime = 0L

    private var text: String = ""
    private var position: Int = 0
    var onEnabled: (Boolean) -> Unit = {
    }

    companion object {
    }

    init {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_vip_activity,
            this,
            true
        )


    }

    override fun onCreate() {
        super.onCreate()
        initViews()
        initClick()
    }


    private fun initViews() {
    }

    fun initClick() {

    }


    fun update(activity: AppCompatActivity) {
        var endTime = ServiceConfigBox.getConfig().activityEndTime
        var activityTitle = ServiceConfigBox.getConfig().activityTitle
        text = activityTitle ?: ""
        if (!MainCommonHelper.checkNeedPayActivity()) {
            mBinding.root.visibility = GONE
            onEnabled.invoke(false)
            return
        }
        onEnabled.invoke(true)

        if (endTime > 0L|| endTime > System.currentTimeMillis()) {
            startCutDown(endTime, activity)
            mBinding.tvRemainTime.visibility = VISIBLE
        }

        mBinding.tvTitle.text = activityTitle
        mHandler.sendEmptyMessageDelayed(0, DELAY_TIME)
    }


    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val span = SpannableString(text).apply {
                setSpan(
                    RelativeSizeSpan(1.5F),
                    position,
                    position + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                var colorSpan = ForegroundColorSpan(
                    android.graphics.Color.parseColor("#FF3B30")
                )

                setSpan(
                    colorSpan, position,
                    position + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            mBinding.tvTitle.text = span
            position++
            if (position >= text.length) {
                position = 0
            }
            this.sendEmptyMessageDelayed(0, DELAY_TIME)
        }
    }


    /**
     * 倒计时逻辑
     */
    private fun startCutDown(endTime: Long, activity: AppCompatActivity) {
        mCurrentRemainTime = (endTime - System.currentTimeMillis()) / 1000
        var cutDownTime = mCurrentRemainTime
        var mCutDownJob = CutdownUtils.countDownCoroutines(
            cutDownTime.toInt(),
            scope = activity.lifecycleScope,
            onFinish = {
                mBinding.tvRemainTime.buildSpannableString {
                    addText("还剩") {
                        setColor("#666666")
                    }
                    addText(finishText) {
                        setColor("#FA601F")
                    }
                }
            },
            onTick = {
                mCurrentRemainTime--
                var timeText = TimeUtils.getDHHMMSS(mCurrentRemainTime * 1000)
                if (mCurrentRemainTime < 0) {
                    mCurrentRemainTime = 0
                    timeText = finishText
                }
                mBinding.tvRemainTime.buildSpannableString {
                    addText("还剩") {
                        setColor("#666666")
                    }
                    addText(timeText) {
                        setColor("#FA601F")
                    }
                }
            })
    }

}
