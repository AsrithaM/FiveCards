package com.apps.asritha.fivecards

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.apps.asritha.fivecards.ui.screens.InstructionsScreen
import com.apps.asritha.fivecards.ui.theme.FiveCardsTheme

class Instructions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContent {
            FiveCardsTheme {
                InstructionsScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}
