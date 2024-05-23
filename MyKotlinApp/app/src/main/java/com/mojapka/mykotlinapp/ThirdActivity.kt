package com.mojapka.mykotlinapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mojapka.mykotlinapp.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sortedData = intent.getStringExtra("sorted_data")
        binding.sortedDataTv.text = sortedData
        binding.shareButton.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, sortedData)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Udostępnij wynik sortowania"))
        }
    }
}
