package com.dragonlight.secondapp.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dragonlight.secondapp.R
import com.dragonlight.secondapp.databinding.ItemUserDetailBinding
import com.dragonlight.secondapp.db.entity.Task

class UserDetailAdapter: ListAdapter<Task,UserDetailAdapter.UserDetailViewHolder>(DiffCallback()) {

    var onItemClick: ((Task) -> Unit)? = null

    class UserDetailViewHolder(private val binding: ItemUserDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task){
            binding.apply {
                tvUserDetailTaskTitle.text = task.taskTitle
                tvUserDetailTaskInfo.text = task.taskInfo
                tvUserDetailTaskStartDate.text =task.date
                tvUserDetailSpinnerLevel.text = task.importantLevelId.toString()
                checkLevelDrawer(task)
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun checkLevelDrawer(task: Task) {
            val image = binding.imageUserDetailTaskImportant
            when(task.importantLevelId){
                R.drawable.shape_o ->{image.setImageDrawable(Resources.getSystem().getDrawable(R.drawable.shape_o))}
                R.drawable.shape_b ->{image.setImageDrawable(Resources.getSystem().getDrawable(R.drawable.shape_b))}
                R.drawable.shape_g ->{image.setImageDrawable(Resources.getSystem().getDrawable(R.drawable.shape_g))}
                R.drawable.shape_p ->{image.setImageDrawable(Resources.getSystem().getDrawable(R.drawable.shape_p))}
                R.drawable.shape_r ->{image.setImageDrawable(Resources.getSystem().getDrawable(R.drawable.shape_r))}
                R.drawable.shape_y ->{image.setImageDrawable(Resources.getSystem().getDrawable(R.drawable.shape_y))}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailViewHolder {
        val binding = ItemUserDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserDetailViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener { onItemClick?.invoke(currentItem) }
    }

    class DiffCallback: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}