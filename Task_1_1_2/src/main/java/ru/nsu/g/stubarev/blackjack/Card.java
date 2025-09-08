package ru.nsu.g.stubarev.blackjack;

public class Card {
    private final int title;
    private final String symbol;
    private final int points;

    public int getTitle() {
        return title;
    }

    public int getPoints() {
        return points;
    }

    public String getSymbol() {
        return symbol;
    }


    public Card() {
        this.title = genRandomTitle();
        this.symbol = genSymbolByTitle(this.title);
        this.points = genPointsByTitle(this.title);
    }

    private int genRandomTitle() {
        return 2 + (int) (Math.random() * (13));
    }

    private int genPointsByTitle(int title) {
        if (title < 11) {
            return title;
        } else if (title != 14) {
            return 10;
        } else {
            return 11;
        }
    }

    private String genSymbolByTitle(int title) {
        final char[] suits = {'♥', '♦', '♣', '♠'};
        int randomSuit = (int) (Math.random() * 4);
        String cardValue = switch (title) {
            case 11 -> "JACK";
            case 12 -> "QUEEN";
            case 13 -> "KING";
            case 14 -> "ACE";
            default -> Integer.toString(title);
        };

        return cardValue + suits[randomSuit];
    }
}
