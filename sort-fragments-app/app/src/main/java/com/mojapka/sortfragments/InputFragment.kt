package com.mojapka.sortfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.mojapka.sortfragments.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: OnSortButtonClickListener

    interface OnSortButtonClickListener {
        fun onSortButtonClick(numbers: String, method: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as OnSortButtonClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement InputFragment.OnSortButtonClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sorting_methods,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortingMethod.adapter = adapter

        binding.sortButton.setOnClickListener {
            val numbers = binding.numberInput.text.toString()
            val method = binding.sortingMethod.selectedItem.toString()
            listener.onSortButtonClick(numbers, method)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
