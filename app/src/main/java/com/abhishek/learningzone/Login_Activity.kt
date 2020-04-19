package com.abhishek.learningzone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.abhishek.learningzone.teacher.trainer_dashbaord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*


class Login_Activity : AppCompatActivity(){

    private lateinit var mDatabaseref : DatabaseReference
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        button.setOnClickListener {
            email = fieldEmail.text.toString()
            password = fieldPassword.text.toString()
            signin(email, password)

        }

        testbtn.setOnClickListener{
            startActivity(Intent(this,trainer_dashbaord::class.java))
            finish()
        }
    }

    private fun signin(email:String, pass:String){
        if (!validateForm()) {
            println("not auth lol")
            return
        }

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    role_matching(user)
                }
                else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
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

    private fun role_matching(user: FirebaseUser?){
        mDatabaseref = FirebaseDatabase.getInstance().getReference().child("User").child(user!!.uid)
        mDatabaseref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var role : String = p0.child("role").value.toString()
                if (role==spinner1.selectedItem.toString()){
                    if (role=="trainer"){
                        var intent = Intent(this@Login_Activity, trainer_dashbaord::class.java)
                        startActivity(intent)
                        finish()
                    }
                    if (role == "learner"){
                        var intent = Intent(this@Login_Activity, DashBoard::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else{
                    Toast.makeText(this@Login_Activity, "You are not Authorised to Login as Selected Role", Toast.LENGTH_SHORT).show()
                }
            }

        }
        )

    }

}