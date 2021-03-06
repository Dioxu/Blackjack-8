package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Jason Ye on 3/9/2016.
 */
public class Game {
    public ArrayList<Cards> deck = new ArrayList<Cards>();;
    public Player player;
    public Dealer dealer;
    public int state, playerScore, dealerScore, playerScoreSplit;
    public boolean playerBust, dealerBust, playerBustSplit;
    public int win, winSplit;
    //win = 0 [tie] | 1 [player wins] | 2 [dealer wins] | 3 [player wins with blackjack]


    public Game(){
        state = 1;
        buildDeck();
        shuffle();
        player = new Player();
        dealer = new Dealer();
		dealTwo();
        //play
    }


    public void play()
    {
        while(state == 1)
        {
            if(player.turnEnd == true)
            {
                playerBust = player.getBust();
                playerScore = player.valueTotal();
                dealer.less17(deck);
                dealerBust = dealer.getBust();
                dealerScore = dealer.valueTotal();

                //Normal hand
                if((dealerBust == false) && (playerBust == false))
                {
                    if((player.PlayerHand.size() == 2) && (playerScore == 21))
                    {
                        if((dealer.DealerHand.size() == 2) && (dealerScore == 21)){
                            win = 0;
                        } else {
                            win = 3;
                        }
                    } else if (playerScore > dealerScore)
                    {
                        win = 1;
                    } else if (dealerScore > playerScore)
                    {
                        win = 0;
                    }
                } else if ((dealerBust == true) && (playerBust == false))
                {
                    if((player.PlayerHand.size() == 2) && (playerScore == 21))
                    {
                        win = 3;
                    } else
                    {
                        win = 1;
                    }
                } else if ((playerBust == true) && (dealerBust == false))
                {
                    win = 2;
                } else
                {
                    win = 0;
                }

                //If player split
                if(player.split == 1)
                {
                    playerBustSplit = player.getBustSplit();
                    playerScoreSplit = player.valueTotalSplit();

                    if((dealerBust == false) && (playerBustSplit == false))
                    {
                        if((player.SplitHand.size() == 2) && (playerScoreSplit == 21))
                        {
                            if((dealer.DealerHand.size() == 2) && (dealerScore == 21)){
                                winSplit = 0;
                            } else {
                                winSplit = 3;
                            }
                        } else if (playerScoreSplit > dealerScore)
                        {
                            winSplit = 1;
                        } else if (dealerScore > playerScoreSplit)
                        {
                            winSplit = 0;
                        }
                    } else if ((dealerBust == true) && (playerBustSplit == false))
                    {
                        if((player.SplitHand.size() == 2) && (playerScoreSplit == 21))
                        {
                            winSplit = 3;
                        } else
                        {
                            winSplit = 1;
                        }
                    } else if ((playerBustSplit == true) && (dealerBust == false))
                    {
                        winSplit = 2;
                    } else
                    {
                        winSplit = 0;
                    }
                }

                state = 0;
            }
        }
        //Whatever we want to happen after the game ends
    }

    public void buildDeck(){
        
        for(int i = 1; i < 14; i++) {
            deck.add(new Cards(i, Suit.Clubs));
            deck.add(new Cards(i, Suit.Diamonds));
            deck.add(new Cards(i, Suit.Hearts));
            deck.add(new Cards(i, Suit.Spades));
        }
    }

    public void shuffle(){
        long shuffling = System.nanoTime();
        Collections.shuffle(deck, new Random(shuffling));
    }

    public void dealTwo() {
        if(player.bet())
        {
            for (int i = 0; i < 2; i++) {
                dealer.hit(deck);
                player.hit(deck);
            }
        }else {
            //Error Message
        }
    }
}
