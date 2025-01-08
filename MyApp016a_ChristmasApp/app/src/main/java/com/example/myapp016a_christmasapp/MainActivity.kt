package com.example.myapp016a_christmasapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp016a_christmasapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var container: View
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        container = binding.container
        showHomePage()
    }

    //Otázky pro kvíz
    private val questions = listOf(
        Question("Kde se tradičně pečou perníčky?", listOf("V troubě", "Na grilu", "V mikrovlnce"), 0),
        Question("Jakou barvu má Santa Clausův oblek?", listOf("Modrou", "Červenou", "Zelenou"), 1),
        Question("Kdy slavíme Štědrý den?", listOf("24. prosince", "25. prosince", "31. prosince"), 0),
        Question("Kdo tradičně nosí dárky v České republice?", listOf("Ježíšek", "Santa Claus", "Děda Mráz"), 0),
        Question("Který den se tradičně zdobí vánoční stromeček v Česku?", listOf("24. prosince", "6. prosince", "1. prosince"), 0),
        Question("Jaký druh ryby je tradičně servírován na Štědrý den?", listOf("Treska", "Kapr", "Losos"), 1),
        Question("Co se tradičně lije během českých Vánoc pro věštění budoucnosti?", listOf("Vosk", "Olovo", "Zlatý písek"), 1)
    )

    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    //Homepage (nemáme jako activity_main)
    private fun showHomePage() {
        val inflater = LayoutInflater.from(this)
        val homeView = inflater.inflate(R.layout.homepage, null)
        val countdownTimer: TextView = homeView.findViewById(R.id.countdownTimer)
        val randomWish: TextView = homeView.findViewById(R.id.randomWish)
        val btnNextWish: Button = homeView.findViewById(R.id.btnNextWish)
        val btnQuiz: Button = homeView.findViewById(R.id.btnQuiz)
        val btnViewResults: Button = homeView.findViewById(R.id.btnViewResults)

        // Odpočet
        startCountdown(countdownTimer)

        // Náhodné přání
        randomWish.text = getRandomWish()

        //Další přání
        btnNextWish.setOnClickListener {
            randomWish.text = getRandomWish()
        }

        // Přechod na kvíz
        btnQuiz.setOnClickListener {
            showQuizPage()
        }

        // Přechod na zobrazení výsledků
        btnViewResults.setOnClickListener {
            showResultsPage()
        }


        replaceView(homeView)
    }

    //Start kvízu
    private fun showQuizPage() {
        val inflater = LayoutInflater.from(this)
        val quizView = inflater.inflate(R.layout.fragment_quiz, null)

        val questionText: TextView = quizView.findViewById(R.id.questionText)
        val answersGroup: RadioGroup = quizView.findViewById(R.id.answersGroup)
        val btnNext: Button = quizView.findViewById(R.id.btnNext)

        setupQuestion(questionText, answersGroup)

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
                showResultsDialog()
            }
        }

        replaceView(quizView)
    }

    //Výsledek kvízu
    private fun showResultsDialog() {
        saveQuizResult(correctAnswers, questions.size)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Výsledek kvízu")
        builder.setMessage("Správně jste odpověděli na $correctAnswers z ${questions.size} otázek.")

        builder.setPositiveButton("Hrát znovu") { _, _ ->
            currentQuestionIndex = 0
            correctAnswers = 0
            showQuizPage()
        }

        builder.setNegativeButton("Zpět na úvodní stránku") { _, _ ->
            showHomePage()
        }

        val dialog = builder.create()
        dialog.show()
    }

    //Nastavení otázky
    private fun setupQuestion(questionText: TextView, answersGroup: RadioGroup) {
        val question = questions[currentQuestionIndex]
        questionText.text = question.text
        answersGroup.removeAllViews()

        question.options.forEachIndexed { index, option ->
            val radioButton = RadioButton(this)
            radioButton.text = option
            radioButton.id = index

            // Změna barvy textu
            radioButton.setTextColor(resources.getColor(android.R.color.white, theme))

            answersGroup.addView(radioButton)
        }
    }

    // Konfigurace preferencí
    private val PREFS_NAME = "quiz_results_prefs"
    private val RESULTS_KEY = "quiz_results"

    // Uložení výsledků
    private fun saveQuizResult(correct: Int, total: Int) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val results = sharedPreferences.getStringSet(RESULTS_KEY, mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        // Formátování data s českou lokalizací
        val dateFormat = SimpleDateFormat("d. MMMM yyyy", Locale("cs", "CZ"))
        val currentDate = dateFormat.format(Date())

        // Formátovaný výsledek
        val newResult = "Správně: $correct z $total (dokončeno $currentDate)"
        results.add(newResult)

        sharedPreferences.edit().putStringSet(RESULTS_KEY, results).apply()
    }

    // Načtení výsledků
    private fun loadQuizResults(): List<String> {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return sharedPreferences.getStringSet(RESULTS_KEY, emptySet())?.toList() ?: emptyList()
    }

    // Zobrazení výsledků
    private fun showResultsPage() {
        val inflater = LayoutInflater.from(this)
        val resultsView = inflater.inflate(R.layout.results, null)

        val resultsList: LinearLayout = resultsView.findViewById(R.id.resultsList)
        val btnBackToHome: Button = resultsView.findViewById(R.id.btnBackToHome)

        // Načtení výsledků
        val results = loadQuizResults()

        // Dynamické zobrazení výsledků
        results.forEach { result ->
            val textView = TextView(this)
            textView.text = result
            textView.setPadding(16, 8, 16, 8)
            textView.setTextColor(resources.getColor(android.R.color.white, theme))
            resultsList.addView(textView)
        }

        // Návrat na úvodní stránku
        btnBackToHome.setOnClickListener {
            showHomePage()
        }

        replaceView(resultsView)
    }

    //Odpočet
    private fun startCountdown(countdownText: TextView) {
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
                val hours = (diff / (1000 * 60 * 60)) % 24
                val minutes = (diff / (1000 * 60)) % 60
                val seconds = (diff / 1000) % 60

                val countdownTextStr = "$days dní, $hours hodin, $minutes minut, $seconds sekund"
                runOnUiThread {
                    countdownText.text = countdownTextStr
                }
            }
        }, 0, 1000)
    }

    //Získání náhodného přání
    private fun getRandomWish(): String {
        val wishes = listOf(
            "Veselé Vánoce a šťastný nový rok!",
            "Přejeme klidné a pohodové Vánoce!",
            "Užijte si svátky plné radosti a lásky!",
            "Šťastné a veselé Vánoce přejeme!"
        )
        return wishes.random()


    }

    // Nahrazení zobrazeného layoutu
    private fun replaceView(newView: View) {
        val container = findViewById<FrameLayout>(R.id.container)
        container.removeAllViews()
        container.addView(newView)
    }

    data class Question(val text: String, val options: List<String>, val correctAnswer: Int)
}
