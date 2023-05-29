package river.chat.lib_core.view.main.activity

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import river.chat.lib_core.BR
import river.chat.lib_core.view.ktx.bind
import river.chat.lib_core.view.main.BaseViewModel
import river.chat.lib_core.view.main.OnEventListener

/**
 * @Description: Databinding + ViewModel BaseActivity
 * @Author: loongwind
 * @CreateDate： 2020/8/13 7:09 AM
 *
 */
open abstract class BaseBindingViewModelActivity<BINDING : ViewDataBinding, VM : BaseViewModel> :
    BaseBindingActivity<BINDING>(), OnEventListener {

    private var mActivityProvider: ViewModelProvider? = null

    //创建 ViewModel 变量并延迟初始化
    val viewModel: VM by lazy {
        createViewModel()
    }

    override fun initDataBinding(binding: BINDING) {
        //绑定 viewModel
        //绑定变量为 vm。
        // 具体业务实现中在实际的布局 xml 文件中声明当前视图的 ViewModel 变量为 vm 即可自动进行绑定。
        viewModel.bind(this)
        binding.setVariable(BR.vm, viewModel)
    }


    /**
     * @description 初始化 ViewModel 并自动进行绑定
     * @return VM ViewModel 实例对象
     */
    abstract fun createViewModel(): VM


    /**
     * 获取作用域为 activity 生命周期的viewModel
     */
    fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider!![modelClass]
    }

    override fun onEvent(eventId: Int) {

    }
}
