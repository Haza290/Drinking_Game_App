package utility;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import schneidernetwork.drinkinggameapp.Event;
import schneidernetwork.drinkinggameapp.MainActivity;
import schneidernetwork.drinkinggameapp.R;

/**
 * Created by Harry on 22/07/2017.
 */

public class XMLHandler {

    /**
     * Gets all the deckInfos from file deck_list in raw
     * @return
     */
    public static ArrayList<DeckInfo> getDeckInfos() {

        System.out.println("Trying top open deck_list");
        InputStream is = MainActivity.context.getResources().openRawResource(R.raw.deck_list);
        Document doc = getDocFromIs(is);
        // If doc is null then it couldn't be created so return null
        if (doc == null) {
            return null;
        }

        ArrayList<DeckInfo> allDeckInfos = new ArrayList<DeckInfo>();
        NodeList deckInfoNodes = doc.getElementsByTagName("deck");
        // Cycle through each deck
        for (int i = 0; deckInfoNodes.getLength() > i; i++) {
            NodeList deckInfo = deckInfoNodes.item(i).getChildNodes();

            // Initializes all strings needed to create a DeckInfo object
            String name = "place holder";
            String description = "place holder";
            String picture = "place holder";
            String fileName = "place holder";

            // Cycle through all items in deck assigning values to DeckInfo strings
            for (int j = 0; deckInfo.getLength() > j; j++) {
                //System.out.println("Node name: " + deckInfo.item(j).getNodeName());
                if (deckInfo.item(j).getNodeName().equals("name")) {
                    name = deckInfo.item(j).getTextContent();
                } else if (deckInfo.item(j).getNodeName().equals("description")) {
                    description = deckInfo.item(j).getTextContent();
                } else if (deckInfo.item(j).getNodeName().equals("picture")) {
                    picture = deckInfo.item(j).getTextContent();
                } else if (deckInfo.item(j).getNodeName().equals("fileName")) {
                    fileName = deckInfo.item(j).getTextContent();
                }
            }

            // Creates and adds a DeckInfo to allDeckInfos array
            allDeckInfos.add(new DeckInfo(name, description, picture, fileName));
        }

        return allDeckInfos;
    }

    /**
     * Gets an attributes text from a given node
     * @param eventNode The node we are getting the attribute text from
     * @param attribute The attribute we are looking for
     * @return The string of the attribute or null if the attribute text cannot be found
     */
    public static String getAttributeTextFromNode(Node eventNode, String attribute) {
        // Checks if event has any attributes
        if (!eventNode.hasAttributes()) {
            return null;
        }
        // Checks if event has the attribute we are looking for
        Node playersAttribute = eventNode.getAttributes().getNamedItem(attribute);
        if (playersAttribute == null) {
            return null;
        }
        return playersAttribute.getTextContent();
    }

    /**
     * Gets all events from a deck
     * @param deckFileName file name of deck
     * @return An arrayList of event from the deck
     */
    public static ArrayList<Event> getAllEventsFromDeck(String deckFileName, int players) {

        System.out.println("getting all events from deck");

        // Gets document of deck from file name
        int deckId = MainActivity.context.getResources().getIdentifier(deckFileName, "raw", MainActivity.context.getPackageName());
        InputStream is = MainActivity.context.getResources().openRawResource(deckId);
        Document doc = getDocFromIs(is);

        // Gets all events from doc and creates an event object for each event and all its to an
        // array to return
        ArrayList<Event> allEventsFromDeck = new ArrayList<Event>();
        NodeList allEventNodes = doc.getElementsByTagName("event");
        for (int i = 0; allEventNodes.getLength() > i; i++) {
            allEventsFromDeck.add(new Event(allEventNodes.item(i)));
        }

        System.out.println("removing invalid cards from deck");

        // Removes invalid events and events than need more players than there are.
        if(players != -1) {
            for (int i = 0; allEventsFromDeck.size() > i; i++) {
                if (!allEventsFromDeck.get(i).getIsValid() || players < allEventsFromDeck.get(i).getPlayersNum()) {
                    allEventsFromDeck.remove(i);
                    i--;
                }
            }
        }
        return allEventsFromDeck;
    }

    /**
     * Turns an InputStream into a Document
     *
     * @param is inputStream
     * @return null if Document can't be created
     */
    private static Document getDocFromIs(InputStream is) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }
}
