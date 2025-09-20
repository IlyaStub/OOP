package ru.nsu.g.stubarev.blackjack;

import java.util.ArrayList;

/**
 * Represents a hand of cards in the Blackjack game.
 * Manages card operations and point calculations.
 */
public class Hand {
    private final ArrayList<Card> hand;

    /**
     * Gets the copy of hand
     *
     * @return copy of hand
     */
    public ArrayList<Card> getHand() {
        return new ArrayList<>(hand);
    }

    /**
     * Constructs an empty hand.
     */
    public Hand() {
        hand = new ArrayList<>(2);
    }

    /**
     * Gets the last card added to the hand.
     *
     * @return the last card in the hand
     */
    public Card getLastCard() {
        return hand.get(hand.size() - 1);
    }

    /**
     * Adds a card to the hand from the deck.
     *
     * @param deck the deck to draw from
     * @param hidden whether the card should be hidden
     */
    public void addCardToHand(Deck deck, boolean hidden) {
        hand.add(deck.getCard(hidden));
    }

    /**
     * Adds a visible card to the hand from the deck. (ordinary)
     *
     * @param deck the deck to draw from
     */
    public void addCardToHand(Deck deck) {
        hand.add(deck.getCard(false));
    }

    /**
     * Reveals the last hidden card in the hand.
     */
    public void revealHiddenCard() {
        hand.get(hand.size() - 1).setHidden(false);
    }

    /**
     * Calculates the total points of the hand with Ace adjustment.
     *
     * @return the sum of points in the hand
     */
    public int getSumPoints() {
        int summ = 0;
        int tmp = 0;
        int cntAce = 0;
        for (Card card : hand) {
            tmp = card.getPoints();
            summ += tmp;
            if (tmp == 11) {
                cntAce++;
            }
        }
        while (summ > 21 && cntAce > 0) {
            summ -= 10;
            cntAce--;
        }
        return summ;
    }

    /**
     * Returns string representation of the hand.
     *
     * @return string showing cards and total points if all visible
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean hasHidden = false;
        int c = 0;
        for (Card card : hand) {
            if (c != 0) {
                sb.append(", ");
            }
            if (card.isHidden()) {
                hasHidden = true;
            }
            sb.append(card.isHidden() ? "<hidden card>" : card.toString());
            c++;
        }
        sb.append(hasHidden ? "]" : String.format("] => %d", getSumPoints()));
        return sb.toString();
    }
}