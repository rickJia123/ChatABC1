package river.chat.lib_core.view.main.dialog


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import river.chat.lib_core.R
import river.chat.lib_core.utils.other.getBindingType


open abstract class BaseBindingDialogFragment<BINDING : ViewDataBinding> : DialogFragment() {

    var mActivity: AppCompatActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mActivity = context as AppCompatActivity
        //创建 ViewDataBinding 实例
        val binding = createDataBinding(inflater, container)
        //绑定当前 Fragment 生命周期
        binding.lifecycleOwner = this

        // 初始化数据绑定
        initDataBinding(binding)
        //返回布局 View 对象
        return binding.root
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


    @SuppressLint("WrongConstant")
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog == null || dialog.window == null) {
            return
        }
        val window = dialog.window
        val lp = window!!.attributes
        lp.width = onSetDialogWidth()
        lp.height = onSetDialogHeight()
        lp.gravity = onSetDialogGravity()
        lp.dimAmount = onSetDialogDimAmount()
        lp.verticalMargin = onBottomMargin()
        lp.flags = lp.flags or onWindowsFlag()
        window.attributes = lp
        if (onShowDialogWindowAnimations()) {
            window.setWindowAnimations(R.style.DialogStyle_Common)
        }
        window.setBackgroundDrawableResource(river.chat.lib_resource.R.color.transparent)
    }

    /**
     * 设置弹框宽度
     * 默认 MATCH_PARENT
     */
    protected open fun onSetDialogWidth(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    /**
     * 设置弹框高度
     * 默认 MATCH_PARENT(考虑大部分弹框底部都带有分享面板)
     */
    protected open fun onSetDialogHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    /**
     * 设置弹框位置
     * 默认底部
     */
    protected open fun onSetDialogGravity(): Int {
        return Gravity.CENTER
    }

    /**
     * 设置弹框背景色
     * 默认0.8
     */
    protected open fun onSetDialogDimAmount(): Float {
        return 0.8f
    }

    /**
     * 是否显示弹框弹出动画
     * 默认有动画
     */
    protected open fun onShowDialogWindowAnimations(): Boolean {
        return true
    }

    /**
     * 设置弹窗距离底部距离
     * 默认0
     */
    protected open fun onBottomMargin(): Float {
        return 0f
    }

    /**
     * 设置 windows flag
     * 默认0
     */
    protected open fun onWindowsFlag(): Int {
        return 0
    }

}