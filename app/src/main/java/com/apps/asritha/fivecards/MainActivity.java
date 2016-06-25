package com.apps.asritha.fivecards;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


public class MainActivity extends Activity {

    private Card AllCards,Joker;
    private LinkedList<Card> Deck;
    ImageButton opencard,joker,deck;
    ImageButton[] card=new ImageButton[6];
    ImageButton[] c=new ImageButton[5];
    Button startButton;
    Card.Rank jokerRank;
    Button discard,draw,declare;
    float scaleX,scaleY;
    TextView instruction;
    boolean PlayerTurn;
    boolean TimeToDiscard;
    boolean discarded;
    boolean drawn;
    boolean sameRank;
    boolean Declared;
    public int[] cards = new int[]{R.drawable.ace_of_clubs, R.drawable.clubs_2, R.drawable.clubs_3, R.drawable.clubs_4, R.drawable.clubs_5,
            R.drawable.clubs_6, R.drawable.clubs_7, R.drawable.clubs_8, R.drawable.clubs_9, R.drawable.clubs_10,  R.drawable.jack_of_clubs2,
            R.drawable.queen_of_clubs2, R.drawable.king_of_clubs2, R.drawable.ace_of_diamonds, R.drawable.diamonds_2, R.drawable.diamonds_3,
            R.drawable.diamonds_4, R.drawable.diamonds_5, R.drawable.diamonds_6, R.drawable.diamonds_7, R.drawable.diamonds_8, R.drawable.diamonds_9,
            R.drawable.diamonds_10, R.drawable.jack_of_diamonds2, R.drawable.queen_of_diamonds2, R.drawable.king_of_diamonds2, R.drawable.ace_of_hearts,
            R.drawable.hearts_2, R.drawable.hearts_3, R.drawable.hearts_4, R.drawable.hearts_5, R.drawable.hearts_6, R.drawable.hearts_7,
            R.drawable.hearts_8, R.drawable.hearts_9, R.drawable.hearts_10,R.drawable.jack_of_hearts2, R.drawable.queen_of_hearts2, R.drawable.king_of_hearts2,
            R.drawable.ace_of_spades, R.drawable.spades_2, R.drawable.spades_3, R.drawable.spades_4, R.drawable.spades_5, R.drawable.spades_6,
            R.drawable.spades_7, R.drawable.spades_8, R.drawable.spades_9, R.drawable.spades_10, R.drawable.jack_of_spades2,
            R.drawable.queen_of_spades2, R.drawable.king_of_spades2, R.drawable.black_joker, R.drawable.red_joker};
    Random rand = new Random();
    ArrayList<Integer> DiscardCards = new ArrayList<>();
    LinkedList<Card> Player = new LinkedList<>();
    LinkedList<Card> Opponent = new LinkedList<>();
    LinkedList<Card> OpenCard = new LinkedList<>();
    LinkedList<Card> CardArray = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Deck = AllCards.newDeck();
        discard = (Button) findViewById(R.id.discard);
        declare = (Button) findViewById(R.id.declare);
        instruction=(TextView) findViewById(R.id.instruction);
        instruction.setText("Press start to start the game");
        discard.setVisibility(View.INVISIBLE);
        declare.setVisibility(View.INVISIBLE);
        card[0] = (ImageButton) findViewById(R.id.button1);
        card[1] = (ImageButton) findViewById(R.id.button2);
        card[2] = (ImageButton) findViewById(R.id.button3);
        card[3] = (ImageButton) findViewById(R.id.button4);
        card[4] = (ImageButton) findViewById(R.id.button5);
        card[5] = (ImageButton) findViewById(R.id.button6);
        c[0] = (ImageButton) findViewById(R.id.c1);
        c[1] = (ImageButton) findViewById(R.id.c2);
        c[2] = (ImageButton) findViewById(R.id.c3);
        c[3] = (ImageButton) findViewById(R.id.c4);
        c[4] = (ImageButton) findViewById(R.id.c5);
        opencard = (ImageButton) findViewById(R.id.opencard);
        joker = (ImageButton) findViewById(R.id.Joker);
        joker.setEnabled(false);
        deck = (ImageButton) findViewById(R.id.deck);
        startButton=(Button)findViewById(R.id.start);
        startButton.setText("START");
        for(int i=0;i<5;i++)
            card[i].setImageResource(R.drawable.back);
        card[5].setVisibility(View.INVISIBLE);
        opencard.setVisibility(View.INVISIBLE);
        joker.setVisibility(View.INVISIBLE);
    }
    public void start(View v)
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(
                        new Runnable() {
                    @Override
                    public void run() {
                        Deck=AllCards.newDeck();
                        while(!Player.isEmpty())
                        {
                            Player.removeFirst();
                        }
                        while(!Opponent.isEmpty())
                        {
                            Opponent.removeFirst();
                        }
                        while(!OpenCard.isEmpty())
                        {
                            OpenCard.removeFirst();
                        }

                        Collections.shuffle(Deck);
                        for(int i=0; i<5; i++)
                        {
                            Player.add(Deck.get(0));
                            Deck.remove(0);
                        }

                        for(int i=5; i<10; i++)
                        {
                            Opponent.add(Deck.get(0));
                            Deck.remove(0);
                        }

                        OpenCard.add(Deck.get(0));
                        Deck.remove(0);

                        Joker = Deck.get(0);
                        jokerRank=Joker.rank();
                        Deck.remove(0);

                        card[0].setImageResource(cards[Player.get(0).id()]);
                        card[1].setImageResource(cards[Player.get(1).id()]);
                        card[2].setImageResource(cards[Player.get(2).id()]);
                        card[3].setImageResource(cards[Player.get(3).id()]);
                        card[4].setImageResource(cards[Player.get(4).id()]);
                        for(int i=0;i<5;i++)
                            c[i].setImageResource(R.drawable.back);
                        instruction.setText("OPEN CARD");
                        opencard.setVisibility(View.VISIBLE);
                        opencard.setImageResource(cards[OpenCard.getLast().id()]);
                        instruction.setText("JOKER");
                        joker.setVisibility(View.VISIBLE);
                        joker.setImageResource(cards[Joker.id()]);
                        scaleX=card[0].getScaleX();
                        scaleY=card[0].getScaleY();
                        PlayerTurn=true;
                        instruction.setText("Your Turn! Draw a card from the deck or take the open card.(Or discard a card?)");
                        TimeToDiscard=false;
                        discarded=false;
                        drawn=false;
                        sameRank=false;
                        Declared=false;
                        for(int i=0;i<6;i++)
                            card[i].setEnabled(true);
                        opencard.setEnabled(true);
                        deck.setEnabled(true);
                        CardDisplay();
                        ShowComputerCards();
                        startButton.setText("RESTART");
                    }
                });

            }
        });
        thread.start();
    }

    public int Points(LinkedList<Card> Cards)
    {
        int i,value=0;
        for(i=0;i<Cards.size();i++)
        {
            if(Cards.get(i).rank()!=jokerRank)
                value+=Cards.get(i).value();
        }
        return value;
    }
    public LinkedList<Card> Best(LinkedList<Card> Cards)
    {
        LinkedList<Card> temp=new LinkedList<>(Cards);
        LinkedList<Card> temp1=new LinkedList<>();
        LinkedList<Card> temp2=new LinkedList<>();
        LinkedList<Card> temp3=new LinkedList<>();
        int value1=0,value2=0,value3=0;
        int i;
        int currRank=-1,highRank=-1;
        int highValue=0;
        if(OpenCard.size()>0)
            currRank=OpenCard.getLast().rank().ordinal();
        for(i=0; i<temp.size()&&currRank!=-1; i++ ) {
            if (temp.get(i).rank().ordinal() == currRank && temp.get(i).rank()!=jokerRank) {
                temp1.add(temp.get(i));
                value1+=temp.get(i).value();
            }
        }
        for(i=0;i<temp1.size();i++)
        {
            temp.remove(temp1.get(i));
        }
        if(value1!=0)
        {
            value1+= OpenCard.getLast().value();
        }
        for(i=0;i<temp.size();i++)
            if(temp.get(i).value()>highValue && temp.get(i).rank()!=jokerRank)
            {
                highValue=temp.get(i).value();
                highRank=temp.get(i).rank().ordinal();
            }
        for(i=0; i<temp.size()&&temp.size()!=0; i++ ) {
            if (temp.get(i).rank().ordinal() == highRank && temp.get(i).rank()!=jokerRank) {
                temp2.add(temp.get(i));
                value2+=temp.get(i).value();
            }
        }
        for(i=0;i<temp2.size();i++)
        {
            temp.remove(temp2.get(i));
        }
        highValue=0;
        highRank=-1;
        for(i=0;i<temp.size();i++)
            if(temp.get(i).value()>highValue && temp.get(i).rank()!=jokerRank)
            {
                highValue=temp.get(i).value();
                highRank=temp.get(i).rank().ordinal();
            }
        for(i=0; i<temp.size()&&temp.size()!=0; i++ ) {
            if (temp.get(i).rank().ordinal() == highRank && temp.get(i).rank()!=jokerRank) {
                temp3.add(temp.get(i));
                value3+=temp.get(i).value();
            }
        }
        for(i=0;i<temp3.size();i++)
        {
            temp.remove(temp3.get(i));
        }
        Log.d("Oltra",""+temp1.size()+" "+value1+" "+temp2.size()+" "+value2+" "+temp3.size()+" "+value3+" ");

        if(value1<value2)
            return value3>value2?temp3:temp2;
        return value3>value1?temp3:temp1;
    }

    public void computerTurn()
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                }catch(Exception e)
                {
                    Log.e("can't sleep Exception",e.getMessage());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(!PlayerTurn&&!Declared)
                        {   if(Points(Opponent)<=5)
                                declareNow();
                            int currRank=-1;
                            if(OpenCard.size()>0)
                                currRank=OpenCard.getLast().rank().ordinal();
                            Log.d("size before",""+Opponent.size());
                            LinkedList<Card> temp=Best(Opponent);
                            Log.d("size after",""+Opponent.size());
                            Log.d("Oltra 2",""+temp.size());
                            if(temp.size()>0&&temp.get(0).rank().ordinal()!=currRank&&currRank!=-1)
                            {

                                Log.d("Oltra 3",""+OpenCard.getLast().value());
                                if(OpenCard.getLast().value()<5) {
                                    Card temp1= OpenCard.getLast();
                                    Opponent.add(temp1);
                                    OpenCard.remove(temp1);
                                    Log.d("size open",""+Opponent.size());
                                }
                                else
                                {
                                    Opponent.add(Deck.get(0));
                                    Deck.remove(0);
                                    Log.d("size deck",""+Opponent.size());
                                }
                            }
                            int i;
                            for(i=0;i<temp.size();i++)
                            {
                                Opponent.remove(temp.get(i));
                                OpenCard.add(temp.get(i));
                            }
                            opencard.setVisibility(View.VISIBLE);
                            opencard.setImageResource(cards[OpenCard.getLast().id()]);
                            Log.d("size lose",""+Opponent.size());

                            if(Opponent.size()>0) {

                                PlayerTurn = true;
                                TimeToDiscard = false;
                                discarded = false;
                                drawn = false;
                                sameRank = false;

                                for (i = 0; i < 6; i++)
                                    card[i].setEnabled(true);
                                opencard.setEnabled(true);
                                deck.setEnabled(true);
                                if(Points(Player)<10)
                                    declare.setVisibility(View.VISIBLE);
                                ShowComputerCards();
                                instruction.setText("Your Turn! Draw a card from the deck or take the open card.(Or discard a card?)");
                            }
                            else
                            {    declareNow();
                            }
                        }
                    }
                });
            }
        });
        thread.start();


    }
    public void ChooseToDiscard(View v)
    {
        if(PlayerTurn) {
            discard.setVisibility(View.VISIBLE);
            v.setScaleX(v.getScaleX() * 1.1f);
            v.setScaleY(v.getScaleY() * 1.1f);
            int[] IDs=new int[]{R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6};
            int i;
            for(i=0;i<6;i++) {
                if (v.getId() == IDs[i]) {
                    if (!CardArray.contains(Player.get(i))) {
                        discard.setVisibility(View.VISIBLE);
                        v.setScaleX(v.getScaleX() * 1.1f);
                        v.setScaleY(v.getScaleY() * 1.1f);
                        CardArray.add(Player.get(i));
                    }
                    else{
                        CardArray.remove(Player.get(i));
                        v.setScaleX(scaleX);
                        v.setScaleY(scaleY);
                        if(CardArray.isEmpty())
                            discard.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }

    }

    public boolean set(LinkedList<Card> DCards)
    {
        int i,j;
        Card.Rank rank;
        Card.Suit suit;
        rank=DCards.getFirst().rank();
        for(i=1;i<DCards.size();i++)
        {
            if(DCards.get(i).rank()!=rank)
                break;
        }
        if(i!=DCards.size())
        {
            if(DCards.size()!=3)
                return false;
            suit=DCards.getFirst().suit();
            for(i=1;i<DCards.size();i++)
            {
                if(DCards.get(i).suit()!=suit)
                    break;
            }
            if(i!=DCards.size())
                return false;
            else
            {
                Card.Rank[] ranks=new Card.Rank[3];
                for(j=0;j<3;j++)
                    ranks[j]=DCards.get(j).rank();
                Arrays.sort(ranks);
                if(!(Card.next(ranks[0])==ranks[1]&&Card.next(ranks[1])==ranks[2]))
                    return false;
            }
        }
        return true;
    }

    public void ReshuffleDeck()
    {
        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                deck.setVisibility(View.INVISIBLE);
                opencard.setVisibility(View.INVISIBLE);
                instruction.setText(instruction.getText()+"\n\nReshuffling Open Cards");
                try {
                    Thread.sleep(2500);
                }catch(Exception e)
                {
                    Log.e("can't sleep Exception",e.getMessage());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(Deck.size()==0)
                        {
                            while (!OpenCard.isEmpty())
                            {
                                Deck.add(OpenCard.get(0));
                                OpenCard.remove(0);
                            }
                            Collections.shuffle(Deck);
                            OpenCard.add(Deck.get(0));
                            Deck.remove(0);
                            deck.setVisibility(View.VISIBLE);
                            opencard.setImageResource(cards[OpenCard.getLast().id()]);
                            opencard.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });
        thread.start();
    }

    public void discard(View v)
    {
        int i;
        if(!CardArray.isEmpty()&&CardArray.size()==1||set(CardArray))
        {
            opencard.setVisibility(View.VISIBLE);
            TimeToDiscard=false;
            if(!drawn) {
                discarded = true;
            }
            Card.Rank currRank=null;
            if(OpenCard.size()>0)
                currRank=OpenCard.getLast().rank();
            discard.setVisibility(View.INVISIBLE);
            for(i=0;i<6;i++)
                card[i].setEnabled(false);
            for(i = 0; i<CardArray.size(); i++ )
            {
                if(CardArray.get(i).rank()==currRank)
                    sameRank=true;
                OpenCard.add(CardArray.get(i));
            }
            for(i=0; i<CardArray.size(); i++)
            {
                Player.remove(CardArray.get(i));
            }

        opencard.setImageResource(cards[OpenCard.getLast().id()]);
            if(drawn)
            {
                PlayerTurn = false;
                instruction.setText("Cards Discarded. Computer Turn!");
                deck.setEnabled(false);
                opencard.setEnabled(false);
                computerTurn();
            }
            else if(sameRank)
            {
                PlayerTurn = false;
                instruction.setText("You discarded a card of the same rank as the top card. Computer Turn!");
                deck.setEnabled(false);
                opencard.setEnabled(false);
                computerTurn();
            }
            else
            {
                opencard.setEnabled(false);
                instruction.setText("Draw a card from the deck.");
            }
        }
        else
        {
            instruction.setText("Those cards cannot be discarded. Choose cards again.");
            for(i=0;i<6;i++)
                card[i].setEnabled(true);
        }

        CardDisplay();
        int size=CardArray.size();
        for(i = 0; i<size; i++)
        {
            CardArray.remove(0);
        }
        //Log.d("Card Array Size",""+CardArray.size());

    }

    public void draw(View v)
    {
        if(PlayerTurn&&Player.size()<6)
        {
            Player.add(Deck.get(0));
            Deck.remove(0);
            CardDisplay();
            if(Deck.size()==0)
            {
                ReshuffleDeck();
            }
            if(!discarded)
            {
                drawn=true;
                TimeToDiscard = true;
                instruction.setText("Select card(s) to discard.");
            }
            else
            {
                PlayerTurn=false;
                instruction.setText("Computer Turn!");
                computerTurn();
            }
            deck.setEnabled(false);
            opencard.setEnabled(false);
        }
        else
        {
            instruction.setText("You can only draw one card. Select card(s) to discard.");
        }
    }

    public void declareNow()
    {
        Declared=true;
        int c=Points(Opponent);
        int p=Points(Player);
        String text="Computer: "+c+" Player: "+p;
        if(c>p)
            text+="\nYOU WIN!";
        else if(c<p)
            text+="\nCOMPUTER WINS!";
        else
            text+="\nIT'S A DRAW";
        instruction.setText(text);
        int i;
        for (i = 0; i < 6; i++)
            card[i].setEnabled(false);
        opencard.setEnabled(false);
        deck.setEnabled(false);
        discard.setVisibility(View.INVISIBLE);
        declare.setVisibility(View.INVISIBLE);
        DisplayComputerCards();
    }

    public void declare(View v)
    {
        declareNow();
    }

    public void CardDisplay()
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int i;
                        for(i=0;i<Player.size();i++)
                        {
                            card[i].setVisibility(View.VISIBLE);
                            card[i].setImageResource(cards[Player.get(i).id()]);
                            card[i].setScaleX(scaleX);
                            card[i].setScaleY(scaleY);
                        }
                        for(;i<6;i++)
                        {
                            card[i].setVisibility(View.INVISIBLE);
                            card[i].setScaleX(scaleX);
                            card[i].setScaleY(scaleY);
                        }
                    }
                });

            }
        });
        thread.start();
    }

    public void ShowComputerCards()
    {
        int i;
        for(i=0;i<Opponent.size();i++)
        {
            c[i].setVisibility(View.VISIBLE);
        }
        for(;i<5;i++)
        {
            c[i].setVisibility(View.INVISIBLE);
        }
    }
    public void DisplayComputerCards()
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int i;
                        for(i=0;i<Opponent.size();i++)
                        {
                            c[i].setVisibility(View.VISIBLE);
                            c[i].setImageResource(cards[Opponent.get(i).id()]);
                        }
                        for(;i<5;i++)
                        {
                            c[i].setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });
        thread.start();
    }

    public void chooseopencard(View v)
    {
        if(PlayerTurn&&Player.size()<6)
        {
            deck.setEnabled(false);
            opencard.setEnabled(false);
            Card temp = OpenCard.getLast();
            Player.add(temp);
            OpenCard.remove(temp);
            if(OpenCard.size()>0)
                opencard.setImageResource(cards[OpenCard.getLast().id()]);
            else
                opencard.setVisibility(View.INVISIBLE);
            CardDisplay();
            if(!discarded)
            {
                TimeToDiscard = true;
                instruction.setText("Select card(s) to discard.");
                drawn=true;
            }
            else
            {
                PlayerTurn=false;
                instruction.setText("Computer Turn!");
                computerTurn();
            }
            deck.setEnabled(false);
            opencard.setEnabled(false);
        }
    }
}
