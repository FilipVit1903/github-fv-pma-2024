package com.example.myapp005_moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val twInfo = findViewById<TextView>(R.id.twInfo)
        val etSecNick = findViewById<EditText>(R.id.etSecNick)

        //Load data from intent
        val nickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Data z první aktivity Jméno: $nickname"

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val btnThird = findViewById<Button>(R.id.btnThird)
        btnThird.setOnClickListener {
            val secondNickname = etSecNick.text.toString()
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("SECOND_NAME", secondNickname)
            startActivity(intent)
        }
    }
}