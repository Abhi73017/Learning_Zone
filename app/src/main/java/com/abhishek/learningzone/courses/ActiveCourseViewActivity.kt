package com.abhishek.learningzone.courses

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.R
import com.abhishek.learningzone.Student.S_notes_view
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_active_courses_view.*

class ActiveCourseViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_courses_view)

        val course = intent.getStringExtra("course")

        course_header.text = course?.toString()

        course_read_notes_btn.setOnClickListener {
            Intent(this, S_notes_view::class.java).apply {
                putExtra("course", course)
                startActivity(this)
            }
        }

        course_solve_assign_btn.setOnClickListener {
//            Intent(this, ActiveCourseViewActivity::class.java).apply {
//                putExtra("course",course)
//                startActivity(this)
//            }
            showSnack(it, "Available After 7 days")

        }

        course_faq.setOnClickListener {
//            Intent(this, ActiveCourseViewActivity::class.java).apply {
//                putExtra("course",course)
//                startActivity(this)
//            }
            showSnack(it, "Available After 7 days")

        }


        course_doubts.setOnClickListener {
//            Intent(this, ActiveCourseViewActivity::class.java).apply {
//                putExtra("course",course)
//                startActivity(this)
//            }
            showSnack(it, "Currently, Doubts session is running in respected WhatsApp Groups")
        }

    }

    private fun showSnack(view: View, msg: String) {

        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).apply {
            setAction("ok") {
                this.dismiss()
            }
            show()
        }

    }

}
