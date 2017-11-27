package schneidernetwork.drinkinggameapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import screens.DeckCreationScreen;
import screens.DeckPickingScreen;
import screens.NameInputScreen;

public class MainActivity extends AppCompatActivity {

    public static GameLogic gameLogic;
    public static NameInputScreen nameInputScreen;
    public static DeckPickingScreen deckPickingScreen;
    public static DeckCreationScreen deckCreationScreen;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        gameLogic = new GameLogic();
        nameInputScreen = new NameInputScreen();
        deckPickingScreen = new DeckPickingScreen();
        deckCreationScreen = new DeckCreationScreen();

        nameInputScreen.display();
    }
}
