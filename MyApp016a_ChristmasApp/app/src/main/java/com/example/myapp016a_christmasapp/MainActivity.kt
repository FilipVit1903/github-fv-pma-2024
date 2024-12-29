package com.example.myapp016a_christmasapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp016a_christmasapp.databinding.ActivityMainBinding
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval: Long = 1000 // Aktualizace každou sekundu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace bindingu
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Spuštění odpočtu
        startCountdown()
    }

    private fun startCountdown() {
        val targetDate = Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.DAY_OF_MONTH, 24)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.time

        val runnable = object : Runnable {
            override fun run() {
                val currentDate = Date()
                val diffInMillis = targetDate.time - currentDate.time

                if (diffInMillis > 0) {
                    val seconds = diffInMillis / 1000 % 60
                    val minutes = diffInMillis / (1000 * 60) % 60
                    val hours = diffInMillis / (1000 * 60 * 60) % 24
                    val days = diffInMillis / (1000 * 60 * 60 * 24)

                    val countdownText = String.format(
                        "%02d dní %02dh %02dm %02ds",
                        days, hours, minutes, seconds
                    )

                    binding.countdownTextView.text = countdownText
                    handler.postDelayed(this, updateInterval)
                } else {
                    binding.countdownTextView.text = "Šťastné a veselé Vánoce! 🎄"
                }
            }
        }

        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Zastav aktualizace
    }
}