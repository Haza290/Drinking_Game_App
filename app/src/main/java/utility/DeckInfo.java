package utility;

import android.graphics.Bitmap;

/**
 * Created by Harry on 23/07/2017.
 */

public class DeckInfo {

    public String deckName;
    public String deckDescription;
    public String deckImage;
    public String fileName;

    public DeckInfo(String deckName, String deckDescription, String deckImage, String fileName) {
        this.deckName = deckName;
        this.deckDescription = deckDescription;
        this.deckImage = deckImage;
        this.fileName = fileName;
    }
}
