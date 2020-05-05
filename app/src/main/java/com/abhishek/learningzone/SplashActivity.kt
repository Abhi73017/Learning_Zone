package com.abhishek.learningzone

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.*
import com.abhishek.learningzone.admin.AdminDashboard
import com.abhishek.learningzone.teacher.TrainerDashboard
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlin.system.exitProcess

class SplashActivity : AppCompatActivity() {

    private lateinit var mDatabaseref: DatabaseReference
    private val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private val MY_REQUEST_CODE = 9465

    override fun onStart() {

        if (isOnline()) {
            updateApp()
            if (user != null) {
                preRoleMatching(user)
            }
        } else {
            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setMessage("Internet Connection is not Available.")
                    .setCancelable(false)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                        System.exit(-1)
                    })

            val alert = dialogBuilder.create()
            alert.setTitle("Warning")
            alert.show()
        }


        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (isOnline()) {
            if (user == null) {
                Handler().postDelayed(
                        {
                            Handler().postDelayed({
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }, 500)
                        }, 2000
                )
            }
        } else {
            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setMessage("Internet Connection is not Available.")
                    .setCancelable(false)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                        exitProcess(-1)
                    })

            val alert = dialogBuilder.create()
            alert.setTitle("Warning")
            alert.show()
        }
    }

    private fun preRoleMatching(user: FirebaseUser?) {

        mDatabaseref = FirebaseDatabase.getInstance().reference.child("User").child(user!!.uid)
        mDatabaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val role: String = p0.child("role").value.toString()
                println(role)
                if (role == "Trainer") {
                    val intent = Intent(this@SplashActivity, TrainerDashboard::class.java)
                    startActivity(intent)
                    finish()
                }
                if (role == "Learner") {
                    val intent = Intent(this@SplashActivity, DashBoard::class.java)
                    startActivity(intent)
                    finish()
                }
                if (role == "Admin") {
                    val intent = Intent(this@SplashActivity, AdminDashboard::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        )
    }

    private fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    private fun updateApp() {

        val appUpdateManager = AppUpdateManagerFactory.create(this)

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        MY_REQUEST_CODE)
            }
        }
    }
}