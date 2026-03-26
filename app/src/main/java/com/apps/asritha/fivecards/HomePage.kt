package com.apps.asritha.fivecards

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_home_page)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun play(v: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun rules(v: View) {
        startActivity(Intent(this, Instructions::class.java))
    }
}
