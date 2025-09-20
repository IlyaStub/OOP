package ru.nsu.g.stubarev.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void testHandInitialization() {
        Hand hand = new Hand();

        assertNotNull(hand);
        assertEquals(0, hand.getSumPoints());
        assertTrue(hand.toString().contains("[]"));
    }

    @Test
    void testAddCardToHand() {
        Hand hand = new Hand();
        Deck deck = new Deck();

        hand.addCardToHand(deck);
        assertTrue(hand.getSumPoints() > 0);

        hand.addCardToHand(deck);
        assertTrue(hand.getSumPoints() >= 2);
    }

    @Test
    void testAddHiddenCard() {
        Hand hand = new Hand();
        Deck deck = new Deck();

        hand.addCardToHand(deck, true);
        Card lastCard = hand.getLastCard();

        assertTrue(lastCard.isHidden());
        assertTrue(hand.toString().contains("hidden card"));
    }

    @Test
    void testRevealHiddenCard() {
        Hand hand = new Hand();
        Deck deck = new Deck();

        hand.addCardToHand(deck, true);
        hand.revealHiddenCard();

        Card lastCard = hand.getLastCard();
        assertFalse(lastCard.isHidden());
        assertFalse(hand.toString().contains("hidden card"));
    }

    @Test
    void testAcePointsAdjustment() {
        Hand hand = new Hand();

        hand.addCardToHand(new TestDeck(Card.Rank.ACE, Card.Suit.SPADES));
        hand.addCardToHand(new TestDeck(Card.Rank.ACE, Card.Suit.HEARTS));
        hand.addCardToHand(new TestDeck(Card.Rank.NINE, Card.Suit.DIAMONDS));

        assertEquals(21, hand.getSumPoints());
    }

    @Test
    void testBlackjackScenario() {
        Hand hand = new Hand();

        hand.addCardToHand(new TestDeck(Card.Rank.ACE, Card.Suit.SPADES));
        hand.addCardToHand(new TestDeck(Card.Rank.TEN, Card.Suit.HEARTS));

        assertEquals(21, hand.getSumPoints());
    }

    @Test
    void testToStringWithHiddenCards() {
        Hand hand = new Hand();
        hand.addCardToHand(new TestDeck(Card.Rank.TWO, Card.Suit.SPADES));
        hand.addCardToHand(new TestDeck(Card.Rank.THREE, Card.Suit.HEARTS), true);

        String result = hand.toString();
        assertTrue(result.contains("Two of Spades (2)"));
        assertTrue(result.contains("hidden card"));
        assertFalse(result.contains("=>"));
    }

    private static class TestDeck extends Deck {
        private final Card.Rank rank;
        private final Card.Suit suit;

        TestDeck(Card.Rank rank, Card.Suit suit) {
            this.rank = rank;
            this.suit = suit;
        }

        @Override
        public Card getCard(boolean hidden) {
            Card card = new Card(rank, suit);
            card.setHidden(hidden);
            return card;
        }
    }
}