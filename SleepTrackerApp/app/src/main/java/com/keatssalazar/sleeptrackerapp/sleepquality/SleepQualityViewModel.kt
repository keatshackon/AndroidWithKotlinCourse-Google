package com.keatssalazar.sleeptrackerapp.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keatssalazar.sleeptrackerapp.database.SleepDatabaseDao
import kotlinx.coroutines.*

class SleepQualityViewModel (val database:SleepDatabaseDao,val latestKey:Long):ViewModel(){


    private val _navigateToSleeptracker=MutableLiveData<Boolean>()
    val navigateToSleepTracker:LiveData<Boolean>
        get() = _navigateToSleeptracker

    fun doneNavigating(){
        _navigateToSleeptracker.value=false
    }
    init {
        _navigateToSleeptracker.value=false
    }

    val viewModelJob= Job()
    val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)

    fun onSetSleepQuality(quality: Int) {
        uiScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.
            withContext(Dispatchers.IO) {
                val tonight = database.get(latestKey)
                tonight.sleepQuality = quality
                database.update(tonight)
            }

            // Setting this state variable to true will alert the observer and trigger navigation.
            _navigateToSleeptracker.value = true
        }
    }





    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}