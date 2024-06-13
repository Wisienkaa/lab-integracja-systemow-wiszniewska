package com.mojapka.amw_prime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mojapka.amw_prime.databinding.ActivityDepartmentBinding

class DepartmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDepartmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val departmentName = intent.getStringExtra("departmentName")
        val description = intent.getStringExtra("description")

        binding.departmentNameTextView.text = departmentName
        binding.descriptionTextView.text = description

        binding.backButton.setOnClickListener {
            finish() // Zamyka aktywność, wracając do poprzedniej aktywności na stosie
        }
    }
}
