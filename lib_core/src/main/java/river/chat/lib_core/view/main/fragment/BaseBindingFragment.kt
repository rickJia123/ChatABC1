package river.chat.lib_core.view.main.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import river.chat.lib_core.R
import river.chat.lib_core.utils.other.getBindingType

/**
 * @Description:
 * @Author: loongwind
 * @CreateDate： 2020/8/4 11:46 PM
 *
 */
abstract class BaseBindingFragment<BINDING : ViewDataBinding> : BaseFragment() {

    protected var mActivity: AppCompatActivity? = null
    lateinit var mBinding: BINDING
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mActivity = context as AppCompatActivity
        //创建 ViewDataBinding 实例
        mBinding = createDataBinding(inflater, container)
        //绑定当前 Fragment 生命周期
        mBinding.lifecycleOwner = this

        // 初始化数据绑定
        initDataBinding(mBinding)
        //返回布局 View 对象
        return mBinding.root
    }

    /**
     * 根据泛型 BINDING 创建 ViewDataBinding 实例
     */
    private fun createDataBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING {
        return getBindingType(javaClass)// 获取泛型类型
            ?.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ) // 反射获取 inflate 方法
            ?.invoke(null, inflater, container, false) as BINDING // 通过反射调用 inflate 方法
    }

    /**
     * 初始化数据绑定
     * 子类实现该方法通过 binding 绑定数据
     */
    abstract fun initDataBinding(binding: BINDING)


}