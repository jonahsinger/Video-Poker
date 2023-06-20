/*
 * Jonah Singer
 * jrs2338
 * Game: Class to set up and play the game
 */

import java.util.Scanner;
import java.util.ArrayList;
public class Game {

    private final Player p;
    private final Deck cards;


    public Game(String[] testHand){
        // c = clubs
        // d = diamonds
        // h = hearts
        // s = spades
        // 1-13 correspond to ace-king
        // example: s1 = ace of spades
        // example: test hand = {s1, s13, s12, s11, s10} = rf
        System.out.println("Test Game");
        p = new Player(); //makes player
        cards = new Deck(); //makes deck
        cards.shuffle(); //shuffles deck
        Card c;

        //adds cards to players hand
        for (String s : testHand) {
            c = strToCard(s);
            if (p.hasCard(c)) {//check if card is duplicate
                System.out.println("error: input hand has" +
                        " duplicate cards " + c);
                System.exit(1);
            } else {
                p.addCard(c);  // add c to player's hand
                cards.removeCard(c); // move c above top of Deck
            }
        }

    }

    //method to make deck and deal first 5 cards
    public Game(){
        System.out.println("Normal Game");
        p = new Player(); //makes new player
        cards = new Deck(); //makes deck
        cards.shuffle(); //shuffles deck
        Card c;
        //deals the cards
        for (int i = 0; i < 5; i++) {
            c = cards.deal();
            p.addCard(c);
        }
    }

    //method to play the game
    public void play(){
        //loops while bankroll is greater than zero
        while(p.getBankroll()>0){
            System.out.println("You have " +
                    p.getBankroll() + ", How much do you"+
                    " want to bet 1-5 enter"+
                    " any other number to quit");
            //takes in bet
            Scanner input = new Scanner(System.in);
            double bet=0;
            try{
                bet = input.nextDouble();
            }
            catch(Exception e){
                System.out.println("You entered not an Double. Bye!");
                System.exit(0);
            }
            //checks if bet is valid
            if (bet<=0 || bet > 5) {
                System.out.println("you bet either less than 0 or more"+
                        " than 5. Bye!");
                System.exit(0);
            }

            Card c;
            p.bets(bet); //places bet
            p.sortHand(); //sorts hand
            System.out.println("This is your hand:");
            p.showHand(); //shows hand
            System.out.println("Enter the number of cards"+
                    " you wish to swap [0-5]:");
            int numSwap=0;
            //tests to see is swap number is valid
            try{
                numSwap = input.nextInt();
                if (numSwap>5 || numSwap<0) {
                    System.out.println("this must be between 0" +
                            " and 5 cards, skipping...");
                    numSwap=0;
                }
            }
            //if setting equal to numSwap fails
            catch (Exception e) {
                System.out.println("must be an integer");
            }

            String swapCards;
            /*
             * Loops and swaps cards in players hand
             * does not let player cheat and see new
             * cards or swap a card twice
             */
            for (int i=0;i<numSwap;i++) {
                System.out.println("Enter cards to swap "+
                        "one at a time");
                System.out.println("you can either enter the"+
                        " suit-rank, e.g., "+ p.getHand().get(0)+
                        " or the index of the card in your"+
                        " current hand, 1 - "+p.getHand().size());
                swapCards = input.next();
                char suitChar = swapCards.charAt(0);
                if (swapCards.length()>1) { //e.g. s10 or c1 or d13
                    c = strToCard(swapCards); //calls strToCard
                }
                //if input is a one character number
                else if (suitChar=='1' || suitChar=='2' ||
                        suitChar=='3' || suitChar=='4'
                        || suitChar=='5' || suitChar=='0'){
                    //changes String to int
                    int cardNum=Character.getNumericValue(suitChar)-1;
                    //if to large
                    if (cardNum >= p.getHand().size()) {
                        System.out.println("too large, swapping"+
                                " last card");
                        cardNum = p.getHand().size()-1;
                    }
                    //if to small
                    else if (cardNum<0) {
                        System.out.println("too small, swapping"+
                                " first card");
                        cardNum = 0;
                    }
                    c = p.getHand().get(cardNum);
                }
                //anything else gets set to (0,0)
                else{
                    c = new Card(0,0);
                }
                //removes card from hand
                if (p.hasCard(c)) {
                    p.removeCard2(c);
                }
                //if card does not exist no swap occurs
                else {
                    System.out.println("You don't have card " +
                            c + " skipping...");
                }
                //shows user hand with card removed
                System.out.println("your hand is now:");
                p.showHand();
            }
            //deals cards to replace old ones
            while (p.getHand().size()<5) {
                c = cards.deal();
                p.addCard(c);
            }
            p.sortHand(); //sorts hand
            System.out.println("This is your new hand:");
            p.showHand(); //shows sorted hand
            //gets hand to check
            ArrayList<Card> hand = p.getHand();
            //calls check hand
            String handValue = checkHand(hand);
            //prints value of hand
            System.out.println(handValue);
            int winValue = switch (handValue) {
                case "Royal Flush" -> 250;
                case "Straight Flush" -> 50;
                case "Four of a Kind" -> 25;
                case "Full House" -> 6;
                case "Flush" -> 5;
                case "Straight" -> 4;
                case "Three of a Kind" -> 3;
                case "Two Pair" -> 2;
                case "Pair" -> 1;
                default -> 0;
            }; //variable for multiplier

            //switch case to set winValue depending on hand
            //calls winnings with winValue
            p.winnings(winValue);
            System.out.println("Bankroll is now: "
                    + p.getBankroll());
            /*
             * now clear the hand, shuffle,
             * and re-deal for a new round.
             */
            p.clearHand();
            cards.shuffle();
            for (int i=0;i<5;i++) {
                c = cards.deal();
                p.addCard(c);
            }
        }//while bankroll >0
    }//play

    //method to change a string into a card
    // must be in format [c,d,h,s][1-13] e.g. c1 or s10
    public Card strToCard(String str) {
        //gets rank
        int rank = Integer.parseInt(str.substring(1));
        int s;
        //gets suit
        switch (Character.toLowerCase(str.charAt(0))) {
            case 'c' -> s = 1;
            case 'd' -> s = 2;
            case 'h' -> s = 3;
            case 's' -> s = 4;
            default -> {
                s = 0; //error
                System.out.println("error reading card " + str);
                System.exit(1);
            }
        }
        //returns a card with proper rank and suit
        return new Card(s,rank);
    }

    // this method takes an ArrayList of cards
    // as input and then determine what evaluates to and
    // return that as a String
    public String checkHand(ArrayList<Card> hand){
        //starts at highest hand calls all check methods
        if (straightHand(hand)&&flushHand(hand)) {
            if (hand.get(4).getRank()==1 &&
                    hand.get(0).getRank()==10){
                return "Royal Flush";
            }
            else{
                return "Straight Flush";
            }
        }
        else if (fourKindHand(hand)){
            return "Four of a Kind";
        }
        else if (fullHouseHand(hand)){
            return "Full House";
        }
        else if (flushHand(hand)) {
            return "Flush";
        }
        else if (straightHand(hand)) {
            return "Straight";
        }
        else if (threeKindHand(hand)) {
            return "Three of a Kind";
        }
        else if (twoPairHand(hand)) {
            return "Two Pair";
        }
        else if (pairHand(hand)) {
            return "Pair";
        }
        else{
            return "No pair";
        }
    }

    //method to check for straight
    public boolean straightHand(ArrayList<Card> hand) {
        int[] r = ranksHand(hand);

        if (r[1]==r[0]+1 && r[2]==r[1]+1 &&
                r[3]==r[2]+1 && r[4]==r[3]+1){ // natural straight
            return true;
        }
        else // 10,11,12,13,1
            if (r[0]==2 && r[1]==3 && r[2]==4 &&
                r[3]==5 && r[4]==1){ // ace first
            return true;
        }
        else return r[0] == 10 && r[1] == 11 && r[2] == 12 &&
                    r[3] == 13 && r[4] == 1;
    }

    //method to check for a flush
    public boolean flushHand(ArrayList<Card> hand) {
        //looks if all suits are the same
        return hand.get(0).getSuit() == hand.get(1).getSuit() &&
                hand.get(1).getSuit() == hand.get(2).getSuit() &&
                hand.get(2).getSuit() == hand.get(3).getSuit() &&
                hand.get(3).getSuit() == hand.get(4).getSuit();
    }

    //method to check for four of a kind
    public boolean fourKindHand(ArrayList<Card> hand ) {
        int[] r = ranksHand(hand);
        return (r[0] == r[1] && r[1] == r[2] && r[2] == r[3]) ||
                (r[1] == r[2] && r[2] == r[3] && r[3] == r[4]);
    }

    //method to check for full house
    public boolean fullHouseHand(ArrayList<Card> hand) {
        int[] r = ranksHand(hand);
        // 123 45 , 12 345 since sorted
        return (r[0] == r[1] && r[1] == r[2] && r[3] == r[4]) ||
                (r[0] == r[1] && r[2] == r[3] && r[3] == r[4]);
    }

    //checks for three of a kind
    public boolean threeKindHand(ArrayList<Card> hand) {
        int[] r = ranksHand(hand);
        // 123 234 345 since sorted
        return (r[0] == r[1] && r[1] == r[2]) || (r[1] == r[2] && r[2] == r[3])
                || (r[2] == r[3] && r[3] == r[4]);
    }

    //checks for two pair
    public boolean twoPairHand(ArrayList<Card> hand) {
        // 12 34, 12 45, 23 45 since sorted
        int[] r = ranksHand(hand);
        return (r[0] == r[1] && r[2] == r[3]) || (r[0] == r[1] && r[3] == r[4])
                || (r[1] == r[2] && r[3] == r[4]);
    }

    //method to check for one pair
    public boolean pairHand(ArrayList<Card> hand) {
        int[] r = ranksHand(hand);
        /// 12,23,34,45 since sorted
        return r[0] == r[1] || r[1] == r[2] || r[2] == r[3] || r[3] == r[4];
    }

    //method to return the ranks of all the cards in a hand
    public int[] ranksHand(ArrayList<Card> hand){
        return new int[]{hand.get(0).getRank(),hand.get(1).getRank(),
                hand.get(2).getRank(),hand.get(3).getRank(),hand.get(4).getRank()};
    }


}