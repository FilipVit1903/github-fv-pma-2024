package com.example.myapp001_linear_layout

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */

        val etName = findViewById<EditText>(R.id.etName)
        val etSurname = findViewById<EditText>(R.id.etSurname)
        val etCity = findViewById<EditText>(R.id.etCity)
        val etAge = findViewById<EditText>(R.id.etAge)
        val tvInformation = findViewById<TextView>(R.id.tvInformation)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        //Nastaveni obsluhy pro btn
        btnSend.setOnClickListener {
            val name = etName.text.toString()
            val surname = etSurname.text.toString()
            val city = etCity.text.toString()
            val age = etAge.text.toString()

            //Zobrazeni textu v textView
            val formattedText = "Jmenuji se $name $surname. Je mi $age let a moje bydliště je $city"
            tvInformation.text = formattedText
        }

        btnDelete.setOnClickListener {
            etName.text.clear()
            etSurname.text.clear()
            etAge.text.clear()
            etCity.text.clear()

            tvInformation.text = ""

        }

    }
}