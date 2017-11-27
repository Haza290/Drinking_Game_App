package schneidernetwork.drinkinggameapp;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

import cards.AbstractCard;
import cards.StandardCard;
import cards.ThreeInFiveCard;
import utility.StringAndHashMap;
import utility.TextParser;
import utility.XMLHandler;

/**
 * Created by Harry on 03/08/2017.
 */

public class Event {

    private Node eventNode;
    private int playersNum;
    private String type;
    private boolean isValid = true;
    private ArrayList<AbstractCard> cards;
    private HashMap<String, String> playersNamesKey = new HashMap<String, String>();

    public Event(Node eventNode) {
        this.eventNode = eventNode;
        type = XMLHandler.getAttributeTextFromNode(eventNode, "type");
        String playersNumString = XMLHandler.getAttributeTextFromNode(eventNode, "players");
        if (playersNumString == null || type == null) {
            isValid = false;
        } else {
            playersNum = Integer.parseInt(playersNumString);
        }
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void createCards() {
        if(type.equals("standard") || type.equals("multi")) {
            cards = createStandardCards(eventNode);
            return;
        }
        if (type.equals("three in five")) {
            cards = createThreeInFiveCards(eventNode);
            return;
        }
    }

    public ArrayList<AbstractCard> getCards() {return cards;}

    public int getPlayersNum() {return playersNum;}

    public String getType() {return type;}

    public ArrayList<String> getRawText() {

        ArrayList<String> rawText = new ArrayList<String>();
        for (Node textNode : getAllTextNodes(eventNode)) {
            rawText.add(textNode.getTextContent());
        }
        return rawText;
    }

    private ArrayList<AbstractCard> createStandardCards(Node eventNode) {

        ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();

        for (Node textNode : getAllTextNodes(eventNode)) {
            String cardText = parseCardText(textNode.getTextContent());
            int offset = getCardOffset(textNode);

            cards.add(new StandardCard(cardText, offset, this));
        }
        return cards;
    }

    private ArrayList<AbstractCard> createThreeInFiveCards(Node eventNode) {

        ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();

        for (Node textNode : getAllTextNodes(eventNode)) {
            String cardText = parseCardText(textNode.getTextContent());
            cards.add(new ThreeInFiveCard(cardText, this));
        }
        return cards;

    }

    /**
     * Gets all the nodes with the name "text" from eventNode
     * @param eventNode Node we are looking for text nodes in
     * @return An Array of all the text nodes
     */
    private ArrayList<Node> getAllTextNodes(Node eventNode) {

        ArrayList<Node> textNodes = new ArrayList<Node>();
        NodeList childNodes = eventNode.getChildNodes();

        // Loops through all child nodes looking for text nodes
        for (int i = 0; childNodes.getLength() > i; i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName().equals("text")) {
                textNodes.add(childNode);
            }
        }
        return textNodes;
    }

    /**
     * Parses the raw string
     * @param rawText raw text to be parsed
     * @return The parsed text
     */
    private String parseCardText(String rawText) {
        StringAndHashMap stringAndHashMap = TextParser.evaluateString(rawText, playersNamesKey);
        playersNamesKey = stringAndHashMap.playersMap;
        return stringAndHashMap.text;
    }

    /**
     * Gest the offset attribute of a text
     * @param textNode
     * @return int the offset
     */
    private int getCardOffset(Node textNode) {
        String offset = XMLHandler.getAttributeTextFromNode(textNode, "offset");
        if (offset == null) {
            return 0;
        }
        return Integer.parseInt(TextParser.evaluateString(offset).text);
    }
}
