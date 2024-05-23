package com.mojapka.mykotlinapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mojapka.mykotlinapp.databinding.FragmentPierwszyBinding
import android.content.Context

class PierwszyFragment : Fragment() {
    private var _binding: FragmentPierwszyBinding? = null
    private val binding get() = _binding!!
    var pierwszyFragmentCallback: PierwszyListener? = null
    interface PierwszyListener {
        fun doFragmentu2(tekst: String)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            pierwszyFragmentCallback = context as PierwszyListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString()
                    + " trzeba zaimplementować PierwszyListener")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPierwszyBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.klawiszFa.setOnClickListener { v: View -> onButtonFaClicked(v) }
    }
    private fun onButtonFaClicked(view: View) {
        pierwszyFragmentCallback?.doFragmentu2(binding.editText.text.toString())
        binding.editText.setText(" ")    }

    fun zmienTekst(tekst: String) {
        binding.textFaName.text = tekst
    }

}
