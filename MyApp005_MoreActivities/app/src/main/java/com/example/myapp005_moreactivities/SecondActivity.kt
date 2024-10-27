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
import com.example.myapp005_moreactivities.databinding.ActivityMainBinding
import com.example.myapp005_moreactivities.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.second)

        val twInfo = binding.twInfo
        val etSecNick = binding.etSecNick

        //Load data from intent
        val nickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Data z první aktivity Jméno: $nickname"

        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            finish()
        }

        val btnThird = binding.btnThird
        btnThird.setOnClickListener {
            val secondNickname = etSecNick.text.toString()
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("SECOND_NAME", secondNickname)
            startActivity(intent)
        }
    }
}