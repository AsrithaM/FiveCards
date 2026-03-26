package com.apps.asritha.fivecards

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.apps.asritha.fivecards.ui.screens.GameStartScreen
import com.apps.asritha.fivecards.ui.theme.FiveCardsTheme

class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContent {
            FiveCardsTheme {
                GameStartScreen(
                    onStartGame = { startActivity(Intent(this, MainActivity::class.java)) },
                    onHowToPlay = { startActivity(Intent(this, Instructions::class.java)) }
                )
            }
        }
    }
}
