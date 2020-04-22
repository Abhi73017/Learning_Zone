package com.abhishek.learningzone.Student

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.learningzone.R
import com.abhishek.learningzone.model.DatabaseCourse
import com.abhishek.learningzone.model.couseItems
import com.abhishek.learningzone.student_adapter.courseAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_s_notes_view.*


class S_notes_view : AppCompatActivity() {

    var CourseList: MutableList<couseItems> = mutableListOf()
    var datacourse: MutableList<DatabaseCourse> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_notes_view)
        val intentCourse = intent.getStringExtra("course") ?: return


        FirebaseDatabase.getInstance().getReference("Course/$intentCourse").apply {

            addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(
                        this@S_notes_view,
                        "Database process Cancelled",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDataChange(data: DataSnapshot) {

                    if (data.exists()) {

                        for (item in data.children) {
                            val DATA = item.getValue(DatabaseCourse::class.java)
                            datacourse.add(DATA!!)
                        }
                        if (course_loading_progress.visibility == View.VISIBLE) {
                            course_loading_progress.visibility = View.GONE
                        }
                        rcvCall(datacourse)
                    } else {
                        if (course_loading_progress.visibility == View.VISIBLE) {
                            course_loading_progress.visibility = View.GONE
                        }
                        Toast.makeText(this@S_notes_view, "No Data Avalible", Toast.LENGTH_LONG)
                            .show()
                    }

                }
            })
        }
//        rcv_call(datacourse)
    }


    fun rcvCall(datacourse: MutableList<DatabaseCourse>) {

        val adapter = courseAdapter(datacourse)
        rv_CourseList.adapter = adapter
        rv_CourseList.layoutManager = LinearLayoutManager(this@S_notes_view)
    }

}