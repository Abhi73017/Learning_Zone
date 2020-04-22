package com.abhishek.learningzone

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pdf_reader.*
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class pdf_reader : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)


        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true

        var pdflink= intent.getStringExtra("downloaduri") ?: return

//        var pdflink = "https://mindorks.s3.ap-south-1.amazonaws.com/courses/MindOrks_Android_Online_Professional_Course-Syllabus.pdf"
        try {
            pdflink = URLEncoder.encode(pdflink, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$pdflink")

    }
}