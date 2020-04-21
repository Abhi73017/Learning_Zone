package com.abhishek.learningzone.Student

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.R
import com.abhishek.learningzone.courses.C_pro
import kotlinx.android.synthetic.main.activity_active_courses.*

class active_courses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_courses)

        c_pro_active_btn.setOnClickListener {
            val intent = Intent(this, C_pro::class.java)
            startActivity(intent)
        }
    }
}