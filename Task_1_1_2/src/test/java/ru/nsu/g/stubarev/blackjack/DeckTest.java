package ru.nsu.g.stubarev.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

class DeckTest {

    @Test
    void testDeckInitialization() {
        Deck deck = new Deck();

        Set<String> uniqueCards = new HashSet<>();

        for (int i = 0; i < 52; i++) {
            Card card = deck.getCard(false);
            String cardKey = card.toString();
            uniqueCards.add(cardKey);
        }
        assertEquals(52, uniqueCards.size());
    }

    @Test
    void testGetCard() {
        Deck deck = new Deck();
        Card cardH = deck.getCard(true);
        Card cardNh = deck.getCard(false);

        assertNotNull(cardH);
        assertTrue(cardH.isHidden());

        assertNotNull(cardNh);
        assertFalse(cardNh.isHidden());
    }

    //re
    @Test
    void testDeckExhaustion() {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            Card card = deck.getCard(false);
            assertNotNull(card);
        }

        Card newCard = deck.getCard(false);
        assertNotNull(newCard);
    }
}