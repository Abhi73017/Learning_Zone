package com.abhishek.learningzone

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.teacher.T_Upload_Notes
import com.abhishek.learningzone.teacher.TrainerDashboard
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var alertdialog: AlertDialog //dont delete this sir...i will use this later
    private lateinit var mDatabaseref: DatabaseReference
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        auth = FirebaseAuth.getInstance()

        button.setOnClickListener {

            email = fieldEmail.text.toString()
            password = fieldPassword.text.toString()
            //signin(email, password)
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
        }

        testbtn.setOnClickListener {
            startActivity(Intent(this, T_Upload_Notes::class.java))

        }
        viewbtn.setOnClickListener {
            startActivity(Intent(this, PdfReader::class.java))

        }
    }

    private fun signin(email: String, pass: String) {
        if (!validateForm()) {
            return
        }

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    roleMatching(user)
                } else {
                    Log.e("LoginActivity", task.exception.toString())
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "LoginActivity")
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "AuthException")
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "ExceptionMessage")
                    bundle.putString(FirebaseAnalytics.Param.CONTENT, task.exception.toString())
                    firebaseAnalytics.logEvent("Error", bundle)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            fieldEmail.error = "Required."
            valid = false
        } else {
            fieldEmail.error = null
        }

        val password = fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            fieldPassword.error = "Required."
            valid = false
        } else {
            fieldPassword.error = null
        }

        return valid
    }

    private fun roleMatching(user: FirebaseUser?) {

        mDatabaseref = FirebaseDatabase.getInstance().reference.child("User").child(user!!.uid)
        mDatabaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val role: String = p0.child("role").value.toString()
                println(role)
                if (role == spinner1.selectedItem.toString()) {
                    if (role == "trainer") {
                        val intent = Intent(this@LoginActivity, TrainerDashboard::class.java)
                        startActivity(intent)
                        finish()
                    }
                    if (role == "learner") {
                        val intent = Intent(this@LoginActivity, DashBoard::class.java)
                        startActivity(intent)
                        finish()
                    }

                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "You are not Authorised to Login as Selected Role",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        )

    }

}