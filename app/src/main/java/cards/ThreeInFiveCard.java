package cards;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import schneidernetwork.drinkinggameapp.Event;
import schneidernetwork.drinkinggameapp.MainActivity;
import schneidernetwork.drinkinggameapp.R;
import utility.TextParser;

/**
 * Created by Harry on 03/08/2017.
 */

public class ThreeInFiveCard extends AbstractCard {

    String text;

    public ThreeInFiveCard(String text, Event event) {
        super(0, event);
        this.text = text;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void display() {
        displaySetUpScreen();
    }

    /**
     * Displays the set up screen which lets the players know who is about to play
     */
    private void displaySetUpScreen() {

        ((Activity) context).setContentView(R.layout.standard_card_screen);
        TextView cardText = (TextView) ((Activity) context).findViewById(R.id.cardText);
        // Picks a player to play the game
        String text = TextParser.evaluateString("~A get ready to play 3 in 5!").text;
        cardText.setText(text);

        ((Activity) context).findViewById(R.id.standardScreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTextScreen();
            }
        });
    }

    private void displayTextScreen() {
        ((Activity) context).setContentView(R.layout.standard_card_screen);
        TextView cardText = (TextView) ((Activity) context).findViewById(R.id.cardText);
        cardText.setText(text);

        ((Activity) context).findViewById(R.id.standardScreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCountDownScreen();
            }
        });
    }

    /**
     * Displays the countdown
     */
    private void displayCountDownScreen() {

        System.out.println("starting count down");

        ((Activity) context).setContentView(R.layout.standard_card_screen);
        TextView countDownText = (TextView) ((Activity) context).findViewById(R.id.cardText);
        countDownText.setText("5");
        // Sets up a 5 second count down that fires a tick every second
        new CountDownTimer(5000, 1000) {

            TextView countDownText = (TextView) ((Activity) context).findViewById(R.id.cardText);
            int countDown = 5;

            @Override
            public void onTick(long l) {
                countDownText.setText(Integer.toString(countDown));
                countDown--;
            }

            @Override
            public void onFinish() {
                countDownText.setText("Time Up!");
                ((Activity) context).findViewById(R.id.standardScreen).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.gameLogic.displayNextCard();
                    }
                });
            }
        }.start();
    }
}
