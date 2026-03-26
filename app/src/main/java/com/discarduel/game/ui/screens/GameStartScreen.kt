package com.discarduel.game.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.discarduel.game.R
import com.discarduel.game.ui.theme.Cream
import com.discarduel.game.ui.theme.CreamFaded
import com.discarduel.game.ui.theme.Felt
import com.discarduel.game.ui.theme.FeltDark
import com.discarduel.game.ui.theme.FeltLight
import com.discarduel.game.ui.theme.FiveCardsTheme
import com.discarduel.game.ui.theme.Gold
import com.discarduel.game.ui.theme.GoldLight
import com.discarduel.game.ui.theme.RuleCardBg

@Composable
fun GameStartScreen(
    onStartGame: () -> Unit = {},
    onHowToPlay: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colorStops = arrayOf(
                        0.0f to FeltLight,
                        0.4f to Felt,
                        1.0f to FeltDark
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Discard Duel logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "DISCARD DUEL",
                color = Gold,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Classic Card Game",
                color = GoldLight.copy(alpha = 0.7f),
                fontSize = 13.sp,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Info card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RuleCardBg, RoundedCornerShape(12.dp))
                    .border(1.dp, Gold.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                InfoRow(label = "Players", value = "1 vs Computer")
                HorizontalDivider(
                    color = Gold.copy(alpha = 0.1f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                InfoRow(label = "Deck", value = "54 Cards (with Jokers)")
                HorizontalDivider(
                    color = Gold.copy(alpha = 0.1f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                InfoRow(label = "Goal", value = "Lowest score wins")
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onStartGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = FeltDark
                )
            ) {
                Text(
                    text = "START GAME",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onHowToPlay,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(26.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.6f)),
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                    contentColor = Gold
                )
            ) {
                Text(
                    text = "HOW TO PLAY",
                    fontSize = 14.sp,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = CreamFaded, fontSize = 13.sp)
        Text(text = value, color = Cream, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun GameStartScreenPreview() {
    FiveCardsTheme {
        GameStartScreen()
    }
}
