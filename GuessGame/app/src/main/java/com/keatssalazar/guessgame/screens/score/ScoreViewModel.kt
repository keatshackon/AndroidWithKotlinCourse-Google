package com.keatssalazar.guessgame.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalS:Int) :ViewModel(){

    private val _score=MutableLiveData<Int>()
    val score:LiveData<Int>
        get() = _score

    private val _eventGameFinish=MutableLiveData<Boolean>()
    val eventGameFinish:LiveData<Boolean>
        get() = _eventGameFinish

    init {
        Log.i("sc","$finalS")
        _score.value=finalS
        _eventGameFinish.value=false
    }
    fun onPlayAgain(){
        _eventGameFinish.value=true
    }
    fun onPlayAgainComplete(){
        _eventGameFinish.value=false
    }
}