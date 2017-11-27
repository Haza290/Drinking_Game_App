package schneidernetwork.drinkinggameapp;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import cards.AbstractCard;
import onClickListeners.GameOverOnClickListener;

/**
 * Created by Harry on 22/07/2017.
 */

public class GameLogic {

    private Context context;
    public static ArrayList<String> playerNames;
    private Deck deck;
    private int DEFAULT_NUM_OF_EVENTS = 20;

    int currentCardNum = 0;

    public GameLogic() {
        context = MainActivity.context;
    }

    public void addPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public void startNewGame(String deckFileName) {
        deck = new Deck(deckFileName, DEFAULT_NUM_OF_EVENTS, playerNames.size());
        deck.getCurrentCard().display();
    }

    /**
     * Displays the next card
     */
    public void displayNextCard() {
        AbstractCard nextCard = deck.getNextCard();
        // Checks if we are at the end of the game
        if (nextCard == null) {
            gameOver();
            return;
        }

        nextCard.display();
    }

    /**
     * Displays the previous card
     */
    public void displayPreviousCard() {
        AbstractCard previousCard = deck.getPreviousCard();
        previousCard.display();
    }

    private void gameOver() {
        // TODO write gameOver code
        ((Activity) context).setContentView(R.layout.game_over_screen);
        ((Activity) context).findViewById(R.id.gameOverScreen).setOnClickListener(new GameOverOnClickListener(playerNames));
    }
}
