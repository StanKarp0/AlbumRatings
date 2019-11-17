package com.stankarp0.albumratings.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.stankarp0.albumratings.databinding.FragmentAlbumDetailsBinding
import com.stankarp0.albumratings.R

/**
 * A simple [Fragment] subclass.
 */
class AlbumDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAlbumDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_album_details, container, false)

        val args = AlbumDetailsFragmentArgs.fromBundle(arguments!!)
        binding.album = args.album

        return binding.root
    }


}
