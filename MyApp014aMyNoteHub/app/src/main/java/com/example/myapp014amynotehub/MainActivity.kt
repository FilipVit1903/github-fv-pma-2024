package com.example.myapp014amynotehub

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp014amynotehub.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var database: NoteHubDatabase

    private var isNameAscending = true // Stav řazení podle názvu
    private var currentCategory: String = "Vše" // Aktuálně vybraná kategorie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializace databáze
        database = NoteHubDatabaseInstance.getDatabase(this)

        // Vložení výchozích kategorií
        insertDefaultCategories()

        // Inicializace RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Nastavení uživatelského rozhraní
        setupUI()

        // Načtení poznámek z databáze
        loadNotes()

        // Přidání nové poznámky
        binding.fabAddNote.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun showAddNoteDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_note, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val contentEditText = dialogView.findViewById<EditText>(R.id.editTextContent)
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinnerCategory)

        lifecycleScope.launch {
            val categories = database.categoryDao().getAllCategories().first()
            val categoryNames = categories.map { it.name }
            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Přidat poznámku")
            .setView(dialogView)
            .setPositiveButton("Přidat") { _, _ ->
                val title = titleEditText.text.toString()
                val content = contentEditText.text.toString()
                val selectedCategory =
                    spinnerCategory.selectedItem.toString()

                // Najdeme ID vybrané kategorie
                lifecycleScope.launch {
                    val category = database.categoryDao().getCategoryByName(selectedCategory)
                    if (category != null) {
                        addNoteToDatabase(title, content, category.id)
                    }
                }
            }
            .setNegativeButton("Zrušit", null)
            .create()

        dialog.show()
    }

    private fun addNoteToDatabase(title: String, content: String, categoryId: Int) {
        lifecycleScope.launch {
            val newNote = Note(title = title, content = content, categoryId = categoryId)
            database.noteDao().insert(newNote)
            loadNotes()
        }
    }

    private fun updateNoteInDatabase(note: Note) {
        lifecycleScope.launch {
            database.noteDao().update(note)
            loadNotes()
        }
    }

    private fun loadNotes() {
        lifecycleScope.launch {
            var notes = if (currentCategory == "Vše") {
                database.noteDao().getAllNotes().first()
            } else {
                val category = database.categoryDao().getCategoryByName(currentCategory)
                if (category != null) {
                    database.noteDao().getNotesByCategoryId(category.id).first()
                } else {
                    emptyList()
                }
            }

            // Řazení podle názvu
            notes = if (isNameAscending) {
                notes.sortedBy { it.title?.lowercase() ?: "" }
            } else {
                notes.sortedByDescending { it.title?.lowercase() ?: "" }
            }

            // Aktualizace RecyclerView
            noteAdapter = NoteAdapter(
                notes = notes,
                onDeleteClick = { note -> deleteNote(note) },
                onEditClick = { note -> editNote(note) },
                lifecycleScope = lifecycleScope,
                database = database
            )
            binding.recyclerView.adapter = noteAdapter
        }
    }

    private fun deleteNote(note: Note) {
        lifecycleScope.launch {
            database.noteDao().delete(note)
            loadNotes()
        }
    }

    private fun editNote(note: Note) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_note, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val contentEditText = dialogView.findViewById<EditText>(R.id.editTextContent)
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinnerCategory)

        // Načtení kategorií do spinneru
        lifecycleScope.launch {
            val categories = database.categoryDao().getAllCategories().first()
            val categoryNames = categories.map { it.name }
            val categoryAdapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, categoryNames)
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = categoryAdapter
            val currentCategory = categories.find { it.id == note.categoryId }?.name
            val categoryPosition = categoryNames.indexOf(currentCategory)
            if (categoryPosition >= 0) {
                spinnerCategory.setSelection(categoryPosition)
            }
        }

        // Předvyplnění stávajících dat
        titleEditText.setText(note.title)
        contentEditText.setText(note.content)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Upravit poznámku")
            .setView(dialogView)
            .setPositiveButton("Uložit") { _, _ ->
                val title = titleEditText.text.toString()
                val content = contentEditText.text.toString()
                val selectedCategory =
                    spinnerCategory.selectedItem.toString()

                // Najdeme ID vybrané kategorie a aktualizujeme poznámku
                lifecycleScope.launch {
                    val category = database.categoryDao().getCategoryByName(selectedCategory)
                    if (category != null) {
                        val updatedNote = note.copy(
                            title = title,
                            content = content,
                            categoryId = category.id
                        )
                        updateNoteInDatabase(updatedNote)
                    }
                }
            }
            .setNegativeButton("Zrušit", null)
            .create()

        dialog.show()
    }

    private fun insertDefaultCategories() {
        lifecycleScope.launch {
            val defaultCategories = listOf(
                "Osobní",
                "Práce",
                "Nápady"
            )

            for (categoryName in defaultCategories) {
                val existingCategory = database.categoryDao().getCategoryByName(categoryName)
                if (existingCategory == null) {
                    database.categoryDao().insert(Category(name = categoryName))
                }
            }
        }
    }

    private fun setupUI() {
        setupFilterSpinner()
        setupSortButtons()
    }

    private fun setupFilterSpinner() {
        lifecycleScope.launch {
            val categories = database.categoryDao().getAllCategories().first()
            val categoryNames = categories.map { it.name }.toMutableList()
            categoryNames.add(0, "Vše")

            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerFilterCategory.adapter = adapter

            binding.spinnerFilterCategory.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        currentCategory = categoryNames[position]
                        loadNotes()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun setupSortButtons() {
        binding.btnSortByName.setOnClickListener {
            isNameAscending = !isNameAscending
            loadNotes()
        }
    }
}
