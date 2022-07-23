package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.model.CartItemModel
import com.example.myapplication.view.fragments.FragmentCart
import com.example.myapplication.view.fragments.FragmentMenu
import com.example.myapplication.view.fragments.FragmentTransaction
import com.example.myapplication.view_model.LandingPageViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView

class LandingActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var rootFragment: Fragment

    lateinit var landingPageViewModel: LandingPageViewModel

    lateinit var tVuserName: TextView

    companion object {
        var cartItem = HashMap<Int, CartItemModel>()
        lateinit var userName: String
        var userType: Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navView)

        var headerView =
            LayoutInflater.from(this).inflate(R.layout.nav_heading, navView, true)

        tVuserName = headerView.findViewById(R.id.tvNavUserName)
        tVuserName.text = LoginActivity.userModel.name

        tVuserName = headerView.findViewById(R.id.tvUserType)
        tVuserName.text = if (LoginActivity.userModel.type == 1) "server" else "manager"


        var userProfile: ShapeableImageView = headerView.findViewById(R.id.userProfile)
        userProfile.setImageResource(if (LoginActivity.userModel!!.type == 1) R.drawable.user1 else R.drawable.user2)

        toggle =
            ActionBarDrawerToggle(this@LandingActivity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceFragment(FragmentMenu(), "Menu")

        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_menus -> replaceFragment(FragmentMenu(), "Menu")
                R.id.menu_transaction -> replaceFragment(FragmentTransaction(), "Transactions")
                R.id.menu_cart -> replaceFragment(FragmentCart(), "Cart")
                R.id.menu_logout -> startActivity(
                    Intent(
                        this@LandingActivity,
                        LoginActivity::class.java
                    )
                )
            }
            true
        }


        landingPageViewModel = ViewModelProvider(
            this@LandingActivity,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LandingPageViewModel::class.java)

    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentRoot, fragment).commit()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return false
    }

}