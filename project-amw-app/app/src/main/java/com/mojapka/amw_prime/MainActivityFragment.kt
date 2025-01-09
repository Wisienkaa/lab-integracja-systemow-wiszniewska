package com.mojapka.amw_prime

import DepartmentsFragment
import MainFragment
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.google.android.material.tabs.TabLayout
import com.mojapka.amw_prime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var frameLayout: FrameLayout
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        frameLayout = binding.framelayout
        tabLayout = binding.tablayout

        // Dodanie zakładek
        tabLayout.addTab(tabLayout.newTab().setText("Wydziały"))
        tabLayout.addTab(tabLayout.newTab().setText("Rekrutacja"))
        tabLayout.addTab(tabLayout.newTab().setText("Quiz"))
        tabLayout.addTab(tabLayout.newTab().setText("Kontakt"))

        // Ustawienie domyślnego fragmentu
        replaceFragment(MainFragment())

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment: Fragment = when (tab.position) {
                    0 -> DepartmentsFragment()
                    1 -> RecruitmentFragment()
                    2 -> QuizFragment()
                    3 -> Contact()
                    else -> MainFragment()
                }
                replaceFragment(fragment)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout, fragment)
        fragmentTransaction.commit()
    }
}
