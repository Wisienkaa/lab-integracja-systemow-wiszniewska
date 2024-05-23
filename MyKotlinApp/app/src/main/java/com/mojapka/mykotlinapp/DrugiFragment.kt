package com.mojapka.mykotlinapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mojapka.mykotlinapp.databinding.FragmentDrugiBinding
import android.content.Context

class DrugiFragment : Fragment() {
    private var _binding: FragmentDrugiBinding? = null
    private val binding get() = _binding!!
    var drugiFragmentCallback: DrugiListener? = null
    interface DrugiListener {
        fun doFragmentu1(tekst: String)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            drugiFragmentCallback = context as DrugiListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString()
                    + " trzeba zaimplementować DrugiListener")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDrugiBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.klawiszFa.setOnClickListener { v: View -> onButtonFbClicked(v) }
    }
    private fun onButtonFbClicked(view: View) {
        drugiFragmentCallback?.doFragmentu1(binding.editText.text.toString())
        binding.editText.setText(" ")
    }

    fun zmienTekst(tekst: String) {
        binding.textFaName.text = tekst
    }
}
