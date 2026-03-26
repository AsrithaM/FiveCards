package com.apps.asritha.fivecards

import java.util.LinkedList

class Card(
    private val cardRank: Rank?,
    private val cardSuit: Suit?,
    private val drawableId: Int,
    private val cardValue: Int
) {
    enum class Rank { ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING }
    enum class Suit { CLUBS, DIAMONDS, HEARTS, SPADES }

    fun rank(): Rank? = cardRank
    fun suit(): Suit? = cardSuit
    fun id(): Int = drawableId
    fun value(): Int = cardValue

    companion object {
        private val vals: Array<Rank> = Rank.values()
        val deck: LinkedList<Card> = LinkedList()

        init {
            var i = 0
            for (suit in Suit.values()) {
                for (rank in Rank.values()) {
                    var x = rank.ordinal
                    if (x == 10 || x == 11 || x == 12) x = 9
                    deck.add(Card(rank, suit, i, x + 1))
                    i++
                }
            }
            deck.add(Card(null, null, 52, 0))
            deck.add(Card(null, null, 53, 0))
        }

        fun next(r: Rank): Rank = vals[(r.ordinal + 1) % vals.size]

        fun newDeck(): LinkedList<Card> = LinkedList(deck)
    }
}
