package com.abhishek.learningzone.teacher

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.LoginActivity
import com.abhishek.learningzone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_trainer_dashbaord.*

class TrainerDashboard : AppCompatActivity() {

    private lateinit var mDatabaseref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_dashbaord)

        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        NameAndCourseFind(user)


        upload_notes_btn.setOnClickListener {
            val intent = Intent(this, T_Upload_Notes::class.java)
            startActivity(intent)
        }
        upload_assign_btn.setOnClickListener {
            //val intent = Intent(this, T_Upload_assign::class.java)
            //startActivity(intent)
            Toast.makeText(this, "Assignments can be given at-least 10 days after beginning of course", Toast.LENGTH_SHORT).show()
        }
        check_assign_btn.setOnClickListener {
            //val intent = Intent(this, T_check_assign::class.java)
            //startActivity(intent)
            Toast.makeText(this, "Assignments can be given at-least 10 days after beginning of course", Toast.LENGTH_SHORT).show()
        }
        send_notice_btn.setOnClickListener {
            //val intent = Intent(this, T_send_notice::class.java)
            //startActivity(intent)
            Toast.makeText(this, "Will be available soon", Toast.LENGTH_SHORT).show()
        }
        trainer_logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun NameAndCourseFind(user:FirebaseUser?){
        mDatabaseref = FirebaseDatabase.getInstance().reference.child("User").child(user!!.uid)
        mDatabaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(p0: DataSnapshot) {
                val name: String = p0.child("name").value.toString()
                val course : String = p0.child("courseteaching").value.toString()
                textView_trainer_name.text = name
                textView_course.text = "Course Teaching : $course"
            }
        }
        )
    }
}