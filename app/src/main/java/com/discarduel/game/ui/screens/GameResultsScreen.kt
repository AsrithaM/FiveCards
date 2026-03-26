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
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.discarduel.game.ui.theme.Cream
import com.discarduel.game.ui.theme.CreamFaded
import com.discarduel.game.ui.theme.Felt
import com.discarduel.game.ui.theme.FeltDark
import com.discarduel.game.ui.theme.FeltLight
import com.discarduel.game.ui.theme.FiveCardsTheme
import com.discarduel.game.ui.theme.Gold
import com.discarduel.game.ui.theme.GoldLight
import com.discarduel.game.ui.theme.Red
import com.discarduel.game.ui.theme.RuleCardBg

enum class GameResult { WIN, LOSE, DRAW }

@Composable
fun GameResultsScreen(
    result: GameResult = GameResult.WIN,
    playerScore: Int = 5,
    computerScore: Int = 18,
    onPlayAgain: () -> Unit = {},
    onMainMenu: () -> Unit = {}
) {
    val accentColor = when (result) {
        GameResult.WIN  -> Gold
        GameResult.LOSE -> Red
        GameResult.DRAW -> GoldLight
    }

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // ── Result icon + headline ──
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(accentColor.copy(alpha = 0.15f), CircleShape)
                        .border(2.dp, accentColor.copy(alpha = 0.6f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (result) {
                            GameResult.WIN  -> Icons.Filled.EmojiEvents
                            GameResult.LOSE -> Icons.Filled.SentimentDissatisfied
                            GameResult.DRAW -> Icons.Outlined.Handshake
                        },
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Text(
                    text = when (result) {
                        GameResult.WIN  -> "YOU WIN!"
                        GameResult.LOSE -> "COMPUTER WINS"
                        GameResult.DRAW -> "IT'S A DRAW"
                    },
                    color = accentColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = when (result) {
                        GameResult.WIN  -> "Your hand had fewer points"
                        GameResult.LOSE -> "Computer had fewer points"
                        GameResult.DRAW -> "Both hands were equal"
                    },
                    color = CreamFaded,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }

            // ── Score card ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RuleCardBg, RoundedCornerShape(16.dp))
                    .border(1.dp, Gold.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "FINAL SCORES",
                    color = Gold.copy(alpha = 0.6f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ScoreColumn(
                        label = "YOU",
                        score = playerScore,
                        highlight = result == GameResult.WIN
                    )
                    Text(
                        text = "VS",
                        color = Gold.copy(alpha = 0.3f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    ScoreColumn(
                        label = "CPU",
                        score = computerScore,
                        highlight = result == GameResult.LOSE
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Gold.copy(alpha = 0.12f))
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Lower score wins",
                    color = CreamFaded,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp
                )
            }

            // ── Buttons ──
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onPlayAgain,
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
                        text = "PLAY AGAIN",
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
                    border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Gold)
                ) {
                    Text("MAIN MENU", fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun ScoreColumn(label: String, score: Int, highlight: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = if (highlight) Gold else CreamFaded,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = score.toString(),
            color = if (highlight) Gold else Cream,
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "pts",
            color = CreamFaded,
            fontSize = 11.sp
        )
    }
}

@Preview(showBackground = true, heightDp = 750)
@Composable
fun GameResultsScreenWinPreview() {
    FiveCardsTheme { GameResultsScreen(result = GameResult.WIN, playerScore = 5, computerScore = 18) }
}

@Preview(showBackground = true, heightDp = 750)
@Composable
fun GameResultsScreenLosePreview() {
    FiveCardsTheme { GameResultsScreen(result = GameResult.LOSE, playerScore = 18, computerScore = 5) }
}

@Preview(showBackground = true, heightDp = 750)
@Composable
fun GameResultsScreenDrawPreview() {
    FiveCardsTheme { GameResultsScreen(result = GameResult.DRAW, playerScore = 10, computerScore = 10) }
}
