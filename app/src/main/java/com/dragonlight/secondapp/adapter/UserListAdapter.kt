package com.dragonlight.secondapp.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dragonlight.secondapp.R
import com.dragonlight.secondapp.databinding.FragmentUserListBinding
import com.dragonlight.secondapp.databinding.ItemListUserBinding
import com.dragonlight.secondapp.db.entity.User

class UserListAdapter: ListAdapter<User,UserListAdapter.UserListViewHolder>(DiffCallback()) {
    var onItemClick: ((User) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListAdapter.UserListViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListAdapter.UserListViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener { onItemClick?.invoke(currentItem) }
    }

    class UserListViewHolder(private val binding: ItemListUserBinding):
        RecyclerView.ViewHolder(binding.root){

            @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
            fun bind(user: User){
                val buyOrSell = if (user.buyOrSell){
                   R.drawable.buyer
                }else{
                    R.drawable.seller
                }
                binding.apply {
                    tvListUser.text = user.userName
                    tvListNumber.text = user.userNumber
                    tvItemImportant.text = "Level-${user.levelId}"
                    imageBuyOrSeller.setImageResource(buyOrSell)
                }
            }
    }

    class DiffCallback: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
}