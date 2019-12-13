package com.stankarp0.albumratings.ui.ratingdetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.stankarp0.albumratings.R
import com.stankarp0.albumratings.databinding.FragmentRatingBinding

/**
 * A simple [Fragment] subclass.
 */
class RatingFragment : Fragment() {

    private val viewModel: RatingDetailsViewModel by lazy {
        ViewModelProviders.of(this).get(RatingDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRatingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_rating, container, false
        )

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Arguments
        val args = RatingFragmentArgs.fromBundle(arguments!!)
        binding.rating = args.rating

        // Actions
        binding.performerDetailsButton.setOnClickListener {
            viewModel.findPerformer(args.rating)
        }

        viewModel.performer.observe(this, Observer {
            val action = RatingFragmentDirections.actionRatingFragmentToPerformerDetailsFragment(it)
            binding.root.findNavController().navigate(action)
        })

        binding.albumDetailsButton.setOnClickListener {
            viewModel.findAlbum(args.rating)
        }

        viewModel.album.observe(this, Observer {
            val action = RatingFragmentDirections.actionRatingFragmentToAlbumDetailsFragment(it)
            binding.root.findNavController().navigate(action)
        })

        // Inflate the layout for this fragment
        return binding.root
    }


}
