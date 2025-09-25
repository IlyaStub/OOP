package ru.nsu.g.stubarev.blackjack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a deck of playing cards for Blackjack.
 * Handles card drawing and deck management.
 */
public class Deck {
    private ArrayList<Card> deck;
    private final Random rand;

    /**
     * Constructs and initializes a new deck of cards.
     */
    public Deck() {
        this.rand = new Random();
        initializeDeck();
    }

    /**
     * Initializes the deck with 52 standard playing cards.
     */
    private void initializeDeck() {
        deck = new ArrayList<>(52);
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Draws a random card from the deck.
     *
     * @param hidden whether the drawn card should be hidden
     * @return the drawn card
     */
    public Card getCard(boolean hidden) {
        int len = deck.size();
        if (len == 0) {
            initializeDeck();
            len = 52;
        }
        int randCard = rand.nextInt(len);
        Card card = deck.get(randCard);
        deck.remove(randCard);
        if (hidden) {
            card.setHidden(true);
        }
        return card;

    }
}
