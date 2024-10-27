package com.example.myapp005_moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp005_moreactivities.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.third)

        val twInfo = binding.twInfo

        //Load data from intent
        val secondNickname = intent.getStringExtra("SECOND_NAME")
        twInfo.text = "Data z druhé aktivity Přezdívka: $secondNickname"

        val btnPrevious = binding.btnPrevious
        btnPrevious.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            finish()
    }



    }
}