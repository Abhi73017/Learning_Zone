package com.abhishek.learningzone.courses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.R
import com.abhishek.learningzone.Student.S_notes_view
import kotlinx.android.synthetic.main.activity_c_pro.*

class C_pro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_pro)

        val course : String = "C Language"

        c_pro_read_notes_btn.setOnClickListener {
            val intent = Intent(this, S_notes_view::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }
    }
}