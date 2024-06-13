package com.mojapka.amw_prime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mojapka.amw_prime.databinding.FragmentRecruitmentBinding
import android.text.Html
import androidx.lifecycle.lifecycleScope
import android.widget.Toast
import kotlinx.coroutines.launch
import com.mojapka.amw_prime.database.AppDatabase
import com.mojapka.amw_prime.models.Recruitment
import com.mojapka.amw_prime.models.Question
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RecruitmentFragment : Fragment() {
    private var _binding: FragmentRecruitmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecruitmentBinding.inflate(inflater, container, false)
        binding.btnSubmit.setOnClickListener {
            if (validateForm()) {
                sendForm()
            }
        }
        return binding.root
    }

    private fun validateForm(): Boolean {
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            val acceptTerms = binding.cbAcceptTerms.isChecked

            if (firstName.isEmpty()) {
                binding.etFirstName.error = "Imię jest wymagane"
                return false
            }

            if (lastName.isEmpty()) {
                binding.etLastName.error = "Nazwisko jest wymagane"
                return false
            }

            if (email.isEmpty()) {
                binding.etEmail.error = "E-mail jest wymagany"
                return false
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Hasło jest wymagane"
                return false
            }

            if (!isValidPassword(password)) {
                binding.etPassword.error = "Hasło musi mieć minimum 8 znaków, małą i dużą literę, cyfrę oraz znak specjalny"
                return false
            }

            if (confirmPassword.isEmpty()) {
                binding.etConfirmPassword.error = "Potwierdzenie hasła jest wymagane"
                return false
            }

            if (password != confirmPassword) {
                binding.etConfirmPassword.error = "Hasła się nie zgadzają"
                return false
            }

            if (!acceptTerms) {
                binding.cbAcceptTerms.error = "Musisz zaakceptować klauzulę informacyjną"
                return false
            }

            return true
        }

        private fun isValidPassword(password: String): Boolean {
                val passwordPattern = Regex(
                    pattern = "^" +
                            "(?=.*[0-9])" +         // co najmniej jedna cyfra
                            "(?=.*[a-z])" +         // co najmniej jedna mała litera
                            "(?=.*[A-Z])" +         // co najmniej jedna duża litera
                            "(?=.*[@#\$%^&+=!])" +  // co najmniej jeden znak specjalny
                            "(?=\\S+\$)" +          // bez spacji
                            ".{8,}" +               // co najmniej 8 znaków
                            "\$"
                )
                return passwordPattern.matches(password)
        }



    private fun sendForm() {
        val name = binding.etFirstName.text.toString()
        val surname = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.toString()


        val recruitment = Recruitment(name = name, surname = surname, email = email, password = password)
        saveRecruitmentData(recruitment)
    }

    private fun saveRecruitmentData(recruitment: Recruitment) {
        val db = AppDatabase.getDatabase(requireContext())
        val recruitmentDao = db.recruitmentDao()

        lifecycleScope.launch {
            recruitmentDao.insert(recruitment)
            Toast.makeText(context, "Form Submitted", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPrivacyPolicy.text = Html.fromHtml(PrivacyPolicy.text, Html.FROM_HTML_MODE_LEGACY)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
