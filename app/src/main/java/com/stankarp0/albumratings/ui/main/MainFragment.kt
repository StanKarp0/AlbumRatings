package com.stankarp0.albumratings.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stankarp0.albumratings.R
import com.stankarp0.albumratings.databinding.MainFragmentBinding
import com.stankarp0.albumratings.ui.adapters.AlbumRecyclerAdapter

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AlbumRecyclerAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: MainFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.main_fragment, container, false)
        swipeRefresh = binding.swipeRefresh
        swipeRefresh.isRefreshing = true

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // RecyclerView
        recyclerView = binding.recyclerView
        linearLayoutManager = LinearLayoutManager(inflater.context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = AlbumRecyclerAdapter{a ->
            MainFragmentDirections.actionMainFragmentToAlbumDetailsFragment(a)
        }
        recyclerView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.updateRandomAlbums()
        }

        viewModel.albumObject.observe(this, Observer {
            swipeRefresh.isRefreshing = false
        })

        return binding.root
    }
}
