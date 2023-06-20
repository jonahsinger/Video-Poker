/*
 * Jonah Singer
 * jrs2338
 * Card: Class for defining and comparing card objects
 */

public class Card implements Comparable<Card>{

    private int suit; // use integers 1-4 to encode the suit
    private int rank; // use integers 1-13 to encode the rank

    public Card(int s, int r){
        //make a card with suit s and value v
        this.suit = s;
        this.rank = r;
    }

    /*
     * This method compares two card objects' ranks and
     * returns a negative number if the card object that
     * the method was invoked on is less than the parameter
     * card object and a positive number if it is
     * larger. The tiebreaker is the numeric value assigned
     * to each suit
     */
    public int compareTo(Card c){
        // use this method to compare cards, so they
        // may be easily sorted

        if (rank != c.getRank()){//are ranks the same?
            if(rank == 1){//if ace return positive
                return 1;
            }
            else if(c.getRank() == 1){//is other an ace?
                return -1;
            }
            else{ //return difference in ranks
                return (rank - c.getRank());
            }
        }
        else{ //compare suits if tie in rank
            return (suit - c.getSuit());
        }

    }

    //overrides equals to easily see if cards are the same
    public boolean equals(Object obj){
        if (obj instanceof Card){
            return (((Card) obj).getSuit() == this.suit)&&
                    (((Card) obj).getRank() == this.rank);
        }
        return false;
    }

    //toString returns String for card objects
    public String toString(){
        // use this method to easily return a Card object
        char[] suitsArray = {'c','d','h','s'};
        return String.format("%c%d", suitsArray[suit-1], rank);
    }

    //method to get rank of card
    public int getRank(){
        return rank;
    }

    //jonah method to get suit of card
    public int getSuit(){
        return suit;
    }

}