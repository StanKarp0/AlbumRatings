package com.stankarp0.albumratings

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stankarp0.albumratings.services.AlbumObject
import com.stankarp0.albumratings.services.RatingObject
import com.stankarp0.albumratings.ui.adapters.AlbumRecyclerAdapter
import com.stankarp0.albumratings.ui.adapters.RatingRecyclerAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: AlbumObject?) {
    val adapter = recyclerView.adapter as AlbumRecyclerAdapter
    Log.i("bindRecyclerView", data?.albums.toString())
    adapter.submitList(data?.albums)
}


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: RatingObject?) {
    val adapter = recyclerView.adapter as RatingRecyclerAdapter
    Log.i("bindRecyclerView", data?.ratings.toString())
    adapter.submitList(data?.ratings)
}