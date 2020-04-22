package com.abhishek.learningzone

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pdf_reader.*
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class PdfReader : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)


        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true

        var pdflink = intent.getStringExtra("downloaduri") ?: return

        try {
            pdflink = URLEncoder.encode(pdflink, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$pdflink")

    }
}