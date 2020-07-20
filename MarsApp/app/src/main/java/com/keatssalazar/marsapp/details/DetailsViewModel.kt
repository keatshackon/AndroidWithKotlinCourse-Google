package com.keatssalazar.marsapp.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.keatssalazar.marsapp.network.MarsProperty

class DetailsViewModel(
    val marsProperty: MarsProperty,
    application:Application) : AndroidViewModel(application) {



}