package com.keatssalazar.sleeptrackerapp.sleeptracker

import android.app.Application
import androidx.lifecycle.*
import com.keatssalazar.sleeptrackerapp.database.SleepDatabase
import com.keatssalazar.sleeptrackerapp.database.SleepDatabaseDao
import com.keatssalazar.sleeptrackerapp.database.SleepNight
import com.keatssalazar.sleeptrackerapp.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application) :
    AndroidViewModel(application) {


    private val viewModelJob = Job()

    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tonight = MutableLiveData<SleepNight?>()

    private val nights = database.getAllNight()

    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    val startButtonVisible = Transformations.map(tonight){
        null==it
    }
    val stopButtonVisible= Transformations.map(tonight){
        it!=null
    }
    val  ClearButtonVisible=Transformations.map(nights){
        it?.isNotEmpty()
    }

    private val _showSnackBar=MutableLiveData<Boolean>()
    val showSnackBar:LiveData<Boolean>
        get() =_showSnackBar
    private val _navigateToSleepQuality=MutableLiveData<SleepNight>()
    val navigateToSleepQuality:LiveData<SleepNight>
        get() = _navigateToSleepQuality


    fun doneNAvigating(){
        _navigateToSleepQuality.value=null
    }
    fun doneSnackbar(){
        _showSnackBar.value=false
    }

    init {
        initializeTonight()
        _navigateToSleepQuality.value=null
        _showSnackBar.value=false
    }

    fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }
    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()

            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newNight = SleepNight()
            insertt(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }
    private suspend fun insertt(night: SleepNight) {
        return withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }
    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            updatee(oldNight)
            _navigateToSleepQuality.value=oldNight
        }
    }
    private suspend fun updatee(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }

    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
            _showSnackBar.value=true
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}