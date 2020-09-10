package ru.zzemlyanaya.edocument.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import ru.zzemlyanaya.edocument.R
import ru.zzemlyanaya.edocument.databinding.ActivityMainBinding
import ru.zzemlyanaya.edocument.main.home.HomeFragment
import ru.zzemlyanaya.edocument.main.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.navView.apply {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_home -> showHomeFragment()
                    R.id.nav_settings -> showSettingsFragment()
                }
                return@setOnNavigationItemSelectedListener true
            }
        }

        showHomeFragment()
    }

    private fun showHomeFragment() {
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.containerMain, HomeFragment(), "home")
            .commitAllowingStateLoss()
    }

    private fun showSettingsFragment(){
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.containerMain, SettingsFragment(), "settings")
            .commitAllowingStateLoss()
    }



}