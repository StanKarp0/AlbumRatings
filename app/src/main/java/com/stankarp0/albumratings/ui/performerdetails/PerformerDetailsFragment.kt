package com.stankarp0.albumratings.ui.performerdetails


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
import com.stankarp0.albumratings.databinding.FragmentPerformerDetailsBinding
import com.stankarp0.albumratings.ui.adapters.AlbumRecyclerAdapter

/**
 * A simple [Fragment] subclass.
 */
class PerformerDetailsFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AlbumRecyclerAdapter

    private val viewModel: PerformerDetailsViewModel by lazy {
        ViewModelProviders.of(this).get(PerformerDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPerformerDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_performer_details, container, false
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
            PerformerDetailsFragmentDirections.actionPerformerDetailsFragmentToAlbumDetailsFragment(
                a
            )
        }
        recyclerView.adapter = adapter

        // Arguments
        val args = PerformerDetailsFragmentArgs.fromBundle(arguments!!)
        val performer = args.performer

        // Fill album list
        viewModel.updateModel(performer)

        return binding.root
    }


}
