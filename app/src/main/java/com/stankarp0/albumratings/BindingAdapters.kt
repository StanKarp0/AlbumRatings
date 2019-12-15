package com.stankarp0.albumratings

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stankarp0.albumratings.services.AlbumObject
import com.stankarp0.albumratings.services.PerformerObject
import com.stankarp0.albumratings.services.RatingObject
import com.stankarp0.albumratings.ui.adapters.AlbumRecyclerAdapter
import com.stankarp0.albumratings.ui.adapters.PerformerRecyclerAdapter
import com.stankarp0.albumratings.ui.adapters.RatingRecyclerAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: AlbumObject?) {
    val adapter = recyclerView.adapter as AlbumRecyclerAdapter
    adapter.submitList(data?.albums)
}


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: RatingObject?) {
    val adapter = recyclerView.adapter as RatingRecyclerAdapter
    adapter.submitList(data?.ratings)
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: PerformerObject?) {
    val adapter = recyclerView.adapter as PerformerRecyclerAdapter
    adapter.submitList(data?.performers)
}
