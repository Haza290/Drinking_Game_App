package cards;

import android.app.Activity;
import android.widget.TextView;

import org.w3c.dom.Node;

import java.util.HashMap;

import onClickListeners.NextScreenOnClickListener;
import schneidernetwork.drinkinggameapp.Event;
import schneidernetwork.drinkinggameapp.R;

public class StandardCard extends AbstractCard{

    String text;

    public StandardCard(String text, int offset, Event event) {
        super(offset, event);
        this.text = text;
    }

    public boolean isValid() {
        return true;
    }

    public void display() {
        ((Activity) context).setContentView(R.layout.standard_card_screen);
        TextView cardText = (TextView) ((Activity) context).findViewById(R.id.cardText);
        cardText.setText(text);
        ((Activity) context).findViewById(R.id.standardScreen).setOnClickListener(new NextScreenOnClickListener());
    }

}
