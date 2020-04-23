package com.abhishek.learningzone.Student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.R
import com.abhishek.learningzone.Student.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_all_courses.*

class all_courses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_courses)

        val fragmentAdapter = SectionsPagerAdapter(supportFragmentManager)

        viewPagerSyllabus.adapter = fragmentAdapter

        tabs.setupWithViewPager(viewPagerSyllabus)

    }
}