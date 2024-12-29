package com.example.myapp007_fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapp007_fragments.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnFragment01.setOnClickListener {
            replaceFragment(Fragment01())
        }

        binding.btnFragment02.setOnClickListener {
            replaceFragment(Fragment02())
        }
    }

    private fun replaceFragment(fragment : Fragment) {

        // získá instanci správce fragmentů
        val fragmentManager = supportFragmentManager

        // vytvoří novou transkakci s fragmenty
        val fragmentTransaction = fragmentManager.beginTransaction()

        // nahradí fragment v kontejneru novým fragmentem, který byl předán jako argument
        fragmentTransaction.replace(R.id.fragContainer, fragment)

        // potvrdí transakci a provede výměne fragmentu
        fragmentTransaction.commit()
    }


}



