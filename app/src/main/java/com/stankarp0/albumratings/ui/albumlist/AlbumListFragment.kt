package com.stankarp0.albumratings.ui.albumlist


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private val viewModel: AlbumListViewModel by lazy {
        ViewModelProviders.of(this).get(AlbumListViewModel::class.java)
    }

    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

        viewModel.albumObject.observe(this, Observer {
            setRecyclerViewScrollListener()
        })

        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_ACTION_PREVIOUS -> {
                    recyclerView.removeOnScrollListener(scrollListener)
                    viewModel.setQuery(v.text?.toString() ?: "")
                    true
                }
                else -> {
                    false
                }
            }
        }

        return binding.root
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = linearLayoutManager.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    recyclerView.removeOnScrollListener(scrollListener)
                    viewModel.updateAlbums()
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }


}
