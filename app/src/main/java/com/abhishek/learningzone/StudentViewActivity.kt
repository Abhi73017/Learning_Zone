package com.abhishek.learningzone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.learningzone.model.couseItems
import com.abhishek.learningzone.student_adapter.courseAdapter
import com.google.firebase.storage.FirebaseStorage
import com.squareup.okhttp.Dispatcher
import kotlinx.android.synthetic.main.activity_student_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StudentViewActivity : AppCompatActivity() {
    var CourseList :MutableList<couseItems> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_view)



        val ref = FirebaseStorage.getInstance().reference.child("Python")
        ref.listAll().addOnSuccessListener { listResult ->
            listResult.items.forEach { item ->

                val name = item.name
                var dlurl  = ""
//              CourseList.add(couseItems(name.toString(),  "item.downladoksd"))
                item.downloadUrl.addOnSuccessListener {

                    dlurl= it.toString()

                }
                CourseList.add(couseItems(name, dlurl))
                println(dlurl)


            }

            rcv_call()


        }



        // RecyclerView





    }


    fun rcv_call(){
        println(CourseList)
        val adapter = courseAdapter(CourseList)
        rv_CourseList.adapter = adapter
        rv_CourseList.layoutManager = LinearLayoutManager(this@StudentViewActivity)
    }



    }

