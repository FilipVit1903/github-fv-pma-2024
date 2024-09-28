package com.example.myapplication002

import android.os.Bundle
import android.view.View
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

        //Definice prvku
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val validUsername = findViewById<TextView>(R.id.validUsername)
        val validPassword = findViewById<TextView>(R.id.validPassword)
        val twSuccess = findViewById<TextView>(R.id.twSuccess)
        val twFail = findViewById<TextView>(R.id.twFail)

        btnLogin.setOnClickListener {
            val Username = etUsername.text.toString()
            val Password = etPassword.text.toString()

            if (Username == validUsername.text && Password == validPassword.text ) {
                twSuccess.visibility = View.VISIBLE
                twFail.visibility = View.GONE
            } else {
                twFail.visibility = View.VISIBLE
                twSuccess.visibility = View.GONE
            }
        }

    }
}