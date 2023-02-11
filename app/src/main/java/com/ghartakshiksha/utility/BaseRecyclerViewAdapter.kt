package com.ghartakshiksha.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.ViewDataBinding


abstract class BaseRecyclerViewAdapter<T : Any, VB : ViewDataBinding>
    : RecyclerView.Adapter<BaseRecyclerViewAdapter.Companion.BaseViewHolder<VB>>() {

    var items = mutableListOf<T>()

    fun addItems(items: List<T>) {
        this.items.addAll(items)
        //this.items = items as MutableList<T>
        notifyDataSetChanged()
    }

    fun removeItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    var listener: ((view: View, item: T, position: Int) -> Unit)? = null
    var loadMoreListener: ((position: Int) -> Unit)? = null

    abstract fun getLayout(): Int
    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<VB>(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayout(), parent, false)
    )


    companion object {
        class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) :
            RecyclerView.ViewHolder(binding.root)
    }
}
