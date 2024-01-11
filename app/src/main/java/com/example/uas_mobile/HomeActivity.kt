package com.example.uas_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        loadFragment(HomeFragment())

        bottomNav = findViewById(R.id.bottomNavigationView)

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.bottom_catalog -> {
                    loadFragment(CatalogFragment())
                    true
                }
                R.id.bottom_history -> {
                    loadFragment(HistoryFragment())
                    true
                }
                R.id.bottom_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(
            R.anim.slide_in_right,  // animasi masuk
            R.anim.slide_out_left, // animasi keluar
            R.anim.slide_in_right,  // animasi masuk
            R.anim.slide_out_left // animasi keluar
        )

        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
