package com.stankarp0.albumratings.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stankarp0.albumratings.databinding.PerformerItemRowBinding
import com.stankarp0.albumratings.services.PerformerProperty


class PerformerRecyclerAdapter(
    private val on_click: (PerformerProperty) -> NavDirections) :
    ListAdapter<PerformerProperty, PerformerRecyclerAdapter.PerformerHolder>(
        DiffCallback
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PerformerHolder {
        return PerformerHolder(
            on_click,
            PerformerItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PerformerHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class PerformerHolder(
        private val on_click: (PerformerProperty) -> NavDirections,
        private var binding: PerformerItemRowBinding
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var view: View = binding.root

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            binding.performer?.let {
                v.findNavController().navigate(on_click(it))
            }
        }

        fun bind(property: PerformerProperty) {
            binding.performer = property
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PerformerProperty>() {
        override fun areItemsTheSame(
            oldItem: PerformerProperty,
            newItem: PerformerProperty
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PerformerProperty,
            newItem: PerformerProperty
        ): Boolean {
            return oldItem.performerId == newItem.performerId
        }
    }
}
