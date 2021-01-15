package com.agzz.transformerswars.ui.transformers.list.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.agzz.transformerswars.R
import com.agzz.transformerswars.databinding.TransformersListItemBinding
import com.agzz.transformerswars.models.overallRating
import com.agzz.transformerswars.models.Transformer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


private const val STROKE = 5f
private const val CENTER_RADIUS = 30f

class TransformersViewHolder(
    private val binding: TransformersListItemBinding,
    onTransformerIdLongPress: (Transformer) -> Boolean
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var transformerId: Transformer
    private val circularProgressDrawable: CircularProgressDrawable

    init {
        binding.root.setOnClickListener {
            onTransformerIdLongPress(transformerId)
        }
        binding.root.setOnLongClickListener {
            onTransformerIdLongPress(transformerId)
        }
        circularProgressDrawable = CircularProgressDrawable(binding.root.context).apply {
            strokeWidth = STROKE
            centerRadius = CENTER_RADIUS
            start()
        }
    }

    fun bindItem(transformer: Transformer) {
        transformerId = transformer
        binding.tvName.text = transformer.name
        binding.tvStrength.text = itemView.context.getString(R.string.stat_str, transformer.strength)
        binding.tvIntelligence.text = itemView.context.getString(R.string.stat_int, transformer.intelligence)
        binding.tvSpeed.text = itemView.context.getString(R.string.stat_spd, transformer.speed)
        binding.tvEndurance.text = itemView.context.getString(R.string.stat_end, transformer.endurance)
        binding.tvRank.text = itemView.context.getString(R.string.stat_rnk, transformer.rank)
        binding.tvCourage.text = itemView.context.getString(R.string.stat_crg, transformer.courage)
        binding.tvFirepower.text = itemView.context.getString(R.string.stat_fpw, transformer.firepower)
        binding.tvSkill.text = itemView.context.getString(R.string.stat_skl, transformer.skill)
        binding.tvOverall.text = transformer.overallRating().toString()
        Glide.with(itemView)
            .load(transformer.team_icon)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(circularProgressDrawable)
            .dontAnimate()
            .into(binding.ivImage)
    }
}