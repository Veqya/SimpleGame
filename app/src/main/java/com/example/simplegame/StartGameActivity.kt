package com.example.simplegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics

class StartGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        val gameView = GameView(this)
        setContentView(gameView)

        val displayMetrics = DisplayMetrics()
    }
}
