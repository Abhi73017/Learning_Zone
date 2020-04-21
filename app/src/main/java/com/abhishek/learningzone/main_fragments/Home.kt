package com.abhishek.learningzone.main_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.abhishek.learningzone.R
import kotlinx.android.synthetic.main.home_fragment.*

class Home : Fragment() {

    companion object {
        fun newInstance() = Home()
    }
    val images = arrayOf<Int>(R.drawable.slide1, R.drawable.slide2, R.drawable.slide3, R.drawable.slide4,R.drawable.slide5)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
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