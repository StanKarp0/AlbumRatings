package com.stankarp0.albumratings.ui.performerlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val viewModel: PerformerListViewModel by lazy {
        ViewModelProviders.of(this).get(PerformerListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPerformerListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_performer_list, container, false
        )


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

        return binding.root

    }


}
