/*
 * Jonah Singer
 * jrs2338
 * Player: Class that controls player object and player methods
 */

import java.util.ArrayList;
import java.util.Collections;
public class Player {

    private ArrayList<Card> hand = new ArrayList<>();
    private double bankroll;
    private double bet;

    //method to create a player with bankroll and bet
    public Player(){
        bankroll = 50;
        bet = 0;
    }

    //method that returns the hand
    public ArrayList<Card> getHand(){
        return this.hand;
    }

    //method to add Card c to player's hand
    public void addCard(Card c){
        hand.add(new Card(c.getSuit(),c.getRank()));
    }

    //method to check if player has Card c
    public boolean hasCard(Card c){
        for (Card card : hand) {
            if (card.getSuit() == c.getSuit() &&
                    card.getRank() == c.getRank()) {
                return true;
            }
        }
        return false;
    }

    //method to remove card from players hand
    public void removeCard(Card c){
        // remove the card c from the player's hand
        for (int i=0;i<hand.size();i++) {
            if (hand.get(i).getSuit()==c.getSuit() &&
                    hand.get(i).getRank()==c.getRank()){
                hand.remove(i);
            }
        }
    }

    /*
     * method to remove card from players hand
     * .contains and .remove calls .equals
     * .equals is overriden to make it work
     */
    public void removeCard2(Card c){
        hand.remove(c);
    }

    //sets bet and updates bankroll
    public void bets(double amt){
        bet = amt;
        bankroll -= amt;
    }

    //method updates bankroll after game is played
    public void winnings(double odds){
        //	adjust bankroll if player wins
        bankroll = bankroll + (odds*bet);
    }

    //method returns value of bankroll
    public double getBankroll(){
        return bankroll;
    }

    //method to print player's hand
    public void showHand(){
        if (hand.size()>0){
            System.out.println(hand);
        }
    }

    //method to sort hand
    public void sortHand(){
        Collections.sort(hand);
    }

    //method to clear hand
    public void clearHand(){
        hand.removeAll(hand);
    }

}