package river.chat.chatevery.splash

import com.umeng.commonsdk.UMConfigure
import river.chat.businese_common.router.jump2Main
import river.chat.chatevery.databinding.ActivitySplashBinding
import river.chat.lib_core.privacy.PrivacyManager
import river.chat.lib_core.view.main.activity.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */

class SplashActivity : BaseBindingViewModelActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun initDataBinding(binding: ActivitySplashBinding) {
        super.initDataBinding(binding)
        PrivacyManager.tryShowPrivacyDialog(this) {
            UMConfigure.submitPolicyGrantResult(applicationContext, it)
//            if (it) {
//                binding.tvTitle1.postDelayed({
//                    go2Main()
//                }, 0)
//            } else {
//                finish()
//            }
            binding.tvTitle1.postDelayed({
                go2Main()
            }, 0)
//            initVideo()
            showGif()
        }
    }

    private fun showGif() {
//        mBinding.ivSplash.load(R.drawable.splash_1)
    }

    private fun initVideo() {
        //总播放时间
//        var totalPLayTime = 20
//        var uri = Uri.parse("http://vjs.zencdn.net/v/oceans.mp4")
//        var videoView = mBinding.videoView
//        videoView.setVideoURI(uri)
//        videoView.requestFocus()
//
//        var totalDuration = videoView.duration
//        if (totalDuration < 0) {
//            totalDuration = 0
//        }
//        var startTime = Random.nextInt(8) - totalPLayTime
//        if (startTime < 0) {
//            startTime = 0
//        }
//        videoView.seekTo(startTime * 1000)
//
//        CutdownUtils.countDownCoroutines(
//            totalPLayTime,
//            scope = lifecycleScope,
//            onTick = {},
//            onFinish = {
//                videoView.stopPlayback()
//                go2Main()
//            }, delayTime = 1000
//        )
//
//        // 在播放完毕被回调
//        videoView.setOnCompletionListener {
//            go2Main()
//        }
//
//        videoView.setOnErrorListener { mp, what, extra ->
//            go2Main()
//            return@setOnErrorListener true
//        }
    }

    private fun go2Main() {
        jump2Main()
        finish()
    }


    override fun createViewModel() = SplashViewModel()


}