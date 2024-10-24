package com.example.myapp007_fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapp007_fragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var binding: ActivityMainBinding

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFragment01.setOnClickListener {
            replaceFragment(Fragment01())
        }

        binding.btnFragment02.setOnClickListener {
            replaceFragment(Fragment02())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        //get instance from fragment manager
        val fragmentManager = supportFragmentManager

        //create transaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragContainer, fragment)

        fragmentTransaction.commit()
    }

}