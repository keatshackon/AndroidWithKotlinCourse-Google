package com.keatssalazar.sleeptrackerapp.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.keatssalazar.sleeptrackerapp.R
import com.keatssalazar.sleeptrackerapp.database.SleepDatabase
import com.keatssalazar.sleeptrackerapp.databinding.FragmentSleepQualityBinding
import com.keatssalazar.sleeptrackerapp.sleeptracker.SleepTrackerViewModel
import com.keatssalazar.sleeptrackerapp.sleeptracker.SleepTrackerViewModelFactory

class SleepQualityFragment :Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding:FragmentSleepQualityBinding=DataBindingUtil.inflate(inflater,
            R.layout.fragment_sleep_quality,container,false)

        val application= requireNotNull(this.context).applicationContext

        val dataSource= SleepDatabase.getInstance(application).SleepDatabaseDao

        val arguments = SleepQualityFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)

        val viewModel= ViewModelProviders.of(this,viewModelFactory).get(SleepQualityViewModel::class.java)

        binding.lifecycleOwner=this

        binding.sleepQualityViewModel=viewModel

        viewModel.navigateToSleepTracker.observe(this, Observer {
            if(it==true){
                this.findNavController().navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                viewModel.doneNavigating()
            }
        })
        return binding.root

    }
}