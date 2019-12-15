package com.stankarp0.albumratings.ui.albumdetails


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
import com.stankarp0.albumratings.databinding.FragmentAlbumDetailsBinding
import com.stankarp0.albumratings.R
import com.stankarp0.albumratings.ui.adapters.RatingRecyclerAdapter
import android.content.Intent



/**
 * A simple [Fragment] subclass.
 */
class AlbumDetailsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RatingRecyclerAdapter

    private val viewModel: AlbumDetailsViewModel by lazy {
        ViewModelProviders.of(this).get(AlbumDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAlbumDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_album_details, container, false
        )

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // RecyclerView
        recyclerView = binding.recyclerView
        linearLayoutManager = LinearLayoutManager(inflater.context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = RatingRecyclerAdapter { rating ->
            AlbumDetailsFragmentDirections.actionAlbumDetailsFragmentToRatingFragment(rating)
        }
        recyclerView.adapter = adapter

        // Arguments
        val args = AlbumDetailsFragmentArgs.fromBundle(arguments!!)
        val album = args.album
        binding.album = album

        // Fill album list
        viewModel.updateAlbumRatings(album)

        // Actions
        binding.performerDetailsButton.setOnClickListener {
            viewModel.findPerformer(album)
        }

        viewModel.performer.observe(this, Observer {
            val action = AlbumDetailsFragmentDirections.actionAlbumDetailsFragmentToPerformerDetailsFragment(it)
            binding.root.findNavController().navigate(action)
        })

        binding.searchButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEARCH)
            intent.setPackage("com.google.android.youtube")
            intent.putExtra("query", album.header)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        return binding.root
    }

}
