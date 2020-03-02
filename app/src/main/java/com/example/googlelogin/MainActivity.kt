package com.example.googlelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btngooglelogin.setOnClickListener {
            startActivity(Intent(this,GoogleLoginActivity::class.java))
        }
        btnfacebooklogin.setOnClickListener {
            startActivity(Intent(this,FacebookLoginActivity::class.java))
        }

        btnFirebaseotp.setOnClickListener {
            startActivity(Intent(this,FirebaseOtp::class.java))
        }

    }
}
