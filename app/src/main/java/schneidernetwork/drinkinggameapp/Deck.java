package schneidernetwork.drinkinggameapp;

import java.util.ArrayList;
import java.util.Random;

import cards.AbstractCard;
import utility.XMLHandler;

/**
 * Created by Harry on 01/08/2017.
 */

public class Deck {

    private ArrayList<AbstractCard> deck;
    private int currentCardPos;

    public Deck(String deckFileName, int deckSize, int players) {
        // Gets all events from a deck 
        ArrayList<Event> allEventsFromDeck = XMLHandler.getAllEventsFromDeck(deckFileName, players);
        // Creates a linked list of cards from all events
        deck = createDeck(allEventsFromDeck, deckSize);
        deck = sortOffsets(deck);
        currentCardPos = 0;
    }

    /**
     * Returns the next card and updates currentCardPos
     * @return The next card, returns null if there are no more cards
     */
    public AbstractCard getNextCard() {
        currentCardPos++;
        if (currentCardPos >= deck.size()) {
            return null;
        }
        return getCurrentCard();
    }

    /**
     * Returns the previous card and updates currentCardPos
     * @return the previous card, the current card if it is the first card in the deck
     */
    public AbstractCard getPreviousCard() {
        if (currentCardPos > 0) {
            currentCardPos--;
        }
        return getCurrentCard();
    }

    public AbstractCard getCurrentCard() {
        return deck.get(currentCardPos);
    }

    /**
     * Picks random cards from allEvents untill the deck is size deckSize
     * @param allEvents an array of all events to pick from
     * @param deckSize the size of the deck required
     * @return An arrayList of cards
     */
    private ArrayList<AbstractCard> createDeck(ArrayList<Event> allEvents, int deckSize) {

        ArrayList<AbstractCard> deck = new ArrayList<AbstractCard>();

        // If deck has less events than numOfEvents set numOfEvents to be the number of events in
        // the deck
        if (deckSize > allEvents.size()) {
            deckSize = allEvents.size();
        }

        for (int i = 0; deckSize > i; i++) {
            // Pick a random event and removes it from allEvents
            Random rand = new Random();
            int randomEventNum = rand.nextInt(allEvents.size());
            Event pickedEvent = allEvents.get(randomEventNum);
            allEvents.remove(randomEventNum);

            // Creates cards from pickedEvent and adds them to the decks array if the cards are valid
            pickedEvent.createCards();
            if (pickedEvent.getIsValid()) {
                deck.addAll(pickedEvent.getCards());
            }
        }
        return deck;
    }

    private ArrayList<AbstractCard> sortOffsets(ArrayList<AbstractCard> deck) {
        // Loops through all cards in the deck
        for (int i = 0; deck.size() > i; i++) {
            // Checks if card needs to be moved
            if (deck.get(i).getOffset() > 0) {
                // Gets new possition in the deck the card should be get a copy of the card and
                // removes card from the deck
                int newPos = deck.get(i).getOffset() + i;
                AbstractCard cardToMove = deck.get(i);
                cardToMove.setOffset(0);
                deck.remove(i);

                // If the new position of the card is past the end of the deck then just add it to
                // the end of the deck otherwise add it to the new position
                if (newPos >= deck.size()) {
                    deck.add(cardToMove);
                } else {
                    // Checks if card is being placed in the middle of a multi card event, if it is try puting it
                    // a card later
                    while(newPos < deck.size() - 1 && deck.get(newPos - 1).getEvent() == deck.get(newPos).getEvent()) {
                        newPos++;
                    }
                    deck.add(newPos, cardToMove);
                }
            }
        }
        return deck;
    }
}
