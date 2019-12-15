package com.stankarp0.albumratings.ui.performerlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stankarp0.albumratings.R
import com.stankarp0.albumratings.databinding.FragmentPerformerListBinding
import com.stankarp0.albumratings.ui.adapters.PerformerRecyclerAdapter

/**
 * A simple [Fragment] subclass.
 */
class PerformerListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PerformerRecyclerAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val viewModel: PerformerListViewModel by lazy {
        ViewModelProviders.of(this).get(PerformerListViewModel::class.java)
    }

    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPerformerListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_performer_list, container, false
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
        adapter = PerformerRecyclerAdapter { p ->
            PerformerListFragmentDirections.actionPerformerListFragmentToPerformerDetailsFragment(
                p
            )
        }
        recyclerView.adapter = adapter

        viewModel.performerObject.observe(this, Observer {
            setRecyclerViewScrollListener()
            binding.swipeRefresh.isRefreshing = false
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

        swipeRefresh.setOnRefreshListener {
            recyclerView.removeOnScrollListener(scrollListener)
            viewModel.reloadPerformers()
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
                    swipeRefresh.isRefreshing = true
                    viewModel.updatePerformers()
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

}
