package com.abhishek.learningzone.main_fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.abhishek.learningzone.R
import com.abhishek.learningzone.Student.active_courses
import com.abhishek.learningzone.Student.all_courses
import com.abhishek.learningzone.Student.view_notices
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*

class Home : Fragment() {

    companion object {
        fun newInstance() = Home()
    }

    val images = arrayOf<Int>(R.drawable.slide1, R.drawable.slide2, R.drawable.slide3, R.drawable.slide4,R.drawable.slide5)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view : View =  inflater.inflate(R.layout.home_fragment, container, false)

        view.active_courses_btn.setOnClickListener {
            val intent = Intent(activity, active_courses::class.java)
            activity!!.startActivity(intent)
        }
        view.all_courses_btn.setOnClickListener {
            val intent = Intent(activity, all_courses::class.java)
            activity!!.startActivity(intent)
        }
        view.notice_board_btn.setOnClickListener {
            val intent = Intent(activity, view_notices::class.java)
            activity!!.startActivity(intent)
        }

        return view
        }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        for (image in images){
            Sliderimage(image)
        }
    }

    public fun Sliderimage(image : Int){
        var imageview = ImageView(activity)
        imageview.setBackgroundResource(image)
        v_flipper.addView(imageview)
        v_flipper.flipInterval = 2500
        v_flipper.startFlipping()
        v_flipper.isAutoStart = true
        v_flipper.setInAnimation(activity, android.R.anim.slide_in_left)
        v_flipper.setOutAnimation(activity, android.R.anim.slide_out_right)
    }
}