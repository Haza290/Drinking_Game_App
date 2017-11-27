package screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

import schneidernetwork.drinkinggameapp.Event;
import schneidernetwork.drinkinggameapp.MainActivity;
import schneidernetwork.drinkinggameapp.R;
import utility.TextParser;
import utility.XMLHandler;

/**
 * Created by Harry on 09/08/2017.
 */

public class DeckCreationScreen {

    Context context = MainActivity.context;
    Document saveFile;

    ArrayList<String> standardCardText = new ArrayList<String>();
    ArrayList<String> threeInFiveCardText = new ArrayList<String>();
    ArrayList<ArrayList<String>> multiPartCardText = new ArrayList<ArrayList<String>>();

    public void display(String fileName) {

        getAllEventsTextFromFile(fileName);

        ((Activity) context).setContentView(R.layout.deck_creation_screen);

        displayStandardCards();
        displayThreeInFiveCards();
        displayMultiPartCards();

    }

    private void displayStandardCards() {

        LinearLayout standardCardTextLayout = ((Activity) context).findViewById(R.id.standardCards);
        Button addNewCardStandardCardButton = (Button) ((Activity) context).findViewById(R.id.addNewStardardCard);
        addCardsToLayout(standardCardText, standardCardTextLayout);
        setOnClickListenerForAddNewCardToLayoutButton(standardCardTextLayout, addNewCardStandardCardButton);

    }

    private void displayThreeInFiveCards() {

        LinearLayout threeInFiveTextLayout = ((Activity) context).findViewById(R.id.threeInFiveCards);
        Button addNewCardThreeInFiveCardButton = (Button) ((Activity) context).findViewById(R.id.addNewThreeInFiveCard);
        addCardsToLayout(threeInFiveCardText, threeInFiveTextLayout);
        setOnClickListenerForAddNewCardToLayoutButton(threeInFiveTextLayout, addNewCardThreeInFiveCardButton);

    }

    private void displayMultiPartCards() {

        final LinearLayout multiPartTextLayout = ((Activity) context).findViewById(R.id.multiPartCards);

        for (ArrayList<String> textArray : multiPartCardText) {
            addCardsToLayout(textArray, addNewMultiPartCardInputToLayout(multiPartTextLayout));
        }

        Button addNewCardMultiPartCardButton = (Button) ((Activity) context).findViewById(R.id.addNewMiltPartCard);
        addNewCardMultiPartCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewMultiPartCardInputToLayout(multiPartTextLayout);
            }
        });

    }

    /**
     * TODO rename this thing
     * @param layout
     * @param addNewCardButton
     */
    private void setOnClickListenerForAddNewCardToLayoutButton(final LinearLayout layout, Button addNewCardButton) {
        addNewCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.addView(createNewEditText());
            }
        });
    }

    private LinearLayout addNewMultiPartCardInputToLayout (LinearLayout allMulitPartCardsLayout) {
        // Creates an textLayout to hold all of the EditTexts and an outer layout to hold the textLayout
        // and a button
        LinearLayout outerLayout = new LinearLayout(context);
        outerLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout textLayout = new LinearLayout(context);
        textLayout.setOrientation(LinearLayout.VERTICAL);
        outerLayout.setPadding(10,10,10,10);

        // Creates a button, sets its text and adds a onClickListener to create more EditTexts
        Button addCardButton = new Button(context);
        addCardButton.setText(R.string.new_text_button);
        setOnClickListenerForAddNewCardToLayoutButton(textLayout, addCardButton);

        // adds the textLayout and addCardButton to the outerLayout
        outerLayout.addView(textLayout);
        outerLayout.addView(addCardButton);

        allMulitPartCardsLayout.addView(outerLayout);

        return textLayout;
    }

    /**
     * Reads in all the events from a deck then gets all of there text
     * @param fileName
     */
    private void getAllEventsTextFromFile(String fileName) {

        ArrayList<Event> allEvents = XMLHandler.getAllEventsFromDeck(fileName, -1);
        for (Event event : allEvents) {
            if (event.getType().equals("standard")) {
                standardCardText.add(event.getRawText().get(0));
            } else if (event.getType().equals("three in five")) {
                threeInFiveCardText.addAll(event.getRawText());
            } else if (event.getType().equals("multi")) {
                multiPartCardText.add(event.getRawText());
            }
        }
    }

    /**
     * Creates a new editText and adds it to a given layout for each card
     * @param cards  The cards to add to a layout
     * @param layout The layout to add the cards to
     */
    private void addCardsToLayout(ArrayList<String> cards, LinearLayout layout) {
        for (String cardText : cards) {
            EditText editText = createNewEditText();
            editText.setText(cardText);
            layout.addView(editText);
        }
    }

    /**
     * Creates a new EditText and formats it
     * @return A new EditText
     */
    private EditText createNewEditText() {
        EditText editText = new EditText(context);
        editText.setMaxLines(1);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        return editText;
    }

    public void saveAs(String fileName) {

        ArrayList<String> standardCardText = new ArrayList<String>();
        ArrayList<ArrayList<String>> multiPartCardText = new ArrayList<ArrayList<String>>();
        ArrayList<String> threeInFiveCardText = new ArrayList<String>();

        LinearLayout standardLayout = ((Activity)context).findViewById(R.id.standardCards);
        for (int i = 0; standardLayout.getChildCount() > i; i++) {
            EditText editText = (EditText) standardLayout.getChildAt(i);
            standardCardText.add(editText.getText().toString());
        }

        LinearLayout threeInFiveLayout = ((Activity)context).findViewById(R.id.threeInFiveCards);
        for (int i = 0; threeInFiveLayout.getChildCount() > i; i++) {
            EditText editText = (EditText) threeInFiveLayout.getChildAt(i);
            threeInFiveCardText.add(editText.getText().toString());
        }

        LinearLayout mulitCardLayout = ((Activity)context).findViewById(R.id.multiPartCards);
        for (int i = 0; mulitCardLayout.getChildCount() > i; i++) {
            EditText editText = (EditText) threeInFiveLayout.getChildAt(i);
            threeInFiveCardText.add(editText.getText().toString());
        }
    }

    private Document addStandardCardsToDoc(ArrayList<String> standardCardTexts, Document saveFile) {

        for (String standardCardText : standardCardTexts) {
            // Creates an event element and its attributes
            Element eventElement = saveFile.createElement("event");
            eventElement.setAttribute("type", "standard");
            eventElement.setAttribute("players", Integer.toString(TextParser.calculateNumOfPlayersNeeded(standardCardText)));

            // Creates the text element and adds it to the event element
            Element textElement = saveFile.createElement("text");
            textElement.setTextContent(standardCardText);
            eventElement.appendChild(textElement);

            // Adds the event element to the root element in the saveFile
            saveFile.getFirstChild().appendChild(eventElement);
        }
        return saveFile;
    }

    private Document addThreeInFiveCardsToDoc(ArrayList<String> standardCardTexts, Document saveFile) {

        for (String standardCardText : standardCardTexts) {
            // Creates an event element and its attributes
            Element eventElement = saveFile.createElement("event");
            eventElement.setAttribute("type", "three in five");
            eventElement.setAttribute("players", Integer.toString(TextParser.calculateNumOfPlayersNeeded(standardCardText) + 1));

            // Creates the text element and adds it to the event element
            Element textElement = saveFile.createElement("text");
            textElement.setTextContent(standardCardText);
            eventElement.appendChild(textElement);

            // Adds the event element to the root element in the saveFile
            saveFile.getFirstChild().appendChild(eventElement);
        }
        return saveFile;
    }

    private Document addMultiCardsToDoc(ArrayList<ArrayList<String>> mulitCardsTexts, Document saveFile) {

        for (ArrayList<String> textsForEvent: mulitCardsTexts) {

            // Creates an event element and type attributes
            Element eventElement = saveFile.createElement("event");
            eventElement.setAttribute("type", "multi");
            int numOfPlayersNeeded = 0;

            // loops through all the text
            for (String cardText : textsForEvent) {

                // Creates a new element for each of the texts and sets its TextContent
                Element textElement = saveFile.createElement("text");
                textElement.setTextContent(cardText);
                eventElement.appendChild(textElement);

                // TODO rewrite this code to check how many players this needs coz its only checking
                // one at a time are the moment
                if(TextParser.calculateNumOfPlayersNeeded(cardText) > numOfPlayersNeeded) {
                    numOfPlayersNeeded = TextParser.calculateNumOfPlayersNeeded(cardText);
                }

            }

            eventElement.setAttribute("players", Integer.toString(numOfPlayersNeeded));
            saveFile.getFirstChild().appendChild(eventElement);
        }
        return saveFile;
    }

    /**
     * Creates an AlertDialog to input the name the deck should be saved as
     */
    private void createSaveAsAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        final EditText fileNameEditText = new EditText(context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(fileNameEditText).setTitle("Save as");

        // set dialog message
        alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}
