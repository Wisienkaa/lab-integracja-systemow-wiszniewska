package com.mojapka.mykotlinapp
import SortAlgorithm
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mojapka.mykotlinapp.databinding.ActivityMainBinding
import com.mojapka.mykotlinapp.databinding.ActivitySecondBinding
import kotlin.system.measureTimeMillis
import android.content.Intent

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding
    lateinit var sortAlgorithm: SortAlgorithm

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val sortData = intent.getStringExtra("text_data").toString()
        val spinnerData = intent.getStringExtra("spinner_data")
        sortAlgorithm = SortAlgorithm()

        binding.startTv.text= "Posortowany ciąg: $sortData\nDane z Spinner: $spinnerData"


            binding.resultTv.text = sortData
            val timeInMillis = measureTimeMillis {
                val sortedValues = when (spinnerData) {
                    "Szybkie" ->
                    {
                        binding.descriptionTv.text = "Sortowanie szybkie (quicksort) to efektywny algorytm sortowania, który stosuje strategię dziel i zwyciężaj."
                        sortAlgorithm.quickSort(sortData)
                    }
                    "Bąbelkowe" ->
                    {
                        binding.descriptionTv.text = "Sortowanie bąbelkowe (bubblesort) to prosty algorytm sortowania, który iteruje po zestawie danych wielokrotnie, porównując każde dwa sąsiednie elementy i zamieniając je miejscami, jeśli są w niewłaściwej kolejności."
                        sortAlgorithm.bubbleSort(sortData)
                    }
                    else -> sortData
                }
                binding.resultTv.text = "Posortowane wartości: $sortedValues"
                binding.startTv.text = "Początkowy ciąg: $sortData"

                // Dodanie OnClickListener dla przycisku do przejścia do ThirdActivity

                binding.shareButton.setOnClickListener {
                    val shareIntent = Intent(this, ThirdActivity::class.java).apply {
                        putExtra("sorted_data", sortedValues)
                    }
                    startActivity(shareIntent)
                }

            }
            binding.timeTv.text = "Czas sortowania: $timeInMillis ms"


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}