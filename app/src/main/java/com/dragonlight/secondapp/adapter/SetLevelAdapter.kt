package com.dragonlight.secondapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dragonlight.secondapp.databinding.ItemListUserBinding
import com.dragonlight.secondapp.databinding.ItemSetLevelBinding
import com.dragonlight.secondapp.db.entity.ImportantLevel

class SetLevelAdapter: ListAdapter<ImportantLevel,SetLevelAdapter.SetLevelViewHolder>(DiffCallBack()) {

    var onItemClick: ((ImportantLevel) -> Unit)?= null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SetLevelAdapter.SetLevelViewHolder {
        val binding = ItemSetLevelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SetLevelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SetLevelAdapter.SetLevelViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener { onItemClick?.invoke(currentItem) }
    }


    class SetLevelViewHolder(private val binding: ItemSetLevelBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(level: ImportantLevel){
            binding.apply {
//                tvItemLevelId.text = level.levelId.toString()
                tvItemAddLevelTitle.text = level.levelGroup
                tvAddLevelInfo.text = level.levelInfo
                imageItemAddLevel.setImageDrawable(ContextCompat.getDrawable(itemView.context,level.levelColor))
//                tvItemLevelColor.text = level.levelColor.toString()
            }
        }
    }
    class DiffCallBack: DiffUtil.ItemCallback<ImportantLevel>(){
        override fun areItemsTheSame(oldItem: ImportantLevel, newItem: ImportantLevel): Boolean {
            return oldItem.levelId == newItem.levelId
        }

        override fun areContentsTheSame(oldItem: ImportantLevel, newItem: ImportantLevel): Boolean {
            return oldItem == newItem
        }

    }
}