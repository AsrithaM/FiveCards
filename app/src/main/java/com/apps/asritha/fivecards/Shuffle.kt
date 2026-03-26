package com.apps.asritha.fivecards;

import java.util.Comparator;

/**
 * Created by Asritha on 21-05-2016.
 */
public class Shuffle implements Comparator<Card> {

    @Override
    public int compare(Card c1, Card c2)
    {
        if(c1.rank().ordinal() > c2.rank().ordinal())
        {
            return 1;
        }

        else
            return -1;
    }
}

