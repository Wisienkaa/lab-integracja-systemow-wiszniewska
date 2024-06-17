package com.mojapka.sortfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mojapka.sortfragments.ResultFragment
import com.mojapka.sortfragments.InputFragment
import com.mojapka.sortfragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), InputFragment.OnSortButtonClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var resultFragment: ResultFragment
    private lateinit var inputFragment: InputFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultFragment = ResultFragment()
        inputFragment = InputFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.resultFragmentContainer.id, resultFragment)
                .replace(binding.inputFragmentContainer.id, inputFragment)
                .commit()
        }
    }

    override fun onSortButtonClick(numbers: String, method: String) {
        resultFragment.sortAndDisplayNumbers(numbers, method)
    }
}