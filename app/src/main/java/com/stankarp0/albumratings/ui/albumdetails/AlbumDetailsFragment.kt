package com.stankarp0.albumratings.ui.albumdetails


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stankarp0.albumratings.databinding.FragmentAlbumDetailsBinding
import com.stankarp0.albumratings.R
import com.stankarp0.albumratings.ui.adapters.RatingRecyclerAdapter
import com.stankarp0.albumratings.ui.albumdetails.AlbumDetailsFragmentArgs
import com.stankarp0.albumratings.ui.main.MainViewModel

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
        val binding: FragmentAlbumDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_album_details, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // RecyclerView
        recyclerView = binding.recyclerView
        linearLayoutManager = LinearLayoutManager(inflater.context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = RatingRecyclerAdapter()
        recyclerView.adapter = adapter

        // Arguments
        val args = AlbumDetailsFragmentArgs.fromBundle(arguments!!)
        binding.album = args.album
        Log.i("AlbumDetailsFragment", "onCreateView")
        viewModel.updateAlbumRatings(args.album)
        Log.i("AlbumDetailsFragment", viewModel.ratingObject.toString())
        return binding.root
    }

}
