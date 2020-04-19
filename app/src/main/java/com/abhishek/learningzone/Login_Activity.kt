package com.abhishek.learningzone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhishek.learningzone.teacher.UploadActivity
import kotlinx.android.synthetic.main.activity_login.*

class Login_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button.setOnClickListener {
            startActivity( Intent(this, DashBoard::class.java) )

        }

        testbtn.setOnClickListener{
            startActivity(Intent(this,UploadActivity::class.java))
        }
    }
}