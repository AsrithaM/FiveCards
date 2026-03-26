package com.apps.asritha.fivecards

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.apps.asritha.fivecards.ui.screens.GamePhase
import com.apps.asritha.fivecards.ui.screens.MainGameplayScreen
import com.apps.asritha.fivecards.ui.theme.FiveCardsTheme
import java.util.Collections
import java.util.LinkedList

class MainActivity : AppCompatActivity() {

    // ── Drawable resource map (Card.id() → R.drawable.*) ──
    private val cardImages = intArrayOf(
        R.drawable.ace_of_clubs,
        R.drawable.clubs_2,
        R.drawable.clubs_3,
        R.drawable.clubs_4,
        R.drawable.clubs_5,
        R.drawable.clubs_6,
        R.drawable.clubs_7,
        R.drawable.clubs_8,
        R.drawable.clubs_9,
        R.drawable.clubs_10,
        R.drawable.jack_of_clubs2,
        R.drawable.queen_of_clubs2,
        R.drawable.king_of_clubs2,
        R.drawable.ace_of_diamonds,
        R.drawable.diamonds_2,
        R.drawable.diamonds_3,
        R.drawable.diamonds_4,
        R.drawable.diamonds_5,
        R.drawable.diamonds_6,
        R.drawable.diamonds_7,
        R.drawable.diamonds_8,
        R.drawable.diamonds_9,
        R.drawable.diamonds_10,
        R.drawable.jack_of_diamonds2,
        R.drawable.queen_of_diamonds2,
        R.drawable.king_of_diamonds2,
        R.drawable.ace_of_hearts,
        R.drawable.hearts_2,
        R.drawable.hearts_3,
        R.drawable.hearts_4,
        R.drawable.hearts_5,
        R.drawable.hearts_6,
        R.drawable.hearts_7,
        R.drawable.hearts_8,
        R.drawable.hearts_9,
        R.drawable.hearts_10,
        R.drawable.jack_of_hearts2,
        R.drawable.queen_of_hearts2,
        R.drawable.king_of_hearts2,
        R.drawable.ace_of_spades,
        R.drawable.spades_2,
        R.drawable.spades_3,
        R.drawable.spades_4,
        R.drawable.spades_5,
        R.drawable.spades_6,
        R.drawable.spades_7,
        R.drawable.spades_8,
        R.drawable.spades_9,
        R.drawable.spades_10,
        R.drawable.jack_of_spades2,
        R.drawable.queen_of_spades2,
        R.drawable.king_of_spades2,
        R.drawable.black_joker,
        R.drawable.red_joker
    )

    // ── Compose UI state ──
    private var uiPhase by mutableStateOf(GamePhase.IDLE)
    private var uiStatus by mutableStateOf("Press New Game to begin.")
    private var uiPlayerHand by mutableStateOf<List<Card>>(emptyList())
    private var uiPlayerHandPoints by mutableIntStateOf(0)
    private var uiComputerCardCount by mutableIntStateOf(0)
    private var uiComputerHandPoints by mutableStateOf<Int?>(null)
    private var uiOpenCard by mutableStateOf<Card?>(null)
    private var uiJokerCard by mutableStateOf<Card?>(null)
    private var uiJokerRank by mutableStateOf<Card.Rank?>(null)
    private var uiDeckCount by mutableIntStateOf(0)
    private var uiSelectedIndices by mutableStateOf<Set<Int>>(emptySet())
    private var uiCanDeclare by mutableStateOf(false)
    private var uiIsPlayerTurn by mutableStateOf(false)

    // ── Game logic state ──
    private var jokerCard: Card? = null
    private var jokerRank: Card.Rank? = null
    private var cardDeck: LinkedList<Card> = LinkedList()
    private val Player: LinkedList<Card> = LinkedList()
    private val Opponent: LinkedList<Card> = LinkedList()
    private val OpenCard: LinkedList<Card> = LinkedList()
    private val CardArray: LinkedList<Card> = LinkedList()
    private var PlayerTurn = false
    private var discarded = false
    private var drawn = false
    private var sameRank = false
    private var Declared = false
    private var completedTurns = 0  // tracks full turns to unlock Declare

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContent {
            FiveCardsTheme {
                MainGameplayScreen(
                    phase = uiPhase,
                    statusMessage = uiStatus,
                    computerCardCount = uiComputerCardCount,
                    computerHandPoints = uiComputerHandPoints,
                    playerHand = uiPlayerHand,
                    playerHandPoints = uiPlayerHandPoints,
                    openCard = uiOpenCard,
                    jokerCard = uiJokerCard,
                    jokerRank = uiJokerRank,
                    cardImages = cardImages,
                    deckCount = uiDeckCount,
                    selectedIndices = uiSelectedIndices,
                    canDeclare = uiCanDeclare,
                    isPlayerTurn = uiIsPlayerTurn,
                    onCardSelected = { index -> toggleCardSelection(index) },
                    onDrawFromDeck = { drawFromDeck() },
                    onDrawFromOpen = { drawFromOpen() },
                    onDiscard = { discard() },
                    onDeclare = { declareNow() },
                    onNewGame = { startGame() }
                )
            }
        }
    }

    // ── New Game ──
    private fun startGame() {
        cardDeck = Card.newDeck()
        Player.clear(); Opponent.clear(); OpenCard.clear(); CardArray.clear()
        Collections.shuffle(cardDeck)

        repeat(5) { Player.add(cardDeck.removeAt(0)) }
        repeat(5) { Opponent.add(cardDeck.removeAt(0)) }
        OpenCard.add(cardDeck.removeAt(0))
        jokerCard = cardDeck.removeAt(0)
        jokerRank = jokerCard!!.rank()

        PlayerTurn = true
        discarded = false; drawn = false; sameRank = false; Declared = false
        completedTurns = 0

        syncUi()
        uiCanDeclare = false
        uiIsPlayerTurn = true
        uiPhase = GamePhase.GAME_STARTED
    }

    // ── Draw from deck ──
    private fun drawFromDeck() {
        if (!PlayerTurn || drawn || Player.size >= 6) return
        if (cardDeck.isEmpty()) reshuffleDeck()
        Player.add(cardDeck.removeAt(0))
        drawn = true
        syncUi()
        if(discarded) {
            uiStatus = "Cards discarded. Computer's turn…"
            computerTurn()
        } else {
            uiStatus = "Card drawn. Tap a card to select, then Discard."
        }
    }

    // ── Take open card ──
    private fun drawFromOpen() {
        if (!PlayerTurn || drawn || Player.size >= 6) return
        if (OpenCard.isEmpty()) return
        val card = OpenCard.last
        Player.add(card); OpenCard.remove(card)
        drawn = true
        syncUi()
        uiStatus = "Took open card. Tap a card to select, then Discard."
    }

    // ── Toggle card selection ──
    private fun toggleCardSelection(index: Int) {
        if (!PlayerTurn) return
        val current = uiSelectedIndices.toMutableSet()
        if (current.contains(index)) current.remove(index) else current.add(index)
        uiSelectedIndices = current
        CardArray.clear()
        current.forEach { i -> if (i < Player.size) CardArray.add(Player[i]) }
    }

    // ── Discard ──
    private fun discard() {
        if (CardArray.isEmpty() || !PlayerTurn || discarded) return
        if (CardArray.size == 1 || set(CardArray)) {
            var currRank: Card.Rank? = null
            if (!OpenCard.isEmpty()) currRank = OpenCard.last.rank()
            discarded = true

            for (c in CardArray) {
                if (c.rank() == currRank) sameRank = true
                OpenCard.add(c)
            }
            for (c in CardArray) Player.remove(c)
            CardArray.clear()
            uiSelectedIndices = emptySet()

            syncUi()

            if (drawn || sameRank) {
                PlayerTurn = false
                uiIsPlayerTurn = false
                uiStatus = "Cards discarded. Computer's turn…"
                computerTurn()
            } else {
                uiStatus = "Now draw a card from the deck or open pile."
            }
        } else {
            uiStatus =
                "Invalid discard. Single card, same-rank multiples, or 3+ same-suit sequence only."
        }
    }

    // ── Validate discard ──
    private fun set(DCards: LinkedList<Card>): Boolean {
        val rank = DCards.first.rank()
        var i = 1
        while (i < DCards.size) { if (DCards[i].rank() != rank) break; i++ }
        if (i != DCards.size) {
            if (DCards.size != 3) return false
            val suit = DCards.first.suit()
            i = 1
            while (i < DCards.size) { if (DCards[i].suit() != suit) break; i++ }
            if (i != DCards.size) return false
            val ranks = Array(3) { j -> DCards[j].rank()!! }
            ranks.sort()
            if (!(Card.next(ranks[0]) == ranks[1] && Card.next(ranks[1]) == ranks[2])) return false
        }
        return true
    }

    // ── Reshuffle ──
    private fun reshuffleDeck() {
        val top = OpenCard.last
        cardDeck.addAll(OpenCard.dropLast(1))
        OpenCard.clear(); OpenCard.add(top)
        Collections.shuffle(cardDeck)
        uiStatus = "Deck exhausted — reshuffling open pile."
    }

    // ── Computer turn ──
    private fun computerTurn() {
        Thread {
            Thread.sleep(1500)
            runOnUiThread {
                if (Declared) return@runOnUiThread

                val openTop = if (!OpenCard.isEmpty()) OpenCard.last else null
                val drew = if (openTop != null && openTop.value() < 5) {
                    OpenCard.remove(openTop); openTop
                } else if (cardDeck.isNotEmpty()) {
                    cardDeck.removeAt(0)
                } else null

                if (drew != null) Opponent.add(drew)

                val best = Best(Opponent)
                for (c in best) {
                    Opponent.remove(c); OpenCard.add(c)
                }

                if (Points(Opponent) <= 8 && Math.random() < 0.35) {
                    computerDeclares(); return@runOnUiThread
                }

                PlayerTurn = true
                discarded = false; drawn = false; sameRank = false
                completedTurns++
                syncUi()
                uiIsPlayerTurn = true
                uiCanDeclare = completedTurns >= 2
                uiStatus = "Your turn — tap the deck or open card to draw."
            }
        }.start()
    }

    // ── Computer declares ──
    private fun computerDeclares() {
        Declared = true
        val compPts = Points(Opponent)
        val playerPts = Points(Player)
        uiComputerHandPoints = compPts
        uiIsPlayerTurn = false
        uiCanDeclare = false
        syncUi()
        uiStatus = when {
            compPts < playerPts -> "Computer declares! Computer: $compPts pts vs Your: $playerPts pts. Computer wins!"
            playerPts < compPts -> "Computer declares — but you had fewer points! You: $playerPts vs Computer: $compPts. You win!"
            else -> "Computer declares — it's a tie! Both have $compPts points."
        }
        uiPhase = GamePhase.IDLE
    }

    // ── Player declares ──
    private fun declareNow() {
        if (!uiCanDeclare || Declared) return
        Declared = true
        val playerPts = Points(Player)
        val compPts = Points(Opponent)
        uiComputerHandPoints = compPts
        uiIsPlayerTurn = false
        uiCanDeclare = false
        syncUi()
        uiStatus = when {
            playerPts < compPts -> "You declare! You: $playerPts pts vs Computer: $compPts pts. You win!"
            compPts < playerPts -> "You declare — Computer had fewer points. You: $playerPts vs Computer: $compPts. Computer wins!"
            else -> "Tie! Both have $playerPts points."
        }
        uiPhase = GamePhase.IDLE
    }

    // ── Score calculation ──
    private fun Points(cards: LinkedList<Card>): Int =
        cards.sumOf { if (it.rank() != jokerRank) it.value() else 0 }

    // ── Computer strategy ──
    private fun Best(cards: LinkedList<Card>): LinkedList<Card> {
        val temp = LinkedList(cards)
        val temp1 = LinkedList<Card>();
        val temp2 = LinkedList<Card>();
        val temp3 = LinkedList<Card>()
        var value1 = 0;
        var value2 = 0;
        var value3 = 0
        var currRank = -1;
        var highRank = -1;
        var highValue = 0

        if (!OpenCard.isEmpty()) currRank = OpenCard.last.rank()?.ordinal ?: -1

        for (c in temp)
            if (currRank != -1 && c.rank()?.ordinal == currRank && c.rank() != jokerRank) {
                temp1.add(c); value1 += c.value()
            }
        for (c in temp1) temp.remove(c)
        if (value1 != 0 && !OpenCard.isEmpty()) value1 += OpenCard.last.value()

        for (c in temp)
            if (c.value() > highValue && c.rank() != jokerRank) {
                highValue = c.value(); highRank = c.rank()?.ordinal ?: -1
            }
        var i = 0
        while (i < temp.size) {
            if (temp[i].rank()?.ordinal == highRank && temp[i].rank() != jokerRank) {
                temp2.add(temp[i]); value2 += temp[i].value()
            }
            i++
        }
        for (c in temp2) temp.remove(c)

        highValue = 0; highRank = -1
        for (c in temp)
            if (c.value() > highValue && c.rank() != jokerRank) {
                highValue = c.value(); highRank = c.rank()?.ordinal ?: -1
            }
        i = 0
        while (i < temp.size) {
            if (temp[i].rank()?.ordinal == highRank && temp[i].rank() != jokerRank) {
                temp3.add(temp[i]); value3 += temp[i].value()
            }
            i++
        }

        if (value1 < value2) return if (value3 > value2) temp3 else temp2
        return if (value3 > value1) temp3 else temp1
    }

    // ── Sync game state → UI state ──
    private fun syncUi() {
        uiPlayerHand = Player.toList()
        uiPlayerHandPoints = Points(Player)
        uiComputerCardCount = Opponent.size
        uiOpenCard = if (!OpenCard.isEmpty()) OpenCard.last else null
        uiJokerCard = jokerCard
        uiJokerRank = jokerRank
        uiDeckCount = cardDeck.size
        if (!Declared) uiComputerHandPoints = null  // hide until declared
    }
}
