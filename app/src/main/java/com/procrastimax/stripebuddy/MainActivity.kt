package com.procrastimax.stripebuddy

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

const val MainActivityTAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_rgb)
        setSupportActionBar(findViewById(R.id.toolbar))

        val sliderFragment = RGBSliderFragment.newInstance()
        switchFragment(sliderFragment)
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val settingsFragment = supportFragmentManager.findFragmentByTag("settings")
                ?: SettingsFragment.newInstance()
            if (!settingsFragment.isAdded) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, settingsFragment, "settings")
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}