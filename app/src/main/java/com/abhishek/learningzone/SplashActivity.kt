package com.abhishek.learningzone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.admin.AdminDashboard
import com.abhishek.learningzone.teacher.TrainerDashboard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SplashActivity : AppCompatActivity() {

    private lateinit var mDatabaseref: DatabaseReference
    val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser


    override fun onStart() {
        if (user!=null){
            preRoleMatching(user)
        }

        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (user == null) {
            Handler().postDelayed(
                {
                    Handler().postDelayed({
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }, 500)
                }, 2500
            )
        }
    }

    private fun preRoleMatching(user: FirebaseUser?) {

        mDatabaseref = FirebaseDatabase.getInstance().reference.child("User").child(user!!.uid)
        mDatabaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val role: String = p0.child("role").value.toString()
                println(role)
                    if (role == "Trainer") {
                        val intent = Intent(this@SplashActivity, TrainerDashboard::class.java)
                        startActivity(intent)
                    }
                    if (role == "Learner") {
                        val intent = Intent(this@SplashActivity, DashBoard::class.java)
                        startActivity(intent)
                    }
                    if (role == "Admin"){
                        val intent = Intent(this@SplashActivity, AdminDashboard::class.java)
                        startActivity(intent)
                    }

                }


        }
        )

    }
}
