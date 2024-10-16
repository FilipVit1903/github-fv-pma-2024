package com.example.myapp005_moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContentView(R.layout.activity_third)

        val twInfo = findViewById<TextView>(R.id.twInfo)

        //Load data from intent
        val secondNickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Data z první aktivity Jméno: $secondNickname"

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
    }



    }
}