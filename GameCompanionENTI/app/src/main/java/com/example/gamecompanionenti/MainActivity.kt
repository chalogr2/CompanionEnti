package com.example.gamecompanionenti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            // TODO: Go to the correct screen
            when (item.itemId) {
                R.id.menu_chat -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val fragment = SupportFragment()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.commit()
                    val context = this
                    if(FirebaseAuth.getInstance().currentUser == null) {
                        startActivity(Intent(context, Signup::class.java))
                    }
                }
                R.id.menu_news -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val fragment = HomeFragment()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.commit()
                }
                R.id.menu_profile -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val fragment = ProfileFragment()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.commit()
                    val context = this
                    if(FirebaseAuth.getInstance().currentUser == null) {
                        startActivity(Intent(context, Signup::class.java))
                    }
                }
            }

            true
        }
        bottom_navigation.selectedItemId = R.id.menu_news

    }
}
