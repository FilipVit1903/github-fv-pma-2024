package com.example.myapp003

import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp003.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        //binding settings
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Change title
        title = "Objednávka smoothie"

        binding.btnOrder.setOnClickListener {
            //id load radiobutton from radioGroup
            val smoothieId = binding.rgSmoothies.checkedRadioButtonId

            val smoothie = findViewById<RadioButton>(smoothieId)

            val fruit = binding.cbFruit.isChecked
            val milk = binding.cbMilk.isChecked
            val ice = binding.cbIce.isChecked

            val orderText = "Souhrn objednávky: " +
                    "${smoothie.text}" +
                    (if (fruit) "; ovoce navíc" else "") +
                    (if (milk) "; mléko navíc" else "") +
                    (if (ice) ";  s ledem" else "")

            binding.tvOrder.text = orderText
        }

        //Image change on choose radioButton
        binding.rbSmoothie01.setOnClickListener {
            binding.imgSmoothie.setImageResource(R.drawable.strawberry_smoothie)
        }

        binding.rbSmoothie02.setOnClickListener {
            binding.imgSmoothie.setImageResource(R.drawable.pineaple_smoothie)
        }

        binding.rbSmoothie03.setOnClickListener {
            binding.imgSmoothie.setImageResource(R.drawable.peach_smoothie)
        }

    }
}