package com.example.gamecompanionenti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title="MARIO KART"
        supportActionBar?.subtitle="Game Companion"
        supportActionBar?.setLogo(getDrawable(R.drawable.cliaprt))
        MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111")

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
