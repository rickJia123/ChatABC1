package river.chat.lib_core.view.main.dialog


import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import river.chat.lib_core.BR
import river.chat.lib_core.view.ktx.bind
import river.chat.lib_core.view.main.BaseViewModel


open abstract class BaseBindingDialogViewModelFragment<BINDING : ViewDataBinding, VM : BaseViewModel> :
    BaseBindingDialogFragment<BINDING>() {

    protected var mBinding :BINDING ?=null
    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null

    //创建 ViewModel 变量并延迟初始化
    val viewModel: VM by lazy {
        createViewModel()
    }

    override fun initDataBinding(binding: BINDING) {
        //绑定 viewModel
        //绑定变量为 vm。
        // 具体业务实现中在实际的布局 xml 文件中声明当前视图的 ViewModel 变量为 vm 即可自动进行绑定。

        binding.setVariable(BR.vm, viewModel)
        mBinding=binding
    }


    /**
     * @description 初始化 ViewModel 并自动进行绑定
     * @return VM ViewModel 实例对象
     */
    abstract fun createViewModel(): VM


    /**
     * 获取作用域为 activity 生命周期的viewModel
     */
    protected open fun <T : ViewModel> getFragmentScopeViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!!.get(modelClass)
    }



    /**
     * 获取作用域为 activity 生命周期的viewModel
     */
    protected open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivity?.let {
                mActivityProvider = ViewModelProvider(it)
            }
        }
        return mActivityProvider!![modelClass]

    }


}