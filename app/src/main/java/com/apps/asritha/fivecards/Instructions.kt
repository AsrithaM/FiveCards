package com.apps.asritha.fivecards

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class Instructions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_instructions)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
