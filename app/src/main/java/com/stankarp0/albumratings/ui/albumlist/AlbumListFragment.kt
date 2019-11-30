package com.stankarp0.albumratings.ui.albumlist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.stankarp0.albumratings.R
import com.stankarp0.albumratings.databinding.FragmentAlbumListBinding
import com.stankarp0.albumratings.ui.adapters.AlbumRecyclerAdapter

/**
 * A simple [Fragment] subclass.
 */
class AlbumListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AlbumRecyclerAdapter

    private val viewModel: AlbumListViewModel by lazy {
        ViewModelProviders.of(this).get(AlbumListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_album_list, container, false)

        val binding: FragmentAlbumListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_album_list, container, false
        )

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // RecyclerView
        recyclerView = binding.recyclerView
        linearLayoutManager = LinearLayoutManager(inflater.context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = AlbumRecyclerAdapter { a ->
            AlbumListFragmentDirections.actionAlbumListFragmentToAlbumDetailsFragment(
                a
            )
        }
        recyclerView.adapter = adapter

        return binding.root
    }


}
