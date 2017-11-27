package screens;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import schneidernetwork.drinkinggameapp.MainActivity;
import schneidernetwork.drinkinggameapp.R;

/**
 * Created by Harry on 03/08/2017.
 */

public class NameInputScreen {

    Context context = MainActivity.context;

    /**
     * Displays the nameInputScreen with no player names already input
     */
    public void display() {
        display(new ArrayList<String>());
    }

    /**
     * Displays the nameInputScreen with the last players names already input
     * @param playerNames
     */
    public void display(ArrayList<String> playerNames) {

        ((Activity) context).setContentView(R.layout.name_input_screen);

        createAllPlayerNameEditText(playerNames);

        // Creates the add player button and adds an on click listener
        Button addPlayerButton = (Button) ((Activity) context).findViewById(R.id.addPlayerButton);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createPlayerNameEditText();
            }
        });

        // Creates the start button and adds an on click listener to display the deckPickingScreen
        Button startButton = (Button) ((Activity) context).findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                hideKeyboard();
                MainActivity.gameLogic.addPlayerNames(getPlayerNames());
                MainActivity.deckPickingScreen.display();
            }
        });
    }

    /**
     * Creates a new editText to input a players name
     * @param playerNames
     */
    public void createAllPlayerNameEditText(ArrayList<String> playerNames) {
        for (String playerName : playerNames) {
            createPlayerNameEditText().setText(playerName);
        }

        if (playerNames.size() < 3) {
            int numOfEditTextsNeeded = 3 - playerNames.size();
            while (numOfEditTextsNeeded > 0) {
                createPlayerNameEditText();
                numOfEditTextsNeeded--;
            }
        }
    }

    /**
     * Creates a new editText for user to input player names
     */
    private EditText createPlayerNameEditText() {

        EditText newEditText = new EditText(context);
        LinearLayout inputNamesLayout = (LinearLayout) ((Activity) context).findViewById(R.id.inputNamesLayout);
        newEditText.setHint("Player " + (inputNamesLayout.getChildCount() + 1));
        newEditText.setMaxLines(1);
        newEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        inputNamesLayout.addView(newEditText);

        // Scrolls the scroll bar to bottom
        ScrollView scrollBar = (ScrollView) ((Activity) context).findViewById(R.id.scrollBar);
        scrollBar.post(new Runnable() {
            @Override
            public void run() {

                ScrollView scrollBar = (ScrollView) ((Activity) context).findViewById(R.id.scrollBar);
                scrollBar.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        return newEditText;
    }

    /**
     * Gets all the player names from user input
     * @return
     */
    private ArrayList<String> getPlayerNames() {

        ArrayList<String> playerNames = new ArrayList<String>();
        LinearLayout inputNamesLayout = (LinearLayout) ((Activity) context).findViewById(R.id.inputNamesLayout);
        // Goes through all editTexts in the name input layout
        for (int i = 0; inputNamesLayout.getChildCount() > i; i++) {
            EditText editText = (EditText) inputNamesLayout.getChildAt(i);
            // If editText is not empty add it to player name array
            if (editText.getText().toString().trim().length() > 0) {
                System.out.println("i: " + i + " playerName: " + editText.getText().toString().trim());
                playerNames.add(editText.getText().toString().trim());
            }
        }
        return playerNames;
    }

    /**
     * Hides the keyboard
     */
    private void hideKeyboard() {
        View currentView = ((Activity) context).getCurrentFocus();
        if (currentView != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromInputMethod(currentView.getWindowToken(), 0);
        }
    }
}
