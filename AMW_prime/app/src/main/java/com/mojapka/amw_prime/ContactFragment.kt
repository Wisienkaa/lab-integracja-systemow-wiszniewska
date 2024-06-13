package com.mojapka.amw_prime

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mojapka.amw_prime.databinding.FragmentContactBinding

class Contact : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val view = binding.root
        val imageView = binding.ivLogo

        // Rozpocznij animację pojawiania się
        imageView.visibility = View.VISIBLE
        imageView.animate().alpha(1.0f).setDuration(1000).start()
        // Ustawienie danych kontaktowych
        binding.contactDetails.text = getString(R.string.contact_details)

        // Obsługa kliknięcia przycisku
        binding.contactEmailButton.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:rzecznik@amw.gdynia.pl")
                putExtra(Intent.EXTRA_SUBJECT, "Kontakt z aplikacji")
            }
            startActivity(Intent.createChooser(emailIntent, "Wyślij email..."))
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
