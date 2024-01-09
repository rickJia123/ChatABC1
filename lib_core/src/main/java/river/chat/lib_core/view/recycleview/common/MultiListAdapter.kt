package river.chat.lib_core.view.recycleview.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by beiyongChao on 2023/1/12
 * Description:
 */
abstract class MultiListAdapter<T> : ListAdapter<T, ItemMultiViewHolder> {
    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)
    constructor(config: AsyncDifferConfig<T>) : super(config)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemMultiViewHolder {
        return ItemMultiViewHolder.create<ViewDataBinding>(
            parent,
            getLayoutByItemViewType(viewType)
        )
    }

    override fun onBindViewHolder(
        holder: ItemMultiViewHolder,
        position: Int
    ) {
        val t = currentList[position]
        bindVar(holder.binding, t)
        holder.bindTo()
        convert(holder, holder.binding, t)
    }

    override fun onBindViewHolder(
        holder: ItemMultiViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val t = currentList[position]
            bindVar(holder.binding, t)
            holder.bindTo()
            convert(holder, holder.binding, t, payloads)
        } else {
            onBindViewHolder(holder, position)
        }
    }


    override fun getItemCount(): Int {
        return currentList.size
    }

    /**
     * 绑定数据供DataBinding使用
     *
     * @param b 视图
     * @param t 数据
     */
    open fun bindVar(b: ViewDataBinding, t: T) = Unit

    /**
     * 其他绑定视图, 差分刷新
     *
     * @param binding  DataBinding对象
     * @param t        数据
     * @param payloads 额外刷新信息
     */
    protected open fun convert(
        holder: ItemMultiViewHolder,
        binding: ViewDataBinding,
        t: T,
        payloads: List<Any>
    ) = Unit

    /**
     * 其他绑定视图, 全量刷新
     *
     * @param binding   DataBinding对象
     * @param t         数据
     */
    protected abstract fun convert(holder: ItemMultiViewHolder, binding: ViewDataBinding, t: T)

    /**
     * 根据viewType获取视图属性
     *
     * 默认返回viewType
     */
    @LayoutRes
    open fun getLayoutByItemViewType(viewType: Int): Int = viewType
}


/**
 * 用于io.dushu.lib.basic.base.adapter.SimpleListAdapter
 *
 * @param <B> 视图</B>
 */
class ItemMultiViewHolder internal constructor(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(
        binding.root
    ) {
    fun bindTo() {
        binding.executePendingBindings()
    }

    companion object {
        fun <B : ViewDataBinding> create(
            parent: ViewGroup,
            @LayoutRes
            layoutId: Int
        ): ItemMultiViewHolder {
            val binding: B = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId, parent, false
            )
            return ItemMultiViewHolder(binding)
        }
    }
}