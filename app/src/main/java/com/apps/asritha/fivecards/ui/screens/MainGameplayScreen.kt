package com.apps.asritha.fivecards.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.asritha.fivecards.ui.components.BottomNavBar
import com.apps.asritha.fivecards.ui.components.NavTab
import com.apps.asritha.fivecards.ui.theme.CardRed
import com.apps.asritha.fivecards.ui.theme.CardWhite
import com.apps.asritha.fivecards.ui.theme.FiveCardsTheme
import com.apps.asritha.fivecards.ui.theme.GoldAccent
import com.apps.asritha.fivecards.ui.theme.NavBackground
import com.apps.asritha.fivecards.ui.theme.ScoreBackground
import com.apps.asritha.fivecards.ui.theme.TableGreen
import com.apps.asritha.fivecards.ui.theme.TableGreenDark
import com.apps.asritha.fivecards.ui.theme.TurnIndicatorBg

@Composable
fun MainGameplayScreen(
    playerScore: Int = 12,
    computerScore: Int = 8,
    isPlayerTurn: Boolean = true,
    onDiscard: () -> Unit = {},
    onDraw: () -> Unit = {},
    onDeclare: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(NavTab.TABLE) }
    val selectedCards = remember { mutableStateListOf<Int>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TableGreenDark)
    ) {
        // Score bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ScoreBackground)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreChip(label = "YOU", score = playerScore, highlight = isPlayerTurn)
            TurnIndicator(isPlayerTurn = isPlayerTurn)
            ScoreChip(label = "CPU", score = computerScore, highlight = !isPlayerTurn)
        }

        // Game table
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(TableGreen)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Opponent (CPU) cards - face down, fanned
                OpponentCardRow()

                Spacer(modifier = Modifier.height(8.dp))

                // Center area: deck + open card + discard pile
                CenterTableArea(onDraw = onDraw)

                Spacer(modifier = Modifier.height(8.dp))

                // Player cards - face up, fanned
                PlayerCardRow(
                    selectedCards = selectedCards,
                    onCardSelected = { index ->
                        if (selectedCards.contains(index)) selectedCards.remove(index)
                        else selectedCards.add(index)
                    }
                )
            }
        }

        // Action buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavBackground)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onDiscard,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldAccent),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent)
            ) {
                Text("DISCARD", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
            Button(
                onClick = onDeclare,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
            ) {
                Text(
                    "DECLARE",
                    color = TableGreenDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }

        BottomNavBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
    }
}

@Composable
private fun ScoreChip(label: String, score: Int, highlight: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = if (highlight) GoldAccent else Color.White.copy(alpha = 0.6f),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = score.toString(),
            color = if (highlight) GoldAccent else Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TurnIndicator(isPlayerTurn: Boolean) {
    Box(
        modifier = Modifier
            .background(TurnIndicatorBg, RoundedCornerShape(16.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = if (isPlayerTurn) "YOUR TURN" else "CPU TURN",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun OpponentCardRow() {
    Row(
        horizontalArrangement = Arrangement.spacedBy((-32).dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {
        repeat(5) { index ->
            FaceDownPlayingCard(
                modifier = Modifier
                    .size(width = 56.dp, height = 80.dp)
                    .rotate((index - 2) * 4f)
                    .offset(y = (kotlin.math.abs(index - 2) * 4).dp)
            )
        }
    }
}

@Composable
private fun PlayerCardRow(
    selectedCards: List<Int>,
    onCardSelected: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy((-28).dp),
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        val sampleCards = listOf("A♠", "7♥", "K♣", "3♦", "J♠")
        sampleCards.forEachIndexed { index, label ->
            val isSelected = selectedCards.contains(index)
            FaceUpPlayingCard(
                label = label,
                isRed = label.contains("♥") || label.contains("♦"),
                isSelected = isSelected,
                modifier = Modifier
                    .size(width = 56.dp, height = 80.dp)
                    .rotate((index - 2) * 4f)
                    .offset(y = if (isSelected) (-12).dp else (kotlin.math.abs(index - 2) * 3).dp)
                    .clickable { onCardSelected(index) }
            )
        }
    }
}

@Composable
private fun CenterTableArea(onDraw: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Deck
        FaceDownPlayingCard(
            modifier = Modifier
                .size(width = 64.dp, height = 90.dp)
                .clickable { onDraw() }
        )

        Spacer(modifier = Modifier.width(24.dp))

        // Open card
        FaceUpPlayingCard(
            label = "Q♥",
            isRed = true,
            isSelected = false,
            modifier = Modifier.size(width = 64.dp, height = 90.dp)
        )
    }
}

@Composable
private fun FaceDownPlayingCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A237E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, GoldAccent.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
        )
    }
}

@Composable
private fun FaceUpPlayingCard(
    label: String,
    isRed: Boolean,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.then(
            if (isSelected) Modifier.border(2.dp, GoldAccent, RoundedCornerShape(6.dp))
            else Modifier
        ),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = if (isRed) CardRed else Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainGameplayScreenPreview() {
    FiveCardsTheme {
        MainGameplayScreen()
    }
}
