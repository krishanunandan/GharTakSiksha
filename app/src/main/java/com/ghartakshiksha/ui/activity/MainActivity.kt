package com.ghartakshiksha.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ghartakshiksha.databinding.ActivityMainBinding
import com.ghartakshiksha.ui.adapter.SectionsPagerAdapter
import com.ghartakshiksha.utility.setStatusBarColor
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(Color.parseColor("#ED9030"))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            val tabNames = listOf("All", "FOR YOU")
            tab.text = tabNames[position]
        }.attach()


    }
}