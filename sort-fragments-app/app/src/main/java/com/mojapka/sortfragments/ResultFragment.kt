package com.mojapka.sortfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mojapka.sortfragments.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val sortAlgorithm = SortAlgorithm()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    fun sortAndDisplayNumbers(numbers: String, method: String) {
        sortAlgorithm.setNumbers(numbers)
        val sortedNumbers = when (method) {
            "Szybkie" -> {
                val description = "Sortowanie szybkie (quicksort)"
                val description2 = resources.getString(R.string.quick)
                val sortedResult = sortAlgorithm.quickSort()
                displaySortedNumbers(description, description2, sortedResult, numbers)
            }
            "Bąbelkowe" -> {
                val description = "Sortowanie bąbelkowe (bubblesort)"
                val description2 = resources.getString(R.string.bubble)
                val sortedResult = sortAlgorithm.bubbleSort()
                displaySortedNumbers(description, description2, sortedResult, numbers)
            }
            else -> {
                "Metoda sortowania nieznana"
            }
        }
    }

    private fun displaySortedNumbers(description: String, description2:String, sortedResult: String, originalNumbers: String) {
        binding.sortingDescription.text = description
        binding.sortingDescription2.text = description2
        val originalListText = "Lista przed sortowaniem:\n$originalNumbers"
        val sortedListText = "Posortowana lista:\n$sortedResult"
        val finalText = "$originalListText\n\n$sortedListText"
        binding.sortedNumbers.text = finalText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
