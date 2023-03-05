package river.chat.lib_core.view.recycleview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import river.chat.lib_core.BR

class BindingViewHolder<T, BINDING : ViewDataBinding>(val binding: BINDING)
    : RecyclerView.ViewHolder(binding.root){

    fun bind(t: T?) {
        binding.setVariable(BR.item, t)
    }

    fun setItemEventHandler(handler:Any?){
        binding.setVariable(BR.handler, handler)
    }

    fun setItemPosition(position:Int){
        binding.setVariable(BR.position, position)
    }

}
