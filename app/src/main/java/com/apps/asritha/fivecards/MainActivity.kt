package com.apps.asritha.fivecards

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import java.util.Collections
import java.util.LinkedList

class MainActivity : Activity() {

    private var jokerCard: Card? = null
    private var cardDeck: LinkedList<Card> = LinkedList()
    lateinit var openCard: ImageButton
    lateinit var joker: ImageButton
    lateinit var deck: ImageButton
    val card: Array<ImageButton?> = arrayOfNulls(6)
    val c: Array<ImageButton?> = arrayOfNulls(5)
    lateinit var startButton: Button
    var jokerRank: Card.Rank? = null
    lateinit var discardButton: Button
    lateinit var declareButton: Button
    var scaleX: Float = 0f
    var scaleY: Float = 0f
    lateinit var instruction: TextView
    var PlayerTurn: Boolean = false
    var TimeToDiscard: Boolean = false
    var discarded: Boolean = false
    var drawn: Boolean = false
    var sameRank: Boolean = false
    var Declared: Boolean = false

    val cards: IntArray = intArrayOf(
        R.drawable.ace_of_clubs, R.drawable.clubs_2, R.drawable.clubs_3, R.drawable.clubs_4, R.drawable.clubs_5,
        R.drawable.clubs_6, R.drawable.clubs_7, R.drawable.clubs_8, R.drawable.clubs_9, R.drawable.clubs_10, R.drawable.jack_of_clubs2,
        R.drawable.queen_of_clubs2, R.drawable.king_of_clubs2, R.drawable.ace_of_diamonds, R.drawable.diamonds_2, R.drawable.diamonds_3,
        R.drawable.diamonds_4, R.drawable.diamonds_5, R.drawable.diamonds_6, R.drawable.diamonds_7, R.drawable.diamonds_8, R.drawable.diamonds_9,
        R.drawable.diamonds_10, R.drawable.jack_of_diamonds2, R.drawable.queen_of_diamonds2, R.drawable.king_of_diamonds2, R.drawable.ace_of_hearts,
        R.drawable.hearts_2, R.drawable.hearts_3, R.drawable.hearts_4, R.drawable.hearts_5, R.drawable.hearts_6, R.drawable.hearts_7,
        R.drawable.hearts_8, R.drawable.hearts_9, R.drawable.hearts_10, R.drawable.jack_of_hearts2, R.drawable.queen_of_hearts2, R.drawable.king_of_hearts2,
        R.drawable.ace_of_spades, R.drawable.spades_2, R.drawable.spades_3, R.drawable.spades_4, R.drawable.spades_5, R.drawable.spades_6,
        R.drawable.spades_7, R.drawable.spades_8, R.drawable.spades_9, R.drawable.spades_10, R.drawable.jack_of_spades2,
        R.drawable.queen_of_spades2, R.drawable.king_of_spades2, R.drawable.black_joker, R.drawable.red_joker
    )

    var Player: LinkedList<Card> = LinkedList()
    var Opponent: LinkedList<Card> = LinkedList()
    var OpenCard: LinkedList<Card> = LinkedList()
    var CardArray: LinkedList<Card> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        cardDeck = Card.newDeck()
        discardButton = findViewById(R.id.discard)
        declareButton = findViewById(R.id.declare)
        instruction = findViewById(R.id.instruction)
        instruction.setText(R.string.msg_press_start)
        discardButton.visibility = View.INVISIBLE
        declareButton.visibility = View.INVISIBLE
        card[0] = findViewById(R.id.button1)
        card[1] = findViewById(R.id.button2)
        card[2] = findViewById(R.id.button3)
        card[3] = findViewById(R.id.button4)
        card[4] = findViewById(R.id.button5)
        card[5] = findViewById(R.id.button6)
        c[0] = findViewById(R.id.c1)
        c[1] = findViewById(R.id.c2)
        c[2] = findViewById(R.id.c3)
        c[3] = findViewById(R.id.c4)
        c[4] = findViewById(R.id.c5)
        openCard = findViewById(R.id.opencard)
        joker = findViewById(R.id.Joker)
        joker.isEnabled = false
        deck = findViewById(R.id.deck)
        startButton = findViewById(R.id.start)
        startButton.setText(R.string.btn_start)
        for (i in 0 until 5) card[i]!!.setImageResource(R.drawable.back)
        card[5]!!.visibility = View.INVISIBLE
        openCard.visibility = View.INVISIBLE
        joker.visibility = View.INVISIBLE
    }

    fun start(v: View) {
        Thread {
            runOnUiThread {
                cardDeck = Card.newDeck()
                Player.clear()
                Opponent.clear()
                OpenCard.clear()

                Collections.shuffle(cardDeck)
                for (i in 0 until 5) { Player.add(cardDeck[0]); cardDeck.removeAt(0) }
                for (i in 5 until 10) { Opponent.add(cardDeck[0]); cardDeck.removeAt(0) }

                OpenCard.add(cardDeck[0]); cardDeck.removeAt(0)
                jokerCard = cardDeck[0]
                jokerRank = jokerCard!!.rank()
                cardDeck.removeAt(0)

                card[0]!!.setImageResource(cards[Player[0].id()])
                card[1]!!.setImageResource(cards[Player[1].id()])
                card[2]!!.setImageResource(cards[Player[2].id()])
                card[3]!!.setImageResource(cards[Player[3].id()])
                card[4]!!.setImageResource(cards[Player[4].id()])
                for (i in 0 until 5) c[i]!!.setImageResource(R.drawable.back)
                instruction.setText(R.string.msg_open_card)
                openCard.visibility = View.VISIBLE
                openCard.setImageResource(cards[OpenCard.last.id()])
                instruction.setText(R.string.msg_joker)
                joker.visibility = View.VISIBLE
                joker.setImageResource(cards[jokerCard!!.id()])
                scaleX = card[0]!!.scaleX
                scaleY = card[0]!!.scaleY
                PlayerTurn = true
                instruction.setText(R.string.msg_your_turn)
                TimeToDiscard = false
                discarded = false
                drawn = false
                sameRank = false
                Declared = false
                for (i in 0 until 6) card[i]!!.isEnabled = true
                openCard.isEnabled = true
                deck.isEnabled = true
                CardDisplay()
                ShowComputerCards()
                startButton.setText(R.string.btn_restart)
            }
        }.start()
    }

    fun Points(Cards: LinkedList<Card>): Int {
        var value = 0
        for (i in 0 until Cards.size) {
            if (Cards[i].rank() != jokerRank) value += Cards[i].value()
        }
        return value
    }

    fun Best(Cards: LinkedList<Card>): LinkedList<Card> {
        val temp = LinkedList(Cards)
        val temp1 = LinkedList<Card>()
        val temp2 = LinkedList<Card>()
        val temp3 = LinkedList<Card>()
        var value1 = 0; var value2 = 0; var value3 = 0
        var currRank = -1; var highRank = -1; var highValue = 0

        if (!OpenCard.isEmpty()) currRank = OpenCard.last.rank()?.ordinal ?: -1

        for (i in 0 until temp.size) {
            if (currRank == -1) break
            if (temp[i].rank()?.ordinal == currRank && temp[i].rank() != jokerRank) {
                temp1.add(temp[i]); value1 += temp[i].value()
            }
        }
        for (card in temp1) temp.remove(card)
        if (value1 != 0) value1 += OpenCard.last.value()

        for (i in 0 until temp.size)
            if (temp[i].value() > highValue && temp[i].rank() != jokerRank) {
                highValue = temp[i].value(); highRank = temp[i].rank()?.ordinal ?: -1
            }
        var i = 0
        while (i < temp.size && !temp.isEmpty()) {
            if (temp[i].rank()?.ordinal == highRank && temp[i].rank() != jokerRank) {
                temp2.add(temp[i]); value2 += temp[i].value()
            }
            i++
        }
        for (card in temp2) temp.remove(card)

        highValue = 0; highRank = -1
        for (j in 0 until temp.size)
            if (temp[j].value() > highValue && temp[j].rank() != jokerRank) {
                highValue = temp[j].value(); highRank = temp[j].rank()?.ordinal ?: -1
            }
        i = 0
        while (i < temp.size && !temp.isEmpty()) {
            if (temp[i].rank()?.ordinal == highRank && temp[i].rank() != jokerRank) {
                temp3.add(temp[i]); value3 += temp[i].value()
            }
            i++
        }
        for (card in temp3) temp.remove(card)

        if (value1 < value2) return if (value3 > value2) temp3 else temp2
        return if (value3 > value1) temp3 else temp1
    }

    fun computerTurn() {
        Thread {
            try {
                Thread.sleep(2500)
            } catch (e: Exception) {
                Thread.currentThread().interrupt()
            }
            runOnUiThread {
                if (!PlayerTurn && !Declared) {
                    if (Points(Opponent) <= 5) declareNow()
                    var currRank = -1
                    if (!OpenCard.isEmpty()) currRank = OpenCard.last.rank()?.ordinal ?: -1
                    val temp = Best(Opponent)
                    if (!temp.isEmpty() && temp[0].rank()?.ordinal != currRank && currRank != -1) {
                        if (OpenCard.last.value() < 5) {
                            val temp1 = OpenCard.last
                            Opponent.add(temp1); OpenCard.remove(temp1)
                        } else {
                            Opponent.add(cardDeck[0]); cardDeck.removeAt(0)
                        }
                    }
                    for (i in 0 until temp.size) {
                        Opponent.remove(temp[i]); OpenCard.add(temp[i])
                    }
                    openCard.visibility = View.VISIBLE
                    openCard.setImageResource(cards[OpenCard.last.id()])

                    if (!Opponent.isEmpty()) {
                        PlayerTurn = true; TimeToDiscard = false
                        discarded = false; drawn = false; sameRank = false
                        for (i in 0 until 6) card[i]!!.isEnabled = true
                        openCard.isEnabled = true; deck.isEnabled = true
                        if (Points(Player) < 10) declareButton.visibility = View.VISIBLE
                        ShowComputerCards()
                        instruction.setText(R.string.msg_your_turn)
                    } else {
                        declareNow()
                    }
                }
            }
        }.start()
    }

    fun ChooseToDiscard(v: View) {
        if (PlayerTurn) {
            discardButton.visibility = View.VISIBLE
            v.scaleX *= 1.1f; v.scaleY *= 1.1f
            val IDs = intArrayOf(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6)
            for (i in 0 until 6) {
                if (v.id == IDs[i]) {
                    if (!CardArray.contains(Player[i])) {
                        discardButton.visibility = View.VISIBLE
                        v.scaleX *= 1.1f; v.scaleY *= 1.1f
                        CardArray.add(Player[i])
                    } else {
                        CardArray.remove(Player[i])
                        v.scaleX = scaleX; v.scaleY = scaleY
                        if (CardArray.isEmpty()) discardButton.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    fun set(DCards: LinkedList<Card>): Boolean {
        val rank = DCards.first.rank()
        var i = 1
        while (i < DCards.size) { if (DCards[i].rank() != rank) break; i++ }
        if (i != DCards.size) {
            if (DCards.size != 3) return false
            val suit = DCards.first.suit()
            i = 1
            while (i < DCards.size) { if (DCards[i].suit() != suit) break; i++ }
            if (i != DCards.size) return false
            else {
                val ranks = Array(3) { j -> DCards[j].rank()!! }
                ranks.sort()
                if (!(Card.next(ranks[0]) == ranks[1] && Card.next(ranks[1]) == ranks[2])) return false
            }
        }
        return true
    }

    fun ReshuffleDeck() {
        Thread {
            deck.visibility = View.INVISIBLE
            openCard.visibility = View.INVISIBLE
            instruction.setText(getString(R.string.msg_reshuffling, instruction.text))
            try {
                Thread.sleep(2500)
            } catch (e: Exception) {
                Thread.currentThread().interrupt()
            }
            runOnUiThread {
                if (cardDeck.isEmpty()) {
                    while (!OpenCard.isEmpty()) { cardDeck.add(OpenCard[0]); OpenCard.removeAt(0) }
                    Collections.shuffle(cardDeck)
                    OpenCard.add(cardDeck[0]); cardDeck.removeAt(0)
                    deck.visibility = View.VISIBLE
                    openCard.setImageResource(cards[OpenCard.last.id()])
                    openCard.visibility = View.VISIBLE
                }
            }
        }.start()
    }

    fun discard(v: View) {
        var i: Int
        if (CardArray.size == 1 || set(CardArray)) {
            openCard.visibility = View.VISIBLE
            TimeToDiscard = false
            discarded = !drawn
            var currRank: Card.Rank? = null
            if (!OpenCard.isEmpty()) currRank = OpenCard.last.rank()
            discardButton.visibility = View.INVISIBLE
            for (j in 0 until 6) card[j]!!.isEnabled = false
            i = 0
            while (i < CardArray.size) {
                if (CardArray[i].rank() == currRank) sameRank = true
                OpenCard.add(CardArray[i]); i++
            }
            i = 0
            while (i < CardArray.size) { Player.remove(CardArray[i]); i++ }

            openCard.setImageResource(cards[OpenCard.last.id()])
            if (drawn) {
                PlayerTurn = false
                instruction.setText(R.string.msg_cards_discarded)
                deck.isEnabled = false; openCard.isEnabled = false
                computerTurn()
            } else if (sameRank) {
                PlayerTurn = false
                instruction.setText(R.string.msg_same_rank_discard)
                deck.isEnabled = false; openCard.isEnabled = false
                computerTurn()
            } else {
                openCard.isEnabled = false
                instruction.setText(R.string.msg_draw_from_deck)
            }
        } else {
            instruction.setText(R.string.msg_invalid_discard)
            for (j in 0 until 6) card[j]!!.isEnabled = true
        }

        CardDisplay()
        val size = CardArray.size
        for (j in 0 until size) CardArray.removeAt(0)
    }

    fun draw(v: View) {
        if (PlayerTurn && Player.size < 6) {
            Player.add(cardDeck[0]); cardDeck.removeAt(0)
            CardDisplay()
            if (cardDeck.isEmpty()) ReshuffleDeck()
            if (!discarded) {
                drawn = true; TimeToDiscard = true
                instruction.setText(R.string.msg_select_to_discard)
            } else {
                PlayerTurn = false
                instruction.setText(R.string.msg_computer_turn)
                computerTurn()
            }
            deck.isEnabled = false; openCard.isEnabled = false
        } else {
            instruction.setText(R.string.msg_one_card_only)
        }
    }

    fun declareNow() {
        Declared = true
        val computerPoints = Points(Opponent)
        val playerPoints = Points(Player)
        val result = when {
            computerPoints > playerPoints -> getString(R.string.result_win, computerPoints, playerPoints)
            computerPoints < playerPoints -> getString(R.string.result_lose, computerPoints, playerPoints)
            else -> getString(R.string.result_draw, computerPoints, playerPoints)
        }
        instruction.text = result
        for (i in 0 until 6) card[i]!!.isEnabled = false
        openCard.isEnabled = false; deck.isEnabled = false
        discardButton.visibility = View.INVISIBLE
        declareButton.visibility = View.INVISIBLE
        DisplayComputerCards()
    }

    fun declare(v: View) { declareNow() }

    fun CardDisplay() {
        Thread { runOnUiThread {
            var i = 0
            while (i < Player.size) {
                card[i]!!.visibility = View.VISIBLE
                card[i]!!.setImageResource(cards[Player[i].id()])
                card[i]!!.scaleX = scaleX; card[i]!!.scaleY = scaleY
                i++
            }
            while (i < 6) {
                card[i]!!.visibility = View.INVISIBLE
                card[i]!!.scaleX = scaleX; card[i]!!.scaleY = scaleY
                i++
            }
        }}.start()
    }

    fun ShowComputerCards() {
        var i = 0
        while (i < Opponent.size) { c[i]!!.visibility = View.VISIBLE; i++ }
        while (i < 5) { c[i]!!.visibility = View.INVISIBLE; i++ }
    }

    fun DisplayComputerCards() {
        Thread { runOnUiThread {
            var i = 0
            while (i < Opponent.size) {
                c[i]!!.visibility = View.VISIBLE
                c[i]!!.setImageResource(cards[Opponent[i].id()])
                i++
            }
            while (i < 5) { c[i]!!.visibility = View.INVISIBLE; i++ }
        }}.start()
    }

    fun chooseOpenCard(v: View) {
        if (PlayerTurn && Player.size < 6) {
            deck.isEnabled = false; openCard.isEnabled = false
            val temp = OpenCard.last
            Player.add(temp); OpenCard.remove(temp)
            if (!OpenCard.isEmpty()) openCard.setImageResource(cards[OpenCard.last.id()])
            else openCard.visibility = View.INVISIBLE
            CardDisplay()
            if (!discarded) {
                TimeToDiscard = true
                instruction.setText(R.string.msg_select_to_discard)
                drawn = true
            } else {
                PlayerTurn = false
                instruction.setText(R.string.msg_computer_turn)
                computerTurn()
            }
            deck.isEnabled = false; openCard.isEnabled = false
        }
    }
}
