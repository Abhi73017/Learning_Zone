package com.abhishek.learningzone.courses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.abhishek.learningzone.R
import com.abhishek.learningzone.Student.S_notes_view
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_active_courses_view.*

class ActiveCourseViewActitity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_courses_view)

        val course  = intent.getStringExtra("course")

        course_header.text = course.toString()

        course_read_notes_btn.setOnClickListener {
            Intent(this, S_notes_view::class.java).apply {
                putExtra("course",course)
                startActivity(this)
            }
        }

        course_solve_assign_btn.setOnClickListener{
//            Intent(this, ActiveCourseViewActitity::class.java).apply {
//                putExtra("course",course)
//                startActivity(this)
//            }
            ShowSnack(it,"Available After 7 days")

        }

        course_faq.setOnClickListener {
//            Intent(this, ActiveCourseViewActitity::class.java).apply {
//                putExtra("course",course)
//                startActivity(this)
//            }
            ShowSnack(it,"Available After 7 days")

        }


        course_doubts.setOnClickListener {
//            Intent(this, ActiveCourseViewActitity::class.java).apply {
//                putExtra("course",course)
//                startActivity(this)
//            }
            ShowSnack(it,"Doubts Are Running On Our respected Whatsapp Groups")
        }

    }

    fun ShowSnack(view:View,msg:String){

        Snackbar.make(view,msg,Snackbar.LENGTH_LONG).apply {
            setAction("ok"){
                this.dismiss()
            }
            show()
        }

    }

}
