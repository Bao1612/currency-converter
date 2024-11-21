package com.example.currency_converter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.currency_converter.R
import com.example.currency_converter.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)


        binding.navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolBar, R.string.open_nav, R.string.close_nav)
        toggle.syncState()


        if(savedInstanceState == null) {
            rePlaceFragment(PairCurrency())
            binding.navView.setCheckedItem(R.id.pairCurrency)
        }


    }

    private fun rePlaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_container, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_pair_currency -> rePlaceFragment(PairCurrency())
            R.id.nav_latest_currency -> rePlaceFragment(LatestCurrencyFragment())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}
