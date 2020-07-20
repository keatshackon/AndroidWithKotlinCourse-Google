package com.keatssalazar.marsapp.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.keatssalazar.marsapp.network.MarsProperty
import java.lang.Appendable
import java.lang.IllegalArgumentException


class DetailsViewModelFactory(
    val marsProperty: MarsProperty,
    val application: Application):ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(marsProperty,application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}