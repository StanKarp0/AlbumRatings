package com.stankarp0.albumratings.ui.ratinglist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.stankarp0.albumratings.R
import com.stankarp0.albumratings.databinding.FragmentAlbumDetailsBinding
import com.stankarp0.albumratings.databinding.FragmentRatingListBinding
import com.stankarp0.albumratings.ui.adapters.RatingRecyclerAdapter
import com.stankarp0.albumratings.ui.albumdetails.AlbumDetailsFragmentArgs
import com.stankarp0.albumratings.ui.albumdetails.AlbumDetailsFragmentDirections
import com.stankarp0.albumratings.ui.albumdetails.AlbumDetailsViewModel

/**
 * A simple [Fragment] subclass.
 */
class RatingListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RatingRecyclerAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val viewModel: RatingListViewModel by lazy {
        ViewModelProviders.of(this).get(RatingListViewModel::class.java)
    }
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRatingListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_rating_list, container, false
        )
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
        adapter = RatingRecyclerAdapter{ rating ->
            RatingListFragmentDirections.actionRatingListFragmentToRatingFragment(rating)
        }
        recyclerView.adapter = adapter

        viewModel.ratingObject.observe(this, Observer {
            swipeRefresh.isRefreshing = false
        })

        swipeRefresh.setOnRefreshListener {
            recyclerView.removeOnScrollListener(scrollListener)
            viewModel.reloadRatings()
        }

        viewModel.ratingObject.observe(this, Observer {
            setRecyclerViewScrollListener()
            swipeRefresh.isRefreshing = false
        })

        return binding.root
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = linearLayoutManager.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    swipeRefresh.isRefreshing = true
                    recyclerView.removeOnScrollListener(scrollListener)
                    viewModel.updateRatings()
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

}
