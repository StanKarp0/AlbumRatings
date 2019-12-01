package com.stankarp0.albumratings.ui.ratinglist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

    private val viewModel: RatingListViewModel by lazy {
        ViewModelProviders.of(this).get(RatingListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRatingListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_rating_list, container, false
        )

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

        return binding.root
    }


}
