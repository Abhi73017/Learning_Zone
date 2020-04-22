package com.abhishek.learningzone.teacher

//import com.google.firebase.auth.FirebaseAuth
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.learningzone.R
import com.abhishek.learningzone.model.DatabaseCourse
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_t_upload_notes.*
import java.io.File


class T_Upload_Notes : AppCompatActivity() {
    private var uri: Uri? = null
    private lateinit var course: String
//    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_upload_notes)

        course_selector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    course = parent!!.getItemAtPosition(position).toString()
                } else {
                    course = "Random"
                }
            }
        }


        // File Chooser
        selectfilebtn.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "application/pdf"
                startActivityForResult(it, 111)
            }
        }


        // Upload Buttn
        uploadbtn.setOnClickListener {

            if (uri == null || filenameinput.text.isEmpty()) {

                Toast.makeText(this@T_Upload_Notes, "Choose Required Options", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val filename = filenameinput.text.toString().trim()

                UploadToStroage(filename, uri!!, course)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 111) {

            uri = data?.data!!

            val file = File(uri!!.path)
            selectfilebtn.text = file.getName().toString()

        }
    }


    private fun UploadToStroage(filename: String, uri: Uri, course: String) {

        FirebaseStorage.getInstance().getReference("/$course/$filename").apply {
            putFile(uri)
                .addOnSuccessListener {
                    println("upload succesfully ${it.metadata?.path}")
                    var fileref = it.metadata?.reference
                    if (fileref != null) {
                        fileref.downloadUrl.addOnSuccessListener {
                            insertDatabase(course, filename, it.toString())
                        }
                    }

                }
                .addOnFailureListener {
                    //choosenText.text = "Uploading Failed"
                    Toast.makeText(this@T_Upload_Notes, "Upload Failed", Toast.LENGTH_SHORT)
                }.addOnProgressListener {
                    val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
                    upload_progress.progress = progress.toInt()
                }
        }
    }

    private fun insertDatabase(course: String, filename: String, DownloadUri: String) {
        var Dref = FirebaseDatabase.getInstance().getReference("Course/$course")
        var DId = Dref.push().key
        var databaseCourse = DatabaseCourse(DId!!, filename, DownloadUri)
        Dref.child(DId).setValue(databaseCourse).addOnSuccessListener {
            Toast.makeText(applicationContext, "Upload Complete", Toast.LENGTH_LONG).show()
        }
    }

}
