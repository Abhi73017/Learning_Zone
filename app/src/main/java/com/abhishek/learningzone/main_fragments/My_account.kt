package com.abhishek.learningzone.main_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.abhishek.learningzone.LoginActivity
import com.abhishek.learningzone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_my_account.*
import kotlinx.android.synthetic.main.fragment_my_account.view.*

class My_account : Fragment() {

    private lateinit var mDatabaseref: DatabaseReference


    companion object {
        fun newInstance() = My_account()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View = inflater.inflate(R.layout.fragment_my_account, container, false)


        v.my_stats_btn.setOnClickListener {
            Toast.makeText(activity, "Will be available soon", Toast.LENGTH_SHORT).show()
        }

        v.warning_btn.setOnClickListener {
            Toast.makeText(activity, "Will be available soon", Toast.LENGTH_SHORT).show()
        }

        v.logOut_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        NameAndEmailFind(user)
    }

    private fun NameAndEmailFind(user : FirebaseUser?){
        mDatabaseref = FirebaseDatabase.getInstance().reference.child("User").child(user!!.uid)
        mDatabaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val name: String = p0.child("name").value.toString()
                val email : String? = user.email
                textView_name.text = name
                textView_email.text = email
            }
        }
        )
    }

}
