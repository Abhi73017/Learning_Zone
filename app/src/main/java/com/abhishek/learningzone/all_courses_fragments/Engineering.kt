package com.abhishek.learningzone.all_courses_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.abhishek.learningzone.R
import kotlinx.android.synthetic.main.fragment_engineering.*

class Engineering : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_engineering, container, false)

        eee_btn.setOnClickListener {
            Toast.makeText(activity,"Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        cse_btn.setOnClickListener {
            Toast.makeText(activity,"Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        civil_btn.setOnClickListener {
            Toast.makeText(activity,"Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        mech_btn.setOnClickListener {
            Toast.makeText(activity,"Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        ee_btn.setOnClickListener {
            Toast.makeText(activity,"Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
