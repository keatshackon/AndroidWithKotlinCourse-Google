package com.keatsSalazar.diceroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var image:ImageView    //lateinit[keyword] promisees the we leave the image view as null and also initialize before doing any operation on it and also treatedas non null!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton:Button = findViewById(R.id.roll_dice);
        rollButton.setOnClickListener {
            rollDice()
        }
        image=findViewById(R.id.img)
    }
    private fun rollDice() {
        val imageres= when(Random.nextInt(6) + 1){
            1->R.drawable.dice_1
            2->R.drawable.dice_2
            3->R.drawable.dice_3
            4->R.drawable.dice_4
            5->R.drawable.dice_5
           else -> R.drawable.dice_6
       }
        image.setImageResource(imageres)
    }
}
