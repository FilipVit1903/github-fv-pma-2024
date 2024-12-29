package com.example.myapp009_datastore

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapp009_datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Inicializace DataStore
    private val Context.dataStore by preferencesDataStore(name = "myDataStore")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Uložení dat do DataStore
        binding.btn1.setOnClickListener {
            val name = binding.etName.text.toString()
            val ageString = binding.etAge.text.toString().trim()

            if (ageString.isBlank()) {
                Toast.makeText(this, "Věk je prázdný", Toast.LENGTH_LONG).show()
            } else {
                val age = ageString.toInt()
                val isAdult = binding.chbAdult.isChecked
                if ((age < 18 && isAdult) || (age >= 18 && !isAdult)) {
                    Toast.makeText(this, "Není ti 18 nebo více", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "V pořádku", Toast.LENGTH_LONG).show()
                    lifecycleScope.launch {
                        saveDataToDataStore(name, age, isAdult)
                    }
                }
            }
        }

        // Načtení dat z DataStore
        binding.btn2.setOnClickListener {
            lifecycleScope.launch {
                val userData = loadDataFromDataStore()
                binding.etName.setText(userData.name)
                binding.etAge.setText(userData.age.toString())
                binding.chbAdult.isChecked = userData.isAdult
            }
        }
    }

    // Uložení dat do DataStore
    private suspend fun saveDataToDataStore(name: String, age: Int, isAdult: Boolean) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[AGE_KEY] = age
            preferences[IS_ADULT_KEY] = isAdult
        }
    }

    // Načtení dat z DataStore
    private suspend fun loadDataFromDataStore(): UserData {
        val preferences = dataStore.data.first()
        val name = preferences[NAME_KEY] ?: ""
        val age = preferences[AGE_KEY] ?: 0
        val isAdult = preferences[IS_ADULT_KEY] ?: false
        return UserData(name, age, isAdult)
    }

    companion object {
        // Klíče pro DataStore
        private val NAME_KEY = stringPreferencesKey("name")
        private val AGE_KEY = intPreferencesKey("age")
        private val IS_ADULT_KEY = booleanPreferencesKey("isAdult")
    }
}

// Datová třída pro uložení dat
data class UserData(val name: String, val age: Int, val isAdult: Boolean)

