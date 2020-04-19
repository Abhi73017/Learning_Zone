package com.abhishek.learningzone.teacher

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_t_upload_notes.*


class T_Upload_Notes : AppCompatActivity() {
    private lateinit var uri:Uri
    private lateinit var course:String
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_upload_notes)

        course_selector.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position!=0){
                    course = parent!!.getItemAtPosition(position).toString()
                }else{
                    course = "Random"
                }
            }
        }



        // File Chooser
        selectfilebtn.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "*/*"
                startActivityForResult(it,111)
            }
        }


        // Upload Buttn
        uploadbtn.setOnClickListener{
            val  filename =  filenameinput.text.toString().trim()
            if (uri == null || filename.isEmpty() ){
                Toast.makeText(this, "Choose Required Options" ,Toast.LENGTH_SHORT)
            }

            else{
                choosenText.text = "Uploading Started"
                UploadToStroage(filename,uri,course)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 111){

            uri = data?.data!!


            choosenText.text = uri.toString()

        }
    }


    private fun  UploadToStroage(filename:String, uri: Uri, course:String){
        val user = mAuth.currentUser
        if (user == null) {
            signInAnonymously()
            return
        }
        FirebaseStorage.getInstance().getReference("/$course/$filename").apply {
            putFile(uri)
                .addOnSuccessListener {
                    println("upload succesfully ${it.metadata?.path}")
                    choosenText.text = "Successfully Uploaded !! Filename: $filename Course: $course"

                }
                .addOnFailureListener {
                    choosenText.text = "Uploading Failed"
                    Toast.makeText(this@T_Upload_Notes, "Upload Failed" ,Toast.LENGTH_SHORT)
                }
        }
    }

    // Signin Anonmously

    private fun signInAnonymously() {
        mAuth.signInAnonymously()
            .addOnSuccessListener(this) {

                Log.d("anonmousSignin","Logged Through anonmous signin")
            }
            .addOnFailureListener(
                this
            ) { exception -> Log.e("Login", "signInAnonymously:FAILURE", exception) }
    }
}
