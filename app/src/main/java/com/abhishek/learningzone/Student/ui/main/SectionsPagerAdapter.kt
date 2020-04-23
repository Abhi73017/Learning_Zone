package com.abhishek.learningzone.Student.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.abhishek.learningzone.all_courses_fragments.Engineering
import com.abhishek.learningzone.all_courses_fragments.Programming

class SectionsPagerAdapter(fm:FragmentManager) :FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {

        return when (position){

            1 ->{ Engineering() }

//
            else->{
                return Programming()
            }
        }
    }

    override fun getCount(): Int {

        return 2
    }


    override fun getPageTitle(position: Int): CharSequence? {

        return when(position){
            1-> {"Engineering"}

            else -> {return "Technology"}
        }


    }


}