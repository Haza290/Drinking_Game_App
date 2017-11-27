package onClickListeners;

import android.content.Context;
import android.view.View;

import schneidernetwork.drinkinggameapp.GameLogic;
import schneidernetwork.drinkinggameapp.MainActivity;

/**
 * Created by Harry on 28/07/2017.
 */

public class DeckSelectionOnClickListener implements View.OnClickListener {

    private String fileName;

    public DeckSelectionOnClickListener(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void onClick(View view) {
        MainActivity.gameLogic.startNewGame(fileName);
    }
}
