package com.agzz.transformerswars.ui.transformers.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agzz.transformerswars.databinding.TransformersListItemBinding
import com.agzz.transformerswars.models.Transformer
import com.agzz.transformerswars.ui.transformers.list.adapter.viewholder.TransformersViewHolder

class TransformersListAdapter(
    private val onTransformerIdLongPress: (Transformer) -> Boolean
) : RecyclerView.Adapter<TransformersViewHolder>() {

    var items: List<Transformer> = ArrayList(0)
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TransformersViewHolder(
            TransformersListItemBinding.inflate(inflater, parent, false),
            onTransformerIdLongPress
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TransformersViewHolder, position: Int) {
        holder.bindItem(items[position])
    }
}