package river.chat.lib_core.view.main


import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import org.koin.android.ext.android.getKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.scope.Scope
import river.chat.lib_core.utils.other.getViewModel
import river.chat.lib_core.view.ktx.bind
import river.chat.lib_core.BR

/**
 * @Description: Databinding + ViewModel BaseFragment
 * @Author: loongwind
 * @CreateDate： 2020/8/13 7:29 AM
 *
 */
open abstract class BaseBindingViewModelFragment<BINDING : ViewDataBinding, VM : BaseViewModel> :
    BaseBindingFragment<BINDING>() {

    private var mActivityProvider: ViewModelProvider? = null
    private var mFragmentProvider: ViewModelProvider? = null

    val viewModel: VM by lazy {
        createViewModel()
    }

    override fun initDataBinding(binding: BINDING) {
        //RDF 默认自动绑定 vm。具体业务实现中在实际的视图 xml 文件中声明当前视图的 ViewModel 为
        // vm 即可自动进行绑定。
        binding.setVariable(BR.vm, viewModel)
    }

    /**
     *
     * @description 初始化 ViewModel 并自动进行绑定
     * @param
     * @return VM
     *
     */
    @OptIn(KoinInternalApi::class)
    abstract fun createViewModel(): VM


    /**
     *
     * @description 是否保持 ViewModel。默认创建与当前 Fragment 生命周期绑定的 ViewModel。
     * 重写此方法返回 true，则创建与当前 Fragment 宿主 Activity 生命周期绑定的 ViewModel，与当前
     * Activity 绑定的其他 Fragment 可共享该 ViewMoel
     * @return true：保持 ViewModel 生命周期与宿主 Activity 同步，false：保持 ViewModel 与当前
     * Fragment 生命周期同步
     *
     */
    open fun isShareViewModel(): Boolean {
        return false
    }


    /**
     * 获取作用域为 fragment 生命周期的viewModel
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