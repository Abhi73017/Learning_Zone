package com.abhishek.learningzone

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.admin.AdminDashboard
import com.abhishek.learningzone.teacher.TrainerDashboard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SplashActivity : AppCompatActivity() {

    private lateinit var mDatabaseref: DatabaseReference
    private val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onStart() {

        val intent = Intent(this, DashBoard::class.java)
        startActivity(intent)

        /*if (isOnline()) {
            if (user != null) {
                preRoleMatching(user)
            }
        }
        else{
            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setMessage("Internet Connection is not Available.")
                    .setCancelable(false)
                    .setPositiveButton("OK", DialogInterface.OnClickListener {
                        dialog, id -> System.exit(-1)
                    })

            val alert = dialogBuilder.create()
            alert.setTitle("Warning")
            alert.show()
        }*/

        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (isOnline()) {
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
        else {
            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setMessage("Internet Connection is not Available.")
                    .setCancelable(false)
                    .setPositiveButton("OK", DialogInterface.OnClickListener {
                        dialog, id -> System.exit(-1)
                    })

            val alert = dialogBuilder.create()
            alert.setTitle("Warning")
            alert.show()
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
                    finish()
                }
                if (role == "Learner") {
                    val intent = Intent(this@SplashActivity, DashBoard::class.java)
                    startActivity(intent)
                    finish()
                }
                if (role == "Admin") {
                    val intent = Intent(this@SplashActivity, AdminDashboard::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        )

    }

    fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}
