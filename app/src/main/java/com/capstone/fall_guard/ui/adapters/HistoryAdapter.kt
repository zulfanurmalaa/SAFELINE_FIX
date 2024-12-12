package com.capstone.fall_guard.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.fall_guard.databinding.ItemHistoryRowBinding

class HistoryAdapter : ListAdapter<String, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { listStory ->
            listStory?.let { item ->
                with(holder.binding) {
                    tvTime.text = item
                }
            }
        }
    }

    inner class ViewHolder(var binding: ItemHistoryRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldStories: String,
                newStories: String
            ): Boolean {
                return oldStories == newStories
            }

            override fun areContentsTheSame(
                oldStories: String,
                newStories: String
            ): Boolean {
                return oldStories == newStories
            }
        }
    }
}