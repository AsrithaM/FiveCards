package com.apps.asritha.fivecards.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.asritha.fivecards.R
import com.apps.asritha.fivecards.ui.components.NavTab
import com.apps.asritha.fivecards.ui.theme.FiveCardsTheme
import com.apps.asritha.fivecards.ui.theme.GoldAccent
import com.apps.asritha.fivecards.ui.theme.TableGreen
import com.apps.asritha.fivecards.ui.theme.TableGreenDark

@Composable
fun GameStartScreen(
    onStartGame: () -> Unit = {}, onHowToPlay: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(NavTab.TABLE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TableGreenDark)
    ) {
        // Card fan and table area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(TableGreen)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Discard Duel logo",
                    modifier = Modifier.size(150.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "DISCARD DUEL",
                    color = GoldAccent,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
                Text(
                    text = "Classic Card Game",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Server info card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = TableGreenDark.copy(alpha = 0.8f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        InfoRow(label = "Players", value = "1 vs Computer")
                        Divider(
                            color = Color.White.copy(alpha = 0.1f),
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                        InfoRow(label = "Deck", value = "54 Cards (with Jokers)")
                        Divider(
                            color = Color.White.copy(alpha = 0.1f),
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                        InfoRow(label = "Goal", value = "Lowest score wins")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onStartGame,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
                ) {
                    Text(
                        text = "START GAME",
                        color = TableGreenDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        letterSpacing = 2.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = onHowToPlay) {
                    Text(
                        text = "HOW TO PLAY",
                        color = GoldAccent.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

//        BottomNavBar(
//            selectedTab = selectedTab,
//            onTabSelected = { selectedTab = it }
//        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.White.copy(alpha = 0.6f), fontSize = 13.sp)
        Text(text = value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun GameStartScreenPreview() {
    FiveCardsTheme {
        GameStartScreen()
    }
}
