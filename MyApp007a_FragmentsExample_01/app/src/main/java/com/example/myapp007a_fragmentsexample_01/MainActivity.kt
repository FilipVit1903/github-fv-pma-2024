package com.example.myapp007a_fragmentsexample_01

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp007a_fragmentsexample_01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Nastavení View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nastavení tlačítek pro výběr fragmentů
        binding.btnConsole1.setOnClickListener {
            showFragment("Nintendo NES", R.drawable.nes)
        }

        binding.btnConsole2.setOnClickListener {
            showFragment("Nintendo SNES", R.drawable.snes)
        }

        binding.btnConsole3.setOnClickListener {
            showFragment("Nintendo 64", R.drawable.n64)
        }

        binding.btnConsole4.setOnClickListener {
            showFragment("Nintendo Gamecube", R.drawable.gamecube)
        }

        binding.btnConsole5.setOnClickListener {
            showFragment("Nintendo Wii", R.drawable.wii)
        }
    }

    private fun showFragment(name: String, imageResId: Int) {
        // Zobrazíme FrameLayout a tlačítko zpět
        binding.fragmentContainer.visibility = View.VISIBLE

        // Přidáme fragment
        val fragment = ConsoleFragment.newInstance(name, imageResId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun showIntroScreen() {
        // Skryjeme FrameLayout a tlačítko zpět
        binding.fragmentContainer.visibility = View.GONE
    }
}
