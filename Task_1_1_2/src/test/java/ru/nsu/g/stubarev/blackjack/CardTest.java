package ru.nsu.g.stubarev.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void testCardCreationAndBasicProperties() {
        Card card = new Card(Card.Rank.ACE, Card.Suit.SPADES);

        assertNotNull(card);
        assertEquals(11, card.getPoints());
        assertFalse(card.isHidden());
    }

    @Test
    void testCardHiddenState() {
        Card card = new Card(Card.Rank.KING, Card.Suit.HEARTS);

        assertFalse(card.isHidden());
        card.setHidden(true);
        assertTrue(card.isHidden());
        card.setHidden(false);
        assertFalse(card.isHidden());
    }

    @Test
    void testCardToStringVisible() {
        Card card = new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS);

        String expected = "Queen of Diamonds (10)";
        assertEquals(expected, card.toString());
    }

    @Test
    void testCardToStringHidden() {
        Card card = new Card(Card.Rank.JACK, Card.Suit.CLUBS);
        card.setHidden(true);

        assertEquals("Hidden Card", card.toString());
    }

    @Test
    void testAllRankValues() {
        assertEquals(2, Card.Rank.TWO.getPoints());
        assertEquals(3, Card.Rank.THREE.getPoints());
        assertEquals(4, Card.Rank.FOUR.getPoints());
        assertEquals(5, Card.Rank.FIVE.getPoints());
        assertEquals(6, Card.Rank.SIX.getPoints());
        assertEquals(7, Card.Rank.SEVEN.getPoints());
        assertEquals(8, Card.Rank.EIGHT.getPoints());
        assertEquals(9, Card.Rank.NINE.getPoints());
        assertEquals(10, Card.Rank.TEN.getPoints());
        assertEquals(10, Card.Rank.JACK.getPoints());
        assertEquals(10, Card.Rank.QUEEN.getPoints());
        assertEquals(10, Card.Rank.KING.getPoints());
        assertEquals(11, Card.Rank.ACE.getPoints());
    }

    @Test
    void testAllSuitNames() {
        assertEquals("Spades", Card.Suit.SPADES.getName());
        assertEquals("Hearts", Card.Suit.HEARTS.getName());
        assertEquals("Diamonds", Card.Suit.DIAMONDS.getName());
        assertEquals("Clubs", Card.Suit.CLUBS.getName());
    }

}