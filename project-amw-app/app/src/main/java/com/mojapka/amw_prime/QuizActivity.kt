package com.mojapka.amw_prime

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mojapka.amw_prime.database.AppDatabase
import com.mojapka.amw_prime.databinding.ActivityQuizBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val answers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        binding.recyclerViewQuestions.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch {
            val dao = AppDatabase.getDatabase(applicationContext).questionDao()
            val questions = dao.getAllQuestions()

            runOnUiThread {
                answers.clear()
                answers.addAll(List(questions.size) { "" })
                binding.recyclerViewQuestions.adapter = QuestionAdapter(questions, answers)
            }
        }

        binding.buttonSubmit.setOnClickListener {
            val mostSelectedAnswer = getMostSelectedAnswer(answers)
            val resultText = "Twój wydział to z pewnością $mostSelectedAnswer!"
            Toast.makeText(this, resultText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMostSelectedAnswer(answers: List<String>): String {
        val answerCounts = mutableMapOf<String, Int>()

        for (answer in answers) {
            if (answer.isNotEmpty()) {
                answerCounts[answer] = answerCounts.getOrDefault(answer, 0) + 1
            }
        }

        val mostSelectedAnswer = answerCounts.maxByOrNull { it.value }?.key

        return when (mostSelectedAnswer) {
            "A" -> "Wydział Dowodzenia i Operacji Morskich"
            "B" -> "Wydział Mechaniczno-Elektryczny"
            "C" -> "Wydział Nauk Humanistycznych i Społecznych"
            "D" -> "Wydział Nawigacji i Uzbrojenia Okrętowego"
            else -> "Brak odpowiedzi"
        }
    }
}
