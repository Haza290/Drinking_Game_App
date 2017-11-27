package onClickListeners;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

import schneidernetwork.drinkinggameapp.MainActivity;
import schneidernetwork.drinkinggameapp.R;

/**
 * Created by Harry on 31/07/2017.
 */

public class GameOverOnClickListener implements View.OnClickListener {

    private ArrayList<String> playerNames;

    public GameOverOnClickListener(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    @Override
    public void onClick(View view) {
        MainActivity.nameInputScreen.display(playerNames);
    }
}
