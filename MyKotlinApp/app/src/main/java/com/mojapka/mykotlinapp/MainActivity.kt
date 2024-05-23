package com.mojapka.mykotlinapp

import SortAlgorithm
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mojapka.mykotlinapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , View.OnClickListener,  PierwszyFragment.PierwszyListener, DrugiFragment.DrugiListener
     {

    lateinit var binding: ActivityMainBinding
    lateinit var sortAlgorithm: SortAlgorithm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        sortAlgorithm = SortAlgorithm()
        val countySpinner = findViewById<Spinner>(R.id.spinner1)
        binding.btnSort.setOnClickListener(this)
        val sortMethod = arrayListOf<String>().apply {
            add("Szybkie")
            add("Bąbelkowe")
        }
        val myAdp = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, sortMethod)
        countySpinner.adapter = myAdp

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sort -> {
                val inputText = binding.etA.text.toString().trim()
                //jeśli użytkownik nie wpisał liczb lub wpisał znaki
                if (inputText.isBlank() || !inputText.matches(Regex("^\\d+(\\s+\\d+)*$"))) {
                    binding.resultTv.text = "Wpisz poprawnie liczby"

                } else {
                    val sortData = binding.etA.text.toString()
                    val spinnerData = binding.spinner1.selectedItem.toString()
                    val explicitIntent = Intent(applicationContext, SecondActivity::class.java)
                    explicitIntent.putExtra("text_data", sortData)
                    explicitIntent.putExtra("spinner_data", spinnerData)
                    startActivity(explicitIntent)
                    }

            }
        }
    }

    override fun doFragmentu2(tekst: String) {
        var drugiFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2)
                as DrugiFragment
        drugiFragment.zmienTekst(tekst)
    }
         override fun doFragmentu1(tekst: String) {
             var pierwszyFragment =
                 supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as PierwszyFragment
             pierwszyFragment.zmienTekst(tekst)
         }



     }
