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
        val nickname = intent.getStringExtra("C_NICK_NAME")
        val surname = intent.getStringExtra("C_SUR_NAME")
        val age = intent.getStringExtra("C_AGE")
        val adress = intent.getStringExtra("C_ADRESS")
        twInfo.text = "Data z první aktivity Jméno: $nickname, Příjmení: $surname, Věk: $age, Adresa: $adress"

        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            finish()
        }

        val btnThird = binding.btnThird
        btnThird.setOnClickListener {
            val secondNickname = etSecNick.text.toString()
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("C_SECOND_NAME", secondNickname)
            startActivity(intent)
        }
    }
}