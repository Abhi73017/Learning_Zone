package com.abhishek.learningzone.Student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.R
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.learningzone.model.DatabaseCourse
import com.abhishek.learningzone.model.couseItems
import com.abhishek.learningzone.student_adapter.courseAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.okhttp.Dispatcher
import kotlinx.android.synthetic.main.activity_s_notes_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception


class S_notes_view : AppCompatActivity() {

    var CourseList: MutableList<couseItems> = mutableListOf()
    var datacourse: MutableList<DatabaseCourse> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_notes_view)


        FirebaseDatabase.getInstance().getReference("Course/Android").apply {

            addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(data: DataSnapshot) {

                    if (data.exists()){
                        println(data.childrenCount)

                        for (item in data.children){
                            var DATA = item.getValue(DatabaseCourse::class.java)
                            datacourse.add(DATA!!)
                        }
                        rcv_call(datacourse)
                    }
                }
            })
        }
//        rcv_call(datacourse)
    }


    fun rcv_call(datacourse: MutableList<DatabaseCourse>) {
        println(CourseList)
        val adapter = courseAdapter(datacourse)
        rv_CourseList.adapter = adapter
        rv_CourseList.layoutManager = LinearLayoutManager(this@S_notes_view)
    }

}