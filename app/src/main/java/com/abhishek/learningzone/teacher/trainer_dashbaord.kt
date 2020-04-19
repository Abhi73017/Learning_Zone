package com.abhishek.learningzone.teacher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhishek.learningzone.Login_Activity
import com.abhishek.learningzone.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_trainer_dashbaord.*

class trainer_dashbaord : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_dashbaord)

        upload_notes_btn.setOnClickListener {
            var intent = Intent(this, T_Upload_Notes::class.java)
            startActivity(intent)
        }
        upload_assign_btn.setOnClickListener {
            var intent = Intent(this, T_Upload_assign::class.java)
            startActivity(intent)
        }
        check_assign_btn.setOnClickListener {
            var intent = Intent(this, T_check_assign::class.java)
            startActivity(intent)
        }
        send_notice_btn.setOnClickListener {
            var intent = Intent(this, T_send_notice::class.java)
            startActivity(intent)
        }
        trainer_logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}