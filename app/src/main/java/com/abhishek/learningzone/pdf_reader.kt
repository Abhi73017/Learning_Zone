package com.abhishek.learningzone

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pdf_reader.*

class pdf_reader : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)

        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true

        //put the recieved download from recyclerview view button here in url

        // val url = url recieved from view button from recyclerview activity

        //webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
    }
}