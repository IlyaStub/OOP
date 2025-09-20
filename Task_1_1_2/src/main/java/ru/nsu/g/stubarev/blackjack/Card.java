package ru.nsu.g.stubarev.blackjack;

/**
 * Represents a playing card with rank, suit, and hidden state.
 */
public class Card {

    /**
     * Enum representing card ranks with their values.
     */
    public enum Rank {
        /**
         * Two with value 2
         */
        TWO("Two", 2),
        /**
         * Three with value 3
         */
        THREE("Three", 3),
        /**
         * Four with value 4
         */
        FOUR("Four", 4),
        /**
         * Five with value 5
         */
        FIVE("Five", 5),
        /**
         * Six with value 6
         */
        SIX("Six", 6),
        /**
         * Seven with value 7
         */
        SEVEN("Seven", 7),
        /**
         * Eight with value 8
         */
        EIGHT("Eight", 8),
        /**
         * Nine with value 9
         */
        NINE("Nine", 9),
        /**
         * Ten with value 10
         */
        TEN("Ten", 10),
        /**
         * Jack with value 10
         */
        JACK("Jack", 10),
        /**
         * Queen with value 10
         */
        QUEEN("Queen", 10),
        /**
         * King with value 10
         */
        KING("King", 10),
        /**
         * Ace with value 11 (can be adjusted to 1)
         */
        ACE("Ace", 11);

        private final String title;
        private final int points;

        /**
         * Constructs a rank with title and point value.
         *
         * @param title  the display title
         * @param points the point value
         */
        Rank(String title, int points) {
            this.title = title;
            this.points = points;
        }

        /**
         * Gets the title of the rank.
         *
         * @return the rank title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Gets the point value of the rank.
         *
         * @return the point value
         */
        public int getPoints() {
            return points;
        }
    }

    /**
     * Enum representing card suits.
     */
    public enum Suit {
        /**
         * Spades suit
         */
        SPADES("Spades"),
        /**
         * Hearts suit
         */
        HEARTS("Hearts"),
        /**
         * Diamonds suit
         */
        DIAMONDS("Diamonds"),
        /**
         * Clubs suit
         */
        CLUBS("Clubs");

        private final String name;

        /**
         * Constructs a suit with name.
         *
         * @param name the suit name
         */
        Suit(String name) {
            this.name = name;
        }

        /**
         * Gets the name of the suit.
         *
         * @return the suit name
         */
        public String getName() {
            return name;
        }
    }

    private final Rank rank;
    private final Suit suit;
    private boolean hidden;

    /**
     * Constructs a new card with specified rank and suit.
     *
     * @param rank the card rank
     * @param suit the card suit
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.hidden = false;
    }

    /**
     * Checks if the card is hidden.
     *
     * @return true if card is hidden, false otherwise
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Sets the hidden state of the card.
     *
     * @param hidden whether the card should be hidden
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Gets the point value of the card.
     *
     * @return the point value
     */
    public int getPoints() {
        return rank.getPoints();
    }

    /**
     * Returns string representation of the card.
     *
     * @return string showing card details or "Hidden Card"
     */
    @Override
    public String toString() {
        if (hidden) {
            return "Hidden Card";
        }
        return String.format("%s of %s (%d)",
                rank.getTitle(),
                suit.getName(),
                rank.getPoints());
    }
}