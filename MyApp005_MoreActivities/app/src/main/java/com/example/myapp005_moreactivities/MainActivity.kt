package com.example.myapp005_moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp005_moreactivities.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnSecond = binding.btnSecond
        val etNickname = binding.etNickname
        val etSurname = binding.etSurname
        val etAdress = binding.etAdress
        val etAge = binding.etAge

        btnSecond.setOnClickListener {
            val nickname = etNickname.text.toString()
            val surname = etSurname.text.toString()
            val age = etAge.text.toString().toInt()
            val adress = etAdress.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("C_NICK_NAME", nickname)
            intent.putExtra("C_SUR_NAME", surname)
            intent.putExtra("C_AGE", age)
            intent.putExtra("C_ADRESS", adress)
            startActivity(intent)
        }
    }
}
