package com.example.myapp006_toast_snackbar

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp006_toast_snackbar.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Nastaveni akce pro btn
        binding.btnShowToast.setOnClickListener {
            val toast = Toast.makeText(this, "Hello - I´m hungry", Toast.LENGTH_LONG)
            toast.show()
        }

        //Snackbar
        binding.btnShowSnackbar.setOnClickListener {
            val snackbar = Snackbar.make(binding.root, "Snackbar text", Snackbar.LENGTH_LONG)
            snackbar.show()
            snackbar.setDuration(7000).setBackgroundTint(Color.parseColor("#547465"))
                .setActionTextColor(Color.parseColor("red"))

            snackbar.setAction("Zavřít") {
                Toast.makeText(this, "Zavírám SNACK", Toast.LENGTH_LONG)
                    .show()
            }
        }

        val toastWrapper = null
        val layout = layoutInflater.inflate(R.layout.custom_toast_layout,toastWrapper)

        //Custom TOAST
        binding.btnShowCustomToast.setOnClickListener {
            //nacteni custom layoutu toast
            Toast(this).apply {
                duration = Toast.LENGTH_LONG
                view = layout
            }.show()
        }


    }
}
