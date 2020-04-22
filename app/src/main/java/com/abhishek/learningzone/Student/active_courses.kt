package com.abhishek.learningzone.Student

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.R
import com.abhishek.learningzone.courses.active_courses_view
import kotlinx.android.synthetic.main.activity_active_courses.*

class active_courses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_courses)

        c_pro_active_btn.setOnClickListener {
            val course : String = "C Language"
            val intent = Intent(this, active_courses_view::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }

        python_active_btn.setOnClickListener {
            val course : String = "Python"
            val intent = Intent(this, active_courses_view::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }

        android_active_btn.setOnClickListener {
            val course : String = "Android"
            val intent = Intent(this, active_courses_view::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }
    }
}