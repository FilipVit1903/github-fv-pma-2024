package com.example.myapp016a_christmasapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapp016a_christmasapp.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val questions = listOf(
        Question(
            text = "Kde se tradičně pečou perníčky?",
            options = listOf("V troubě", "Na grilu", "V mikrovlnce"),
            correctAnswer = 0
        ),
        Question(
            text = "Jakou barvu má Santa Clausův oblek?",
            options = listOf("Modrou", "Červenou", "Zelenou"),
            correctAnswer = 1
        ),
        Question(
            text = "Kdy slavíme Štědrý den?",
            options = listOf("24. prosince", "25. prosince", "31. prosince"),
            correctAnswer = 0
        )
    )

    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        setupQuestion()

        binding.btnNext.setOnClickListener {
            checkAnswer()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            binding.questionText.text = question.text
            binding.answersGroup.removeAllViews()

            question.options.forEachIndexed { index, option ->
                val radioButton = RadioButton(context)
                radioButton.text = option
                radioButton.id = index
                binding.answersGroup.addView(radioButton)
            }
        } else {
            showResults()
        }
    }

    private fun checkAnswer() {
        val selectedOption = binding.answersGroup.checkedRadioButtonId
        if (selectedOption == -1) {
            Toast.makeText(context, "Vyberte odpověď", Toast.LENGTH_SHORT).show()
            return
        }

        val correctAnswer = questions[currentQuestionIndex].correctAnswer
        if (selectedOption == correctAnswer) {
            correctAnswers++
        }

        currentQuestionIndex++
        setupQuestion()
    }

    private fun showResults() {
        Toast.makeText(
            context,
            "Správně jste odpověděli na $correctAnswers z ${questions.size} otázek!",
            Toast.LENGTH_LONG
        ).show()

        // Návrat na domovskou obrazovku
        activity?.onBackPressed()
    }

    data class Question(
        val text: String,
        val options: List<String>,
        val correctAnswer: Int
    )
}
