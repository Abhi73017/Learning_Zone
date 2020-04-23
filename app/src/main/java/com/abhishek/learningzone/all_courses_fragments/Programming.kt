package com.abhishek.learningzone.all_courses_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.abhishek.learningzone.R
import com.abhishek.learningzone.courses.ActiveCourseViewActivity
import kotlinx.android.synthetic.main.fragment_technology.view.*


class Programming : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_technology, container, false)

        view.c_pro_active_btn.setOnClickListener {
            val course = "C Language"
            val intent = Intent(activity, ActiveCourseViewActivity::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }

        view.python_active_btn.setOnClickListener {
            val course: String = "Python"
            val intent = Intent(activity, ActiveCourseViewActivity::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }

        view.android_active_btn.setOnClickListener {
            val course: String = "Android"
            val intent = Intent(activity, ActiveCourseViewActivity::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }

        view.web_techno_btn.setOnClickListener {
            Toast.makeText(activity, "Will be Available soon", Toast.LENGTH_SHORT).show()
        }

        view.Cplusplus_active_btn.setOnClickListener {
            Toast.makeText(activity, "Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        view.ML_active_btn.setOnClickListener {
            Toast.makeText(activity, "Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        view.design_active_btn.setOnClickListener {
            Toast.makeText(activity, "Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        return view

    }
}