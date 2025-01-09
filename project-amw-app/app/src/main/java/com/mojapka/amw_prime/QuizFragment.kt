package com.mojapka.amw_prime

import android.widget.Button
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class QuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        val startQuizButton: Button = view.findViewById(R.id.startQuizButton)

        startQuizButton.setOnClickListener {
            val intent = Intent(requireActivity(), QuizActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
