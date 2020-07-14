package com.keatssalazar.guessgame.screens.game

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment

import com.keatssalazar.guessgame.R
import com.keatssalazar.guessgame.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    private lateinit var viewmodel: GameViewModel
    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        Log.i("keats", "ViewModelProviders called")
        viewmodel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)


        //binding xml to GameViewModel (whose reference is viewmodel )
        binding.gameViewModel = viewmodel

        //same as binding.setLifeCycleOwner(this)
        binding.lifecycleOwner = this
//        binding.skipButton.setOnClickListener {
//            viewmodel.onSkip()
//        }
//        binding.correctButton.setOnClickListener {
//            viewmodel.onCorrect()
//        }


        viewmodel.eventGameFinish.observe(this, Observer {
            if (it) {
                gameFinished()
                viewmodel.finish()
            }

        })

        viewmodel.eventBuzz.observe(this, Observer { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewmodel.onBuzzComplete()
            }
        })

//        viewmodel.currentTime.observe(this, Observer {
//            binding.timerText.text=DateUtils.formatElapsedTime(it)
//        })
        return binding.root
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewmodel.score.value ?: 0)
        NavHostFragment.findNavController(this).navigate(action)
//        Log.i("salazar","refinished")
    }

    private fun buzz(pattern: LongArray) {
       val buzzer=activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        buzzer.vibrate(pattern,-1)
    }

}
