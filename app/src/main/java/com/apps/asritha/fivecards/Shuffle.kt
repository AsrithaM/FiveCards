package com.apps.asritha.fivecards

class Shuffle : Comparator<Card> {
    override fun compare(c1: Card, c2: Card): Int {
        return if (c1.rank()!!.ordinal > c2.rank()!!.ordinal) 1 else -1
    }
}
