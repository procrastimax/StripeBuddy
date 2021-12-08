package com.procrastimax.stripebuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

const val MainActivityTAG = "MainActivity"

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_rgb)

        val sliderFragment = RGBSliderFragment.newInstance()
        val exactFragment = RGBExactFragment.newInstance()

        switchFragment(sliderFragment)
        val bottomNavView = this.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_slider -> {
                    switchFragment(sliderFragment)
                    true
                }
                R.id.item_exact -> {
                    switchFragment(exactFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }
}