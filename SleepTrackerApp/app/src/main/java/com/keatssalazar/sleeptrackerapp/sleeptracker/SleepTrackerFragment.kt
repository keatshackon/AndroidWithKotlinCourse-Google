package com.keatssalazar.sleeptrackerapp.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.keatssalazar.sleeptrackerapp.R
import com.keatssalazar.sleeptrackerapp.database.SleepDatabase
import com.keatssalazar.sleeptrackerapp.databinding.FragmentSleepTrackerBinding

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 */

class SleepTrackerFragment :Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding:FragmentSleepTrackerBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracker,container,false)


        val application = requireNotNull(this.activity).application
        val dataSource= SleepDatabase.getInstance(application).SleepDatabaseDao

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        binding.lifecycleOwner = this

        binding.sleepTrackerViewModel=viewModel

        viewModel.navigateToSleepQuality.observe(this, Observer {night->
            if(night!=null){
                this.findNavController().navigate(SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                viewModel.doneNAvigating()
            }

        })
        viewModel.showSnackBar.observe(this, Observer {
            if(it==true){
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                viewModel.doneSnackbar()
            }
        })





        return binding.root
    }
}