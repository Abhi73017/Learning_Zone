package com.abhishek.learningzone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                Handler().postDelayed({
                    startActivity(Intent(this,Login_Activity::class.java))
                    finish()
                },500)
            },2500)
    }
}
