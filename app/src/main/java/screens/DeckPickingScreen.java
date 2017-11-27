package screens;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import onClickListeners.DeckSelectionOnClickListener;
import onClickListeners.GoToDeckCreationScreen;
import schneidernetwork.drinkinggameapp.MainActivity;
import schneidernetwork.drinkinggameapp.R;
import utility.DeckInfo;
import utility.XMLHandler;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Harry on 03/08/2017.
 */

public class DeckPickingScreen {

    Context context = MainActivity.context;;

    public void display() {

        ((Activity) context).setContentView(R.layout.deck_picking_screen);

        LinearLayout allDeckLayout = (LinearLayout) ((Activity) context).findViewById(R.id.deckLayout);

        // Get all deck infos and adds it to the deckGridLayout
        ArrayList<DeckInfo> deckInfos = XMLHandler.getDeckInfos();

        if (deckInfos == null) {
            System.err.println("deckInfos is null");
            return;
        }

        for (DeckInfo deckinfo : deckInfos) {
            // Creates a vertical linear layout
            LinearLayout deckInfoLayout = new LinearLayout(context);
            deckInfoLayout.setOrientation(VERTICAL);
            deckInfoLayout.setBackgroundColor(Color.CYAN);

            // Creates textView for the decks name
            TextView deckName = new TextView(context);
            deckName.setText(deckinfo.deckName);
            deckName.setTextSize(20f);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                deckName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }

            // Creates textView for the decks info
            TextView deckDescription = new TextView(context);
            deckDescription.setText(deckinfo.deckDescription);
            deckDescription.setTextSize(20f);

            // Adds the deck name and info textViews to the deck info layout
            deckInfoLayout.addView(deckName);
            deckInfoLayout.addView(deckDescription);

            // Adds eventListener to deckInfoLayout
            deckInfoLayout.setOnClickListener(new DeckSelectionOnClickListener(deckinfo.fileName));

            // Adds deck info layout to all the decks grid layout info
            allDeckLayout.addView(deckInfoLayout);
        }


        //TODO remove this test thing
        Button button = (Button) ((Activity) context).findViewById(R.id.editTestDeck);
        button.setOnClickListener(new GoToDeckCreationScreen("testing_deck"));
    }
}
