package com.keatssalazar.marsapp.overview

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.keatssalazar.marsapp.R
import com.keatssalazar.marsapp.databinding.GridViewItemBinding
import com.keatssalazar.marsapp.databinding.OverViewFragmentBinding

class OverViewFragment : Fragment() {

    private lateinit var binding: OverViewFragmentBinding

    private val viewModel: OverViewViewModel by lazy {
        ViewModelProviders.of(this).get(OverViewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = OverViewFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.overViewViewModel = viewModel

        binding.photosGrid.adapter = PhotoGridAdapter()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}