package com.example.simplegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        img_play.setOnClickListener {
            val intent = Intent (img_play.context,StartGameActivity::class.java)
            img_play.context.startActivity(intent)
            finish()
        }
    }
}
