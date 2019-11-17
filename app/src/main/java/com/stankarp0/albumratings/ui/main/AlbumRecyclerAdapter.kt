package com.stankarp0.albumratings.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stankarp0.albumratings.databinding.AlbumItemRowBinding
import com.stankarp0.albumratings.services.AlbumProperty


class AlbumRecyclerAdapter: ListAdapter<AlbumProperty, AlbumRecyclerAdapter.AlbumHolder>(DiffCallback) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumHolder {
        return AlbumHolder(AlbumItemRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class AlbumHolder(private var binding: AlbumItemRowBinding)
        :RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var view: View = binding.root

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            binding.album?.let {
                val action = MainFragmentDirections.actionMainFragmentToAlbumDetailsFragment(it)
                v.findNavController().navigate(action)
            }
        }

        fun bind(albumProperty: AlbumProperty) {
            binding.album = albumProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<AlbumProperty>() {
        override fun areItemsTheSame(oldItem: AlbumProperty, newItem: AlbumProperty): Boolean {
            Log.i("AlbumRecyclerAdapter", "areItemsTheSame$oldItem:$newItem")
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AlbumProperty, newItem: AlbumProperty): Boolean {
            Log.i("AlbumRecyclerAdapter", "areContentsTheSame$oldItem:$newItem")
            return oldItem.albumId == newItem.albumId
        }
    }
}
