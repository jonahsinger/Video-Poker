/*
 * Jonah Singer
 * jrs2338
 * Deck: Class to manage the deck of cards
 */

import java.util.Random;
import java.util.Arrays;
public class Deck {

    private Card[] cards = new Card[52];
    private int top; // the index of the top of the deck

    //method to make 52 card deck with nested for loop
    public Deck(){
        for (int s=1; s<5; s++){
            for (int r=1; r<14; r++){
                cards[((s-1)*13)+r-1] = new Card(s,r);
            }
        }
        top = 0;
    }

    // method to shuffle the deck
    public void shuffle(){
        Random r = new Random();
        for (int i = 51; i>0; i--){ //start at end of deck
            //pick a random index from 0 to i
            int j = r.nextInt(52);
            //Swap cards[i] with card at index
            Card tempCard = cards[i];
            cards[i] = cards[j];
            cards[j] = tempCard;
        }
    }

    //method deals cards returns a card and increments top
    public Card deal(){
        // deal the top card in the deck
        if (top < 51) {
            top++; //increment location of top card
            return cards[top-1];
        }
        else{ //if gone top is at bottom of deck
            shuffle();
            top = 1;
            return cards[0];
        }
    }

    //this method sorts cards with Arrays.sort
    public void sort(){
        Arrays.sort(cards);
    }

    //method prints the deck for testing
    public void printDeck(){
        System.out.print("Deck: ");
        for(int i=0; i<52; i++){
            System.out.print(cards[i]);
        }
        System.out.println();
    }

    /*
     * method for the tester version of game
     * to move cards inputted from user to
     * above the top of the deck so that
     * the cards already in their hand will
     * not be delt to them
     */
    public void removeCard(Card c){
        Card tempCard;
        for(int i=0; i<52; i++){
            if (cards[i].compareTo(c)==0){
                tempCard = cards[top];
                cards[top] = c;
                cards[i] = tempCard;
                top++;
            }
        }
    }
}
