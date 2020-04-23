package com.abhishek.learningzone.all_courses_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.abhishek.learningzone.R
import kotlinx.android.synthetic.main.fragment_engineering.view.*

class Engineering : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_engineering, container, false)

        view.eee_btn.setOnClickListener {
            Toast.makeText(activity, "Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        view.cse_btn.setOnClickListener {
            Toast.makeText(activity, "Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        view.civil_btn.setOnClickListener {
            Toast.makeText(activity, "Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        view.mech_btn.setOnClickListener {
            Toast.makeText(activity, "Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        view.ee_btn.setOnClickListener {
            Toast.makeText(activity, "Will be Available soon", Toast.LENGTH_SHORT).show()

        }
        return view
    }


}
