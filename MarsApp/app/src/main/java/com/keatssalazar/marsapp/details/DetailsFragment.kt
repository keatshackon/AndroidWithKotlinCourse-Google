package com.keatssalazar.marsapp.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.keatssalazar.marsapp.R
import com.keatssalazar.marsapp.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {


    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding:DetailsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding=DataBindingUtil.inflate(layoutInflater,R.layout.details_fragment,container,false)

        val application= activity?.application

        binding.lifecycleOwner=this




        return binding.root
    }



}