package com.example.myapp016a_christmasapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var countdownTimer: TextView
    private lateinit var randomWish: TextView
    private lateinit var btnNextWish: Button
    private lateinit var btnQuiz: Button
    private lateinit var btnViewResults: Button

    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countdownTimer = findViewById(R.id.countdownTimer)
        randomWish = findViewById(R.id.randomWish)
        btnNextWish = findViewById(R.id.btnNextWish)
        btnQuiz = findViewById(R.id.btnQuiz)
        btnViewResults = findViewById(R.id.btnViewResults)

        startCountdown()
        randomWish.text = getRandomWish()

        btnNextWish.setOnClickListener {
            randomWish.text = getRandomWish()
        }

        btnQuiz.setOnClickListener {
            restartQuiz()
        }

        btnViewResults.setOnClickListener {
            showResultsPage()
        }
    }

    // Zobrazení otázky
    private val questions = listOf(
        Question("Kde se tradičně pečou perníčky?", listOf("V troubě", "Na grilu", "V mikrovlnce"), 0),
        Question("Jakou barvu má Santa Clausův oblek?", listOf("Modrou", "Červenou", "Zelenou"), 1),
        Question("Kdy slavíme Štědrý den?", listOf("24. prosince", "25. prosince", "31. prosince"), 0),
        Question("Kdo nosí dárky v Česku?", listOf("Ježíšek", "Santa Claus", "Děda Mráz"), 0),
        Question("Jaká ryba se tradičně jí na Štědrý den?", listOf("Treska", "Kapr", "Losos"), 1),
        Question("Co se tradičně lije o Vánocích pro věštění budoucnosti?", listOf("Vosk", "Olovo", "Zlatý písek"), 1)
    )

    // Restartování kvízu (správné resetování proměnných)
    private fun restartQuiz() {
        currentQuestionIndex = 0
        correctAnswers = 0
        showQuizPage()
    }

    // Zobrazení kvízové stránky
    private fun showQuizPage() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_quiz, null)
        val questionText: TextView = dialogView.findViewById(R.id.questionText)
        val answersGroup: RadioGroup = dialogView.findViewById(R.id.answersGroup)
        val btnNext: Button = dialogView.findViewById(R.id.btnNext)

        setupQuestion(questionText, answersGroup)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        btnNext.setOnClickListener {
            val selectedOption = answersGroup.checkedRadioButtonId
            if (selectedOption == -1) {
                Toast.makeText(this, "Vyberte odpověď", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedOption == questions[currentQuestionIndex].correctAnswer) {
                correctAnswers++
            }

            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                setupQuestion(questionText, answersGroup)
            } else {
                dialog.dismiss()
                showResultsDialog()
            }
        }

        dialog.show()
    }

    // Nastavení otázky a odpovědí
    private fun setupQuestion(questionText: TextView, answersGroup: RadioGroup) {
        if (currentQuestionIndex >= questions.size) return

        val question = questions[currentQuestionIndex]
        questionText.text = question.text
        answersGroup.removeAllViews()

        question.options.forEachIndexed { index, option ->
            val radioButton = RadioButton(this)
            radioButton.text = option
            radioButton.id = index
            radioButton.setTextColor(resources.getColor(android.R.color.white, theme))
            answersGroup.addView(radioButton)
        }
    }

    // Výsledek kvízu
    private fun showResultsDialog() {
        saveQuizResult(correctAnswers, questions.size)

        AlertDialog.Builder(this)
            .setTitle("Výsledek kvízu")
            .setMessage("Správně jste odpověděli na $correctAnswers z ${questions.size} otázek.")
            .setPositiveButton("Hrát znovu") { _, _ ->
                restartQuiz()
            }
            .setNegativeButton("Zavřít", null)
            .show()
    }

    // Historie výsledků
    private fun showResultsPage() {
        val results = loadQuizResults()
        val resultsText = results.joinToString("\n")

        AlertDialog.Builder(this)
            .setTitle("Historie výsledků")
            .setMessage(resultsText.ifEmpty { "Žádné výsledky" })
            .setPositiveButton("Zavřít", null)
            .show()
    }

    // Odpočet do Vánoc
    private fun startCountdown() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val currentTime = Calendar.getInstance()
                val christmas = Calendar.getInstance().apply {
                    set(Calendar.MONTH, Calendar.DECEMBER)
                    set(Calendar.DAY_OF_MONTH, 25)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                }

                if (currentTime.after(christmas)) {
                    christmas.add(Calendar.YEAR, 1)
                }

                val diff = christmas.timeInMillis - currentTime.timeInMillis
                val days = diff / (1000 * 60 * 60 * 24)

                runOnUiThread {
                    countdownTimer.text = "$days dní do Vánoc"
                }
            }
        }, 0, 1000)
    }

    // Náhodné vánoční přání
    private fun getRandomWish(): String {
        val wishes = listOf(
            "Veselé Vánoce a šťastný nový rok!",
            "Přejeme klidné a pohodové Vánoce!",
            "Užijte si svátky plné radosti a lásky!"
        )
        return wishes.random()
    }

    // Uložení výsledků do SharedPreferences
    private val PREFS_NAME = "quiz_results_prefs"
    private val RESULTS_KEY = "quiz_results"

    // Uložení výsledků do SharedPreferences
    private fun saveQuizResult(correct: Int, total: Int) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val results = sharedPreferences.getStringSet(RESULTS_KEY, mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        val dateFormat = SimpleDateFormat("d. MMMM yyyy", Locale("cs", "CZ"))
        val currentDate = dateFormat.format(Date())

        results.add("Správně: $correct z $total ($currentDate)")
        sharedPreferences.edit().putStringSet(RESULTS_KEY, results).apply()
    }

    // Načtení historie výsledků z SharedPreferences
    private fun loadQuizResults(): List<String> {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return sharedPreferences.getStringSet(RESULTS_KEY, emptySet())?.toList() ?: emptyList()
    }

    data class Question(val text: String, val options: List<String>, val correctAnswer: Int)
}
