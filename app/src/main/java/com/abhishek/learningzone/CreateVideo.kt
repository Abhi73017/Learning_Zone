package com.abhishek.learningzone

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore.MediaColumns.ORIENTATION
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.SparseIntArray
import android.view.Surface
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.abhishek.learningzone.teacher.TrainerDashboard
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_video.*
import me.panavtec.drawableview.DrawableViewConfig
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

@Suppress("DEPRECATION")
class CreateVideo : AppCompatActivity() {

    lateinit var config : DrawableViewConfig


    //---------------This Section is a part of screen recorder----------------

    private var screenDensity : Int = 0
    private var projectManager : MediaProjectionManager?= null
    private var mediaProjection : MediaProjection?= null
    private var virtualDisplay : VirtualDisplay? = null
    private var mediaProjectionCallback : MediaProjectionCallBack ?= null
    private var mediaRecorder : MediaRecorder ?= null
    internal var videoUri = ""

    companion object{
        private var REQUEST_CODE = 3334
        private var REQUEST_PERMISSION = 5465
        private var DISPLAY_WIDTH = 2800
        private var DISPLAY_HEIGHT = 1900
        private var ORIENTATIONS = SparseIntArray()

        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }
    }

    inner class MediaProjectionCallBack : MediaProjection.Callback() {

        override fun onStop() {
            if(toggleButton.isChecked){
                    toggleButton.isChecked = false
                    round_green_btn.visibility = GONE
                    mediaRecorder!!.stop()
                    mediaRecorder!!.reset()
            }
            mediaProjection = null
            stopScreenRecord()
        }
    }

    private fun stopScreenRecord() {
        if (virtualDisplay == null)
            return
        round_green_btn.visibility = GONE
        virtualDisplay!!.release()
        destroyMediaProjection()
    }

    private fun destroyMediaProjection() {
        if (mediaProjection != null){
            mediaProjection!!.unregisterCallback(mediaProjectionCallback)
            mediaProjection!!.stop()
            mediaProjection = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyMediaProjection()
    }

    private fun startRecoding(v: View?) {

        if((v as ToggleButton).isChecked){
            initRecorder()
            shareScreen()
        }
        else
        {
            mediaRecorder!!.stop()
            mediaRecorder!!.reset()
            stopScreenRecord()
        }
    }

    private fun shareScreen() {
        if (mediaProjection == null){
            startActivityForResult(projectManager!!.createScreenCaptureIntent(), REQUEST_CODE)
            return
        }
        virtualDisplay = createVirtualDisplay()
        mediaRecorder!!.start()
    }

    private fun createVirtualDisplay(): VirtualDisplay? {
        return mediaProjection?.createVirtualDisplay("CreateVideo", DISPLAY_WIDTH, DISPLAY_HEIGHT, screenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder?.surface, null, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_CODE){
            return
        }
        /*if (requestCode != Activity.RESULT_OK){
            Toast.makeText(this, "Screen Cast Permission Denied", Toast.LENGTH_SHORT).show()
            return
        }*/
        mediaProjectionCallback = MediaProjectionCallBack()
        mediaProjection = data?.let { projectManager?.getMediaProjection(resultCode, it) }
        mediaProjection!!.registerCallback(mediaProjectionCallback, null)
        virtualDisplay = createVirtualDisplay()
        mediaRecorder!!.start()
    }

    private fun initRecorder() {
        try {
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setVideoSource(MediaRecorder.VideoSource.SURFACE)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)

            videoUri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + StringBuilder("/")
                    .append("Learning_Zone")
                    .append(SimpleDateFormat("dd_MM-yyyy-hh_mm_ss").format(Date()))
                    .append(".mp4")
                    .toString()

            mediaRecorder!!.setOutputFile(videoUri)
            mediaRecorder!!.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT)
            mediaRecorder!!.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder!!.setVideoEncodingBitRate(512*1000)
            mediaRecorder!!.setVideoFrameRate(30)

            val rotation = windowManager.defaultDisplay.rotation
            val orientation = ORIENTATIONS.get(rotation + 90)
            mediaRecorder!!.setOrientationHint(orientation)
            mediaRecorder!!.prepare()
        }
        catch (e:IOException){
            e.printStackTrace()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_PERMISSION -> {
                if(grantResults.size > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    startRecoding(toggleButton)
                else{
                    toggleButton.isChecked = false
                    round_green_btn.visibility = GONE

                    Snackbar.make(root_layout, "Permissions", Snackbar.LENGTH_INDEFINITE).setAction(
                            "Enable", {
                            val i = Intent()
                        i.action = Settings.ACTION_APPLICATION_SETTINGS
                        i.addCategory(Intent.CATEGORY_DEFAULT)
                        i.data = Uri.parse("Package:$packageName")
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        startActivity(i)
                    }).show()
                }
                return
            }
        }
    }

    //--------------------Section end---------------------------------------------


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_video)

        // ---------------------------------------Screen Record Code start--------------------------------------------------------

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        screenDensity = metrics.densityDpi

        mediaRecorder = MediaRecorder()
        projectManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        DISPLAY_WIDTH = metrics.widthPixels
        DISPLAY_HEIGHT = metrics.heightPixels

        toggleButton.setOnClickListener {v->

            round_green_btn.visibility = VISIBLE

            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

                    if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)){

                        toggleButton.isChecked = false
                        round_green_btn.visibility = GONE

                        Snackbar.make(root_layout, "Permissions", Snackbar.LENGTH_INDEFINITE).setAction(
                                "Enable", {
                            ActivityCompat.requestPermissions(this,
                                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            android.Manifest.permission.RECORD_AUDIO), REQUEST_PERMISSION)}).show()
                    }
                else{
                        ActivityCompat.requestPermissions(this,
                                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        android.Manifest.permission.RECORD_AUDIO), REQUEST_PERMISSION)
                    }
            }
            else{
                startRecoding(v)
            }
        }


        //-----------------------------------Screen Record Code end--------------------------------------------------------------------------------------

        //-----------------------------------Sketch View Code Start---------------------------------------------------------------
        config = DrawableViewConfig()

        config.strokeColor = resources.getColor(android.R.color.black)
        config.isShowCanvasBounds = true
        config.minZoom = 1.0f
        config.maxZoom = 3.0f
        config.canvasHeight = 1900
        config.canvasWidth = 2800

        Sketch_view.setConfig(config)

        make_bold.setOnClickListener(View.OnClickListener {
            config.strokeWidth = config.strokeWidth + 10
        })

        make_light.setOnClickListener(View.OnClickListener {
            config.strokeWidth = config.strokeWidth - 10
        })

        dlt_btn.setOnClickListener(View.OnClickListener {
            Sketch_view.clear()
        })

        undo_btn.setOnClickListener(View.OnClickListener {
            Sketch_view.undo()
        })

        clr_black.setOnClickListener(View.OnClickListener {
            config.strokeColor = resources.getColor(android.R.color.black)
        })

        clr_red.setOnClickListener(View.OnClickListener {
            config.strokeColor = resources.getColor(android.R.color.holo_red_light)
        })

        clr_green.setOnClickListener(View.OnClickListener {
            config.strokeColor = resources.getColor(android.R.color.holo_green_dark)
        })

        clr_blue.setOnClickListener(View.OnClickListener {
            config.strokeColor = resources.getColor(android.R.color.holo_blue_dark)
        })

        clr_yellow.setOnClickListener(View.OnClickListener {
            config.strokeColor = resources.getColor(android.R.color.holo_orange_dark)
        })

        close_btn.setOnClickListener {
            val i = Intent(this, TrainerDashboard::class.java)
            startActivity(i)
            finish()
        }

        hide_btn.setOnClickListener(View.OnClickListener {
            hide_btn.visibility = GONE
            pallate_lin_layout.visibility = GONE
            show_btn.visibility = VISIBLE
        })

        show_btn.setOnClickListener(View.OnClickListener {
            show_btn.visibility = GONE
            pallate_lin_layout.visibility = VISIBLE
            hide_btn.visibility = VISIBLE
        })
        //-------------------------------------Sketch View Code end--------------------------------------------------------------------

    }



    //Double Back press code
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            val i = Intent(this, TrainerDashboard::class.java)
            startActivity(i)
            finish()
        }

        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press once again to exit!", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
    //Double back press code end
}

