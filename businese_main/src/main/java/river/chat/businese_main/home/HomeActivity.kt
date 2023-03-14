package river.chat.businese_main.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import river.chat.businese_common.router.HomeRouterConstants
import river.chat.business_main.R
import river.chat.business_main.databinding.ActivityHomeBinding
import river.chat.lib_core.view.main.BaseBindingViewModelActivity

/**
 * Created by beiyongChao on 2023/3/8
 * Description:
 */
@Route(path = HomeRouterConstants.HOME_MAIN)
class HomeActivity : BaseBindingViewModelActivity<ActivityHomeBinding, HomeViewModel>() {


    companion object {
        fun launch(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, HomeActivity::class.java))
        }
    }

    override fun initDataBinding(binding: ActivityHomeBinding) {
        super.initDataBinding(binding)
        binding.toolBar.setTitle("ChatEvery")


    }


    override fun createViewModel() = HomeViewModel()


}