package com.apps.asritha.fivecards.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.asritha.fivecards.ui.theme.Cream
import com.apps.asritha.fivecards.ui.theme.CreamFaded
import com.apps.asritha.fivecards.ui.theme.ExampleBg
import com.apps.asritha.fivecards.ui.theme.Felt
import com.apps.asritha.fivecards.ui.theme.FeltDark
import com.apps.asritha.fivecards.ui.theme.FeltLight
import com.apps.asritha.fivecards.ui.theme.FiveCardsTheme
import com.apps.asritha.fivecards.ui.theme.Gold
import com.apps.asritha.fivecards.ui.theme.GreenPoints
import com.apps.asritha.fivecards.ui.theme.Red
import com.apps.asritha.fivecards.ui.theme.RuleCardBg

@Composable
fun InstructionsScreen(
    onBack: () -> Unit = {}
) {
    Column(
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
            )
    ) {
        // Back button bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Gold
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // ── HEADER ──
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Minimum Points",
                color = Gold,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "GAME RULES & INSTRUCTIONS",
                color = Gold.copy(alpha = 0.55f),
                fontSize = 10.sp,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(
                    "♠" to Cream,
                    "♥" to Red,
                    "♦" to Red,
                    "♣" to Cream
                ).forEach { (suit, color) ->
                    Text(text = suit, color = color.copy(alpha = 0.35f), fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }

            GoldRule()

            // ── OBJECTIVE ──
            SectionHeading("Objective")
            BodyText(
                buildAnnotatedString {
                    append("The goal is simple: end the game with the lowest total point value in your hand. When you believe your hand is sufficiently low, you may ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Cream
                        )
                    ) { append("Declare") }
                    append(". If your points are fewer than the computer's, you win the round.")
                }
            )

            GoldRule()

            // ── CARD VALUES ──
            SectionHeading("Card Values")
            Spacer(modifier = Modifier.height(4.dp))
            CardValueTable()

            GoldRule()

            // ── SETUP ──
            SectionHeading("Setup")
            BodyText(
                buildAnnotatedString {
                    append("Each player is dealt ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Cream
                        )
                    ) { append("5 cards") }
                    append(". One card is set aside face-up as the ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Cream
                        )
                    ) { append("Wild Joker") }
                    append(" — any card in your hand that shares the same rank as the Wild Joker is worth zero points for the duration of that round. The top card of the remaining deck is placed face-up to begin the ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Cream
                        )
                    ) { append("Open Pile") }
                    append(".")
                }
            )

            GoldRule()

            // ── HOW TO PLAY ──
            SectionHeading("How to Play")
            BodyText(buildAnnotatedString { append("Players alternate turns. On each turn, proceed as follows:") })
            Spacer(modifier = Modifier.height(8.dp))

            StepItem(
                number = 1,
                title = "Draw a card",
                body = buildAnnotatedString {
                    append("Take the top card from the ")
                    Badge("Deck")
                    append(" or take the visible top card from the ")
                    Badge("Open Pile")
                    append(". Your hand will temporarily hold one extra card.")
                }
            )
            StepItem(
                number = 2,
                title = "Select cards to discard",
                body = buildAnnotatedString {
                    append("Tap one or more cards in your hand to select them. Valid discards are described in the section below. You must retain at least one card at all times.")
                }
            )
            StepItem(
                number = 3,
                title = "Discard",
                body = buildAnnotatedString {
                    append("Press ")
                    Badge("Discard")
                    append(" to place your selected cards onto the Open Pile. Your turn then passes to the computer.")
                }
            )

            GoldRule()

            // ── VALID DISCARDS ──
            SectionHeading("Valid Discards")
            BodyText(buildAnnotatedString { append("You may discard in any of the following combinations:") })
            Spacer(modifier = Modifier.height(8.dp))

            ExampleBlock(
                label = "Single Card",
                text = "Any one card from your hand."
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExampleBlock(
                label = "Multiples — Same Rank, Different Suits",
                text = "Two or more cards of the same rank, regardless of suit. For example: the 7 of Hearts and the 7 of Spades may be discarded together."
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExampleBlock(
                label = "Rummy Set — Consecutive Cards, Same Suit",
                text = "Three or more cards of the same suit in consecutive rank order. For example: 4♣, 5♣, and 6♣ form a valid rummy set."
            )

            GoldRule()

            // ── SWAP WITHOUT DRAWING ──
            SectionHeading("Swap Without Drawing")
            BodyText(
                buildAnnotatedString {
                    append("If you hold a card (or cards) of the same rank as the current top card of the Open Pile, you may discard them ")
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append("without drawing first") }
                    append(". This can be a useful way to shed cards quickly without adding to your hand.")
                }
            )

            GoldRule()

            // ── DECLARING ──
            SectionHeading("Declaring")
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                RuleCard(
                    suit = "♠", title = "When to Declare",
                    text = "Press Declare! at the start of your turn (before drawing) when you believe your hand has fewer points than the computer's."
                )
                RuleCard(
                    suit = "♥", title = "If You Win",
                    text = "Your point total is lower than the computer's. You win the round and a point is added to your score."
                )
                RuleCard(
                    suit = "♦", title = "If You Lose",
                    text = "The computer's total is equal to or lower than yours. The computer wins the round despite your declaration."
                )
                RuleCard(
                    suit = "♣", title = "Computer Declares",
                    text = "The computer may also declare when it judges its hand to be sufficiently low. The same win/loss rules apply."
                )
            }

            GoldRule()

            // ── IMPORTANT RULES ──
            SectionHeading("Important Rules")
            BodyText(
                buildAnnotatedString {
                    append("You must hold ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Cream
                        )
                    ) { append("at least one card") }
                    append(" in your hand at all times — a discard that would leave your hand empty is not permitted.")
                }
            )
            BodyText(
                buildAnnotatedString {
                    append("The Wild Joker is fixed for the entire round. Its rank is displayed on the table beside the deck for your reference.")
                }
            )
            BodyText(
                buildAnnotatedString {
                    append("If the deck is exhausted, the Open Pile is reshuffled (excluding the top card) and play continues.")
                }
            )

            // ── FOOTER ──
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "♠  Minimum Points  ♠  Complete Rules",
                color = Gold.copy(alpha = 0.25f),
                fontSize = 10.sp,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ── Reusable components ──

@Composable
private fun GoldRule() {
    HorizontalDivider(
        color = Gold.copy(alpha = 0.25f),
        thickness = 1.dp,
        modifier = Modifier.padding(vertical = 24.dp)
    )
}

@Composable
private fun SectionHeading(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text.uppercase(),
            color = Gold,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 4.sp
        )
        Spacer(modifier = Modifier.width(12.dp))
        HorizontalDivider(
            color = Gold.copy(alpha = 0.2f),
            thickness = 1.dp,
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun BodyText(text: androidx.compose.ui.text.AnnotatedString) {
    Text(
        text = text,
        color = CreamFaded,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun StepItem(number: Int, title: String, body: androidx.compose.ui.text.AnnotatedString) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Gold, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                color = FeltDark,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column {
            Text(text = title, color = Cream, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = body, color = CreamFaded, fontSize = 14.sp, lineHeight = 21.sp)
        }
    }
}

@Composable
private fun ExampleBlock(label: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ExampleBg, RoundedCornerShape(topEnd = 6.dp, bottomEnd = 6.dp))
            .border(
                width = 3.dp,
                color = Gold.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 3.dp, bottomStart = 3.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Column {
            Text(
                text = label.uppercase(),
                color = Gold.copy(alpha = 0.6f),
                fontSize = 9.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, color = CreamFaded, fontSize = 14.sp, lineHeight = 20.sp)
        }
    }
}

@Composable
private fun RuleCard(suit: String, title: String, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(RuleCardBg, RoundedCornerShape(8.dp))
            .border(1.dp, Gold.copy(alpha = 0.18f), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = suit, color = Gold, fontSize = 14.sp)
            Text(
                text = title.uppercase(),
                color = Gold,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, color = CreamFaded, fontSize = 14.sp, lineHeight = 20.sp)
    }
}

@Composable
private fun CardValueTable() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Gold.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
    ) {
        // Header row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(RuleCardBg, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            TableHeaderCell("Card", Modifier.weight(2f))
            TableHeaderCell("Points", Modifier.weight(1f))
            TableHeaderCell("Notes", Modifier.weight(3f))
        }

        HorizontalDivider(color = Gold.copy(alpha = 0.15f))

        val rows = listOf(
            Triple("Ace", "1" to Cream, "Lowest-value pip card"),
            Triple("2 – 10", "Face value" to Cream, "Two = 2, Three = 3, and so on"),
            Triple("J, Q, K", "10" to Red, "High-value — discard these when possible"),
            Triple("Joker", "0" to GreenPoints, "Always worth zero points"),
            Triple(
                "Wild Joker rank",
                "0" to GreenPoints,
                "Any card sharing the Wild Joker's rank is worth zero"
            )
        )

        rows.forEachIndexed { i, (card, pts, note) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (i % 2 == 0) Color.Transparent else Color(0x0AFFFFFF))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = card, color = Cream, fontSize = 14.sp, modifier = Modifier.weight(2f))
                Text(
                    text = pts.first,
                    color = pts.second,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = note,
                    color = CreamFaded,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    modifier = Modifier.weight(3f)
                )
            }
            if (i < rows.lastIndex) HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
        }
    }
}

@Composable
private fun TableHeaderCell(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        color = Gold.copy(alpha = 0.6f),
        fontSize = 9.sp,
        letterSpacing = 2.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

// Inline badge helper for AnnotatedString builders
private fun androidx.compose.ui.text.AnnotatedString.Builder.Badge(text: String) {
    withStyle(
        SpanStyle(
            color = Gold,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    ) {
        append("[$text]")
    }
}

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun InstructionsScreenPreview() {
    FiveCardsTheme {
        InstructionsScreen()
    }
}
