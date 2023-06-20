/*
* Jonah Singer
* Class contains the main method to play the game
 */

public class PokerTest{

    public static void main(String[] args){
        Game g;
        if (args.length<1){
            g = new Game();
        }
        else{
            g = new Game(args);
        }
        g.play();
    }

}