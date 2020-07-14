package com.keatssalazar.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.keatssalazar.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val myName: MyName = MyName("Keats Salazar")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.myyName = myName
        binding.doneButton.setOnClickListener {
            addNickname(it)
        }

    }

    private fun addNickname(view: View) {

        binding.apply {
            invalidateAll()
         //   myyName?.nickname= nicknameEdit.text.toString()
            if(nicknameedit.text.toString().equals("")){
                nicknameedit.visibility = View.GONE
                view.visibility =GONE
                nicknameText.visibility = View.VISIBLE
            }
            else{
                myyName!!.nickname= nicknameedit.text.toString()
                nicknameedit.visibility = View.GONE
                view.visibility =GONE
                nicknameText.visibility = View.VISIBLE
            }
            // Invalidate all binding expressions and request a new rebind to refresh UI


        }
        // Hide the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}