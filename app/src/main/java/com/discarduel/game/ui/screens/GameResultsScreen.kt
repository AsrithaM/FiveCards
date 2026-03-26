package com.discarduel.game.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.discarduel.game.ui.components.BottomNavBar
import com.discarduel.game.ui.components.NavTab
import com.discarduel.game.ui.theme.FiveCardsTheme
import com.discarduel.game.ui.theme.GoldAccent
import com.discarduel.game.ui.theme.ResultLoseRed
import com.discarduel.game.ui.theme.ResultWinGold
import com.discarduel.game.ui.theme.TableGreen
import com.discarduel.game.ui.theme.TableGreenDark

enum class GameResult { WIN, LOSE, DRAW }

@Composable
fun GameResultsScreen(
    result: GameResult = GameResult.WIN,
    playerScore: Int = 5,
    computerScore: Int = 18,
    chipsEarned: Int = 50,
    onPlayAgain: () -> Unit = {},
    onMainMenu: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(NavTab.TABLE) }
    val isWin = result == GameResult.WIN
    val isDraw = result == GameResult.DRAW

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TableGreenDark)
    ) {
        // Result banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    when (result) {
                        GameResult.WIN -> ResultWinGold.copy(alpha = 0.2f)
                        GameResult.LOSE -> ResultLoseRed.copy(alpha = 0.2f)
                        GameResult.DRAW -> TableGreen
                    }
                )
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = if (isWin || isDraw) Icons.Filled.EmojiEvents
                    else Icons.Filled.SentimentDissatisfied,
                    contentDescription = null,
                    tint = when (result) {
                        GameResult.WIN -> ResultWinGold
                        GameResult.LOSE -> ResultLoseRed
                        GameResult.DRAW -> Color.White
                    },
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (result) {
                        GameResult.WIN -> "YOU WIN!"
                        GameResult.LOSE -> "COMPUTER WINS!"
                        GameResult.DRAW -> "IT'S A DRAW"
                    },
                    color = when (result) {
                        GameResult.WIN -> ResultWinGold
                        GameResult.LOSE -> ResultLoseRed
                        GameResult.DRAW -> Color.White
                    },
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }

        // Scores and chips
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(TableGreen)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Score comparison
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TableGreenDark)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "FINAL SCORES",
                        color = GoldAccent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PlayerScoreColumn(
                            label = "YOU",
                            score = playerScore,
                            highlight = isWin
                        )
                        Text(
                            text = "VS",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        PlayerScoreColumn(
                            label = "CPU",
                            score = computerScore,
                            highlight = !isWin && !isDraw
                        )
                    }
                }
            }

            // Chips earned
            if (isWin || isDraw) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = TableGreenDark)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isWin) "CHIPS EARNED" else "CHIPS RETURNED",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 12.sp,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "+$chipsEarned",
                                color = ResultWinGold,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(GoldAccent.copy(alpha = 0.15f), CircleShape)
                                .border(2.dp, GoldAccent, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.EmojiEvents,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }

            Divider(
                color = Color.White.copy(alpha = 0.1f),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Hand breakdown
            HandSummaryRow(label = "Your best hand", value = "Pair of Aces")
            HandSummaryRow(label = "Computer's hand", value = "High Card King")

            Spacer(modifier = Modifier.weight(1f))

            // Action buttons
            Button(
                onClick = onPlayAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
            ) {
                Text(
                    text = "PLAY AGAIN",
                    color = TableGreenDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    letterSpacing = 2.sp
                )
            }

            OutlinedButton(
                onClick = onMainMenu,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    GoldAccent.copy(alpha = 0.5f)
                ),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldAccent)
            ) {
                Text("MAIN MENU", fontWeight = FontWeight.Medium, fontSize = 14.sp)
            }
        }

        BottomNavBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
    }
}

@Composable
private fun PlayerScoreColumn(label: String, score: Int, highlight: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = if (highlight) GoldAccent else Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = score.toString(),
            color = if (highlight) ResultWinGold else Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "pts",
            color = Color.White.copy(alpha = 0.4f),
            fontSize = 12.sp
        )
    }
}

@Composable
private fun HandSummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
        Text(text = value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun GameResultsScreenWinPreview() {
    FiveCardsTheme {
        GameResultsScreen(result = GameResult.WIN)
    }
}

@Preview(showBackground = true)
@Composable
fun GameResultsScreenLosePreview() {
    FiveCardsTheme {
        GameResultsScreen(
            result = GameResult.LOSE,
            playerScore = 18,
            computerScore = 5,
            chipsEarned = 0
        )
    }
}
