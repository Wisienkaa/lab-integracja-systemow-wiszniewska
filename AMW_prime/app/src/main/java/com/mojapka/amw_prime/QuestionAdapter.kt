package com.mojapka.amw_prime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mojapka.amw_prime.databinding.ItemQuestionBinding
import com.mojapka.amw_prime.models.Question

class QuestionAdapter(
    private val questions: List<Question>,
    private val answers: MutableList<String>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(private val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question, position: Int) {
            binding.textViewQuestion.text = question.questionText
            binding.checkBoxA.text = question.optionA
            binding.checkBoxB.text = question.optionB
            binding.checkBoxC.text = question.optionC
            binding.checkBoxD.text = question.optionD

            binding.checkBoxA.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    answers[position] = "A"
                    binding.checkBoxB.isChecked = false
                    binding.checkBoxC.isChecked = false
                    binding.checkBoxD.isChecked = false
                } else if (answers[position] == "A") {
                    answers[position] = ""
                }
            }

            binding.checkBoxB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    answers[position] = "B"
                    binding.checkBoxA.isChecked = false
                    binding.checkBoxC.isChecked = false
                    binding.checkBoxD.isChecked = false
                } else if (answers[position] == "B") {
                    answers[position] = ""
                }
            }

            binding.checkBoxC.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    answers[position] = "C"
                    binding.checkBoxA.isChecked = false
                    binding.checkBoxB.isChecked = false
                    binding.checkBoxD.isChecked = false
                } else if (answers[position] == "C") {
                    answers[position] = ""
                }
            }

            binding.checkBoxD.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    answers[position] = "D"
                    binding.checkBoxA.isChecked = false
                    binding.checkBoxB.isChecked = false
                    binding.checkBoxC.isChecked = false
                } else if (answers[position] == "D") {
                    answers[position] = ""
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position], position)
    }

    override fun getItemCount() = questions.size
}
