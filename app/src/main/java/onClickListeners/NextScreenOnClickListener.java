package onClickListeners;

import android.view.View;

import schneidernetwork.drinkinggameapp.MainActivity;

/**
 * Created by Harry on 29/07/2017.
 */

public class NextScreenOnClickListener implements View.OnClickListener{
    @Override
    public void onClick(View view) {
        MainActivity.gameLogic.displayNextCard();
    }
}
