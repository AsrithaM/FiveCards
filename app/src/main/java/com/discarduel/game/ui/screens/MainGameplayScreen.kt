package com.discarduel.game.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.discarduel.game.Card
import com.discarduel.game.R
import com.discarduel.game.ui.theme.Cream
import com.discarduel.game.ui.theme.Felt
import com.discarduel.game.ui.theme.FeltDark
import com.discarduel.game.ui.theme.FeltLight
import com.discarduel.game.ui.theme.FiveCardsTheme
import com.discarduel.game.ui.theme.Gold
import com.discarduel.game.ui.theme.GoldLight
import com.discarduel.game.ui.theme.GreenZero
import com.discarduel.game.ui.theme.StatusBg

enum class GamePhase { IDLE, GAME_STARTED }

@Composable
fun MainGameplayScreen(
    phase: GamePhase = GamePhase.IDLE,
    statusMessage: String = "Press New Game to begin.",
    computerCardCount: Int = 0,
    computerHandPoints: Int? = null,
    playerHand: List<Card> = emptyList(),
    playerHandPoints: Int = 0,
    openCard: Card? = null,
    jokerCard: Card? = null,
    jokerRank: Card.Rank? = null,
    cardImages: IntArray = IntArray(54),
    deckCount: Int = 0,
    selectedIndices: Set<Int> = emptySet(),
    canDeclare: Boolean = false,
    isPlayerTurn: Boolean = false,
    mustDraw: Boolean = false,
    onCardSelected: (Int) -> Unit = {},
    onDrawFromDeck: () -> Unit = {},
    onDrawFromOpen: () -> Unit = {},
    onDiscard: () -> Unit = {},
    onDeclare: () -> Unit = {},
    onNewGame: () -> Unit = {}
) {
    val inGame = phase == GamePhase.GAME_STARTED
    val hasSelection = selectedIndices.isNotEmpty()

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
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {

        // ── Title ──
        Text(
            text = "♠  Discard Duel  ♥",
            color = Gold,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Classic Card Game",
            color = GoldLight.copy(alpha = 0.7f),
            fontSize = 9.sp,
            letterSpacing = 3.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ── Status box ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(StatusBg, RoundedCornerShape(6.dp))
                .border(1.dp, Gold.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = statusMessage,
                color = Cream,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Computer hand ──
        ZoneLabel("— COMPUTER'S HAND —")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(computerCardCount) {
                FaceDownCard()
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
        if (computerHandPoints != null) {
            PointsTag(
                "$computerHandPoints pts",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // ── Table divider ──
        TableDivider(label = "♦  TABLE  ♦")

        // ── Mid section: Deck | Open Card | Wild Joker ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Deck — clickable to draw when it's the player's turn and no card drawn yet
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.then(
                    if (inGame && isPlayerTurn && !hasSelection)
                        Modifier.clickable { onDrawFromDeck() } else Modifier
                )
            ) {
                ZoneLabel("DECK")
                FaceDownCard(pulsing = inGame && isPlayerTurn && !hasSelection)
                Spacer(modifier = Modifier.height(4.dp))
                PointsTag("$deckCount")
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Open card — clickable to draw
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ZoneLabel("OPEN CARD")
                if (openCard != null && cardImages[openCard.id()] != 0) {
                    CardImage(
                        drawableRes = cardImages[openCard.id()],
                        selectable = inGame && isPlayerTurn && !hasSelection,
                        onClick = onDrawFromOpen
                    )
                } else {
                    CardSlot(label = "✦")
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Wild Joker
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ZoneLabel("WILD JOKER")
                if (jokerCard != null && cardImages[jokerCard.id()] != 0) {
                    CardImage(drawableRes = cardImages[jokerCard.id()])
                } else {
                    CardSlot(label = "?")
                }
                Spacer(modifier = Modifier.height(4.dp))
                if (jokerCard != null) PointsTag("RANK = 0")
            }
        }

        // ── Player hand divider ──
        TableDivider(label = "♣  YOUR HAND  ♣")

        // ── Player hand ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ZoneLabel("— YOUR HAND —")
            PointsTag("$playerHandPoints pts")
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            itemsIndexed(playerHand) { index, card ->
                val isSelected = selectedIndices.contains(index)
                val isZero = card.rank() == null || card.rank() == jokerRank
                if (cardImages[card.id()] != 0) {
                    CardImage(
                        drawableRes = cardImages[card.id()],
                        selectable = inGame && isPlayerTurn,
                        selected = isSelected,
                        isZeroValue = isZero,
                        modifier = Modifier.padding(bottom = if (isSelected) 14.dp else 0.dp),
                        onClick = { onCardSelected(index) }
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ── Action buttons ──
        when (phase) {
            GamePhase.IDLE -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PrimaryButton(label = "New Game", onClick = onNewGame)
                }
            }

            GamePhase.GAME_STARTED -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (hasSelection && !mustDraw) {
                        PrimaryButton(
                            label = "Discard",
                            onClick = onDiscard,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (canDeclare && isPlayerTurn) {
                        GoldOutlineButton(
                            label = "Declare!",
                            onClick = onDeclare,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Show New Game if neither button visible yet
                    if (!(hasSelection && !mustDraw) && !(canDeclare && isPlayerTurn)) {
                        GoldOutlineButton(
                            label = "New Game",
                            onClick = onNewGame,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

    }
}

// ── Reusable components ──

@Composable
private fun ZoneLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Gold.copy(alpha = 0.6f),
        fontSize = 9.sp,
        letterSpacing = 2.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier.wrapContentWidth()
    )
}

@Composable
private fun TableDivider(label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = Gold.copy(alpha = 0.15f))
        Text(text = label, color = Gold.copy(alpha = 0.4f), fontSize = 9.sp, letterSpacing = 2.sp)
        HorizontalDivider(modifier = Modifier.weight(1f), color = Gold.copy(alpha = 0.15f))
    }
}

@Composable
private fun PointsTag(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0x66000000), RoundedCornerShape(10.dp))
            .border(1.dp, Gold.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(text = text, color = Gold, fontSize = 9.sp, letterSpacing = 1.sp)
    }
}

@Composable
private fun FaceDownCard(pulsing: Boolean = false) {
    Image(
        painter = painterResource(id = R.drawable.back),
        contentDescription = "Face down card",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(width = 64.dp, height = 96.dp)
            .clip(RoundedCornerShape(6.dp))
            .border(
                width = if (pulsing) 2.dp else 1.dp,
                color = if (pulsing) Gold.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
            )
    )
}

@Composable
private fun CardImage(
    drawableRes: Int,
    selectable: Boolean = false,
    selected: Boolean = false,
    isZeroValue: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val borderColor = when {
        selected -> Gold
        isZeroValue -> GreenZero
        selectable -> Gold.copy(alpha = 0.5f)
        else -> Color.White.copy(alpha = 0.15f)
    }
    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = "Card",
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .size(width = 64.dp, height = 96.dp)
            .clip(RoundedCornerShape(6.dp))
            .border(
                width = if (selected || isZeroValue) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(6.dp)
            )
            .then(if (selectable || selected) Modifier.clickable { onClick() } else Modifier)
    )
}

@Composable
private fun CardSlot(label: String) {
    Box(
        modifier = Modifier
            .size(width = 64.dp, height = 96.dp)
            .border(2.dp, Gold.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = Gold.copy(alpha = 0.2f), fontSize = 22.sp)
    }
}

@Composable
private fun PrimaryButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = FeltDark)
    ) {
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
    }
}

@Composable
private fun GoldOutlineButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(4.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Gold),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Gold)
    ) {
        Text(text = label, fontSize = 11.sp, letterSpacing = 1.sp)
    }
}

@Preview(showBackground = true, heightDp = 850)
@Composable
fun MainGameplayScreenPreview() {
    FiveCardsTheme {
        MainGameplayScreen()
    }
}

@Preview(showBackground = true, heightDp = 850)
@Composable
fun MainGameplayScreenInGamePreview() {
    FiveCardsTheme {
        MainGameplayScreen(
            phase = GamePhase.GAME_STARTED,
            computerCardCount = 5,
            statusMessage = "Your turn — tap a card or draw from the deck.",
            isPlayerTurn = true,
            canDeclare = true,
            selectedIndices = setOf(1),
            playerHandPoints = 14,
            deckCount = 36
        )
    }
}
