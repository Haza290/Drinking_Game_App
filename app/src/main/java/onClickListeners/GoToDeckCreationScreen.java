package onClickListeners;


import android.view.View;

import schneidernetwork.drinkinggameapp.MainActivity;

/**
 * Created by Harry on 11/08/2017.
 */

public class GoToDeckCreationScreen implements View.OnClickListener {

    String fileName;

    public GoToDeckCreationScreen(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void onClick(View view) {
        MainActivity.deckCreationScreen.display(fileName);
    }
}

