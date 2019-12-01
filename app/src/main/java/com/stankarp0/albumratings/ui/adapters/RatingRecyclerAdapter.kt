package com.stankarp0.albumratings.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stankarp0.albumratings.databinding.RatingItemRowBinding
import com.stankarp0.albumratings.services.RatingProperty
import kotlinx.android.synthetic.main.fragment_rating.view.*


class RatingRecyclerAdapter(
    private val rating_details: (RatingProperty) -> NavDirections
) : ListAdapter<RatingProperty, RatingRecyclerAdapter.RatingHolder>(
    DiffCallback
) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RatingHolder {
        return RatingHolder(
            rating_details,
            RatingItemRowBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: RatingHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class RatingHolder(
        rating_details: (RatingProperty) -> NavDirections,
        private var binding: RatingItemRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var view: View = binding.root

        init {
            binding.detailsButton.setOnClickListener {
                binding.rating?.let {
                    view.findNavController().navigate(rating_details(it))
                }
            }
        }

        fun bind(ratingProperty: RatingProperty) {
            binding.rating = ratingProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RatingProperty>() {
        override fun areItemsTheSame(oldItem: RatingProperty, newItem: RatingProperty): Boolean {
            Log.i("AlbumRecyclerAdapter", "areItemsTheSame$oldItem:$newItem")
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RatingProperty, newItem: RatingProperty): Boolean {
            Log.i("AlbumRecyclerAdapter", "areContentsTheSame$oldItem:$newItem")
            return oldItem.albumId == newItem.albumId
        }
    }
}
