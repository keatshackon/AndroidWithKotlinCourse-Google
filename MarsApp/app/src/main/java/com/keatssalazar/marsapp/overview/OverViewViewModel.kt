package com.keatssalazar.marsapp.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keatssalazar.marsapp.network.MarsApi
import com.keatssalazar.marsapp.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverViewViewModel : ViewModel() {

    private var _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    private var _properties = MutableLiveData<List<MarsProperty>>()
    val properties:LiveData<List<MarsProperty>>
        get() = _properties


    private val viewViewModeljob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewViewModeljob)


    init {
        getMarsProperties()
    }

    fun getMarsProperties() {

        uiScope.launch {

            val deferredProperty = MarsApi.retrofitService.getPropertiesAsync()
            try {
                _status.value= MarsApiStatus.LOADING
                val listitem = deferredProperty.await()
                _properties.value=listitem
                _status.value= MarsApiStatus.DONE

            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value=ArrayList()
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewViewModeljob.cancel()
    }


}