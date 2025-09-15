package com.example.mon_ets_wrapper

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        /*TODO would be nice to add
        1. gesture detector for zooming
        2. an actually clean logo
        3. figure out why fullscreen doesnt exist (problem being a full calendar looking horrible, not fully our end)
        4. after further testing prelaoding would be nice ux / rm loadwithoverviewmode ?
        5. Check if injection to handle Ui possible
         */
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        val rootLayout = findViewById<ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets

        }

        val webview = findViewById<WebView>(R.id.éts_page)



        webview.webViewClient = WebViewClient()

        val settings: WebSettings = webview.settings
        settings.javaScriptEnabled = true //dont care, ets side
        settings.domStorageEnabled = true
        settings.useWideViewPort = true //aka responsivness if done by ets
        settings.loadWithOverviewMode = true

        webview.loadUrl("https://portail.etsmtl.ca/home")

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webview.canGoBack()){
                    webview.goBack()
                }else{
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }
}