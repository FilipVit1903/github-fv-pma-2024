package com.example.myapp010_imgtoapp

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.myapp010_imgtoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedImageUri: Uri? = null
    private var originalBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Registrace výsledku výběru obrázku z galerie
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = uri
                binding.imgImage.setImageURI(uri)
                originalBitmap = binding.imgImage.drawable.toBitmap()
            }
        }

        // Tlačítko pro výběr obrázku
        binding.btnChoose.setOnClickListener {
            getContent.launch("image/*")
        }

        // Tlačítko pro přidání textu
        binding.btnAddText.setOnClickListener {
            if (originalBitmap != null) {
                showAddTextDialog()
            } else {
                Toast.makeText(this, "Vyberte nejprve obrázek", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Dialog pro zadání textu
    private fun showAddTextDialog() {
        val editText = EditText(this)
        editText.hint = "Zadejte text"

        AlertDialog.Builder(this)
            .setTitle("Přidat text")
            .setView(editText)
            .setPositiveButton("Přidat") { _, _ ->
                val text = editText.text.toString()
                if (text.isNotEmpty()) {
                    addTextToImage(text)
                } else {
                    Toast.makeText(this, "Text nesmí být prázdný", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Zrušit", null)
            .show()
    }

    // Funkce pro přidání textu na obrázek
    private fun addTextToImage(text: String) {
        originalBitmap?.let { bitmap ->
            val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(mutableBitmap)
            val paint = Paint()

            // Nastavení stylu textu
            paint.color = Color.WHITE
            paint.textSize = 94f
            paint.isAntiAlias = true
            paint.setShadowLayer(5f, 10f, 10f, Color.BLACK)

            // Přidání textu na obrázek (pozice: spodní část obrázku, uprostřed)
            val x = (canvas.width / 2) - (paint.measureText(text) / 2)
            val y = canvas.height - 120f
            canvas.drawText(text, x, y, paint)

            // Zobrazení upraveného obrázku
            binding.imgImage.setImageBitmap(mutableBitmap)
        }
    }
}
