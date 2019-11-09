package com.stankarp0.albumratings.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.stankarp0.albumratings.R
import kotlinx.android.synthetic.main.album_item_row.view.*


class AlbumRecyclerAdapter(private val albums: ArrayList<String>)
    : RecyclerView.Adapter<AlbumRecyclerAdapter.AlbumHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumHolder {

        val inflatedView = LayoutInflater.from(parent.context).inflate(
            R.layout.album_item_row, parent, false)

//        val inflatedView = parent.inflate(R.layout.album_item_row, false)
        return AlbumHolder(inflatedView)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        val item = albums[position]
        holder.bindAlbum(item)
    }


    class AlbumHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var description: String? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val action = MainFragmentDirections
                    .actionMainFragmentToAlbumDetailsFragment(description ?: "")
            v.findNavController().navigate(action)
        }

        fun bindAlbum(album: String) {
            view.album_description.text = album
        }

        companion object {
            private val ALBUM_KEY = "ALBUM"
        }
    }
}
