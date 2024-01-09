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
abstract class SimpleListHolderAdapter<T, B : ViewDataBinding> : ListAdapter<T, ItemViewHolder<B>> {
    protected constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)
    protected constructor(config: AsyncDifferConfig<T>) : super(config)

    ///////////////////////////////////////////////////////
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ItemViewHolder<B> {
        return ItemViewHolder.create(parent, layoutId)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder<B>,
        position: Int
    ) {
        val t = currentList[position]
        bindVar(holder.binding, t)
        holder.bindTo()
        convert(holder.binding, t, holder)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder<B>,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val t = currentList[position]
            bindVar(holder.binding, t)
            holder.bindTo()
            convert(holder.binding, t, holder, payloads)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    @get:LayoutRes
    protected abstract val layoutId: Int

    /**
     * 绑定数据供DataBinding使用
     *
     * @param b 视图
     * @param t 数据
     */
    open fun bindVar(b: B, t: T) {}

    /**
     * 其他绑定视图, 差分刷新
     *
     * @param binding  DataBinding对象
     * @param t        数据
     * @param holder   Holder, 可以获取位置信息
     * @param payloads 额外刷新信息
     */
    protected open fun convert(
        binding: B,
        t: T,
        holder: ItemViewHolder<B>,
        payloads: List<Any>
    ) = Unit

    /**
     * 其他绑定视图, 全量刷新
     *
     * @param binding  DataBinding对象
     * @param t        数据
     * @param holder   Holder, 可以获取位置信息
     */
    protected abstract fun convert(binding: B, t: T, holder: ItemViewHolder<B>)
}


/**
 * 用于io.dushu.lib.basic.base.adapter.SimpleListAdapter
 *
 * @param <B> 视图
</B> */
class ItemViewHolder<B : ViewDataBinding> internal constructor(val binding: B) :
    RecyclerView.ViewHolder(
        binding.root
    ) {
    fun bindTo() {
        binding.executePendingBindings()
    }

    companion object {
        fun <B : ViewDataBinding> create(
            parent: ViewGroup,
            viewType: Int
        ): ItemViewHolder<B> {
            val binding: B = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType, parent, false
            )
            return ItemViewHolder(binding)
        }
    }
}