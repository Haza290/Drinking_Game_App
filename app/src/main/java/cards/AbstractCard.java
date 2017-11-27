package cards;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;

import schneidernetwork.drinkinggameapp.Event;
import schneidernetwork.drinkinggameapp.MainActivity;
import schneidernetwork.drinkinggameapp.R;
import utility.StringAndHashMap;
import utility.TextParser;

public abstract class AbstractCard {

    View layoutView;
    Context context;
    Event event;
    int offset = 0;

    public AbstractCard(int offset, Event event) {
        this.offset = offset;
        this.event = event;
        context = MainActivity.context;
    }

    public abstract boolean isValid();
    public abstract void display();

    public Event getEvent() {
        return event;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) { this.offset = offset; }

}