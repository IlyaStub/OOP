package ru.nsu.g.stubarev.blackjack;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

/**
 * Utility class for handling game output and user interface messages.
 * This class contains static methods for printing game information.
 */
public class Parser {

    /**
     * Prints welcome message at game start.
     */
    public static void printWelcome() {
        System.out.println("Welcome to Blackjack!");
    }

    /**
     * Prints round information.
     * @param round current round number
     */
    public static void printRound(int round) {
        System.out.println("Round " + round);
        System.out.println("Dealer dealt the cards");
    }

    /**
     * Prints both player's and dealer's hands.
     * @param playerHand the player's hand
     * @param dealerHand the dealer's hand
     */
    public static void printHands(Hand playerHand, Hand dealerHand) {
        System.out.println("\tYour cards: " + playerHand.toString());
        System.out.println("\tDealer's cards: " + dealerHand.toString());
    }

    /**
     * Prints turn information for the specified player.
     * @param player the player whose turn it is
     */
    public static void printTurn(GameController.Players player) {
        if (player == GameController.Players.PLAYER) {
            System.out.println("Your turn");
            System.out.println("-------");
            System.out.println("Enter '1' to take a card, and '0' to stop");
        } else {
            System.out.println("Dealer's turn");
            System.out.println("-------");
        }
    }

    /**
     * Prints information about a revealed card.
     * @param card the card that was revealed
     * @param player the player who revealed the card
     */
    public static void printOpenCard(Card card, GameController.Players player) {
        if (player == GameController.Players.PLAYER) {
            System.out.println("You revealed card " + card.toString());
        } else {
            System.out.println("Dealer reveals card " + card.toString());
        }
    }

    /**
     * Prints information about a revealed hidden card.
     * @param card the hidden card that was revealed
     */
    public static void printOpenHiddenCard(Card card) {
        System.out.println("Dealer reveals hidden card " + card.toString());
    }

    /**
     * Prints round result and updated scores.
     * @param loser the player who lost (null for draw)
     * @param playerScore current player score
     * @param dealerScore current dealer score
     */
    public static void printLoser(GameController.Players loser, int playerScore, int dealerScore) {
        if (loser == GameController.Players.PLAYER) {
            System.out.println("You lost the round!");
            System.out.println("Score " + playerScore + ":" + dealerScore + " in dealer's favor.");
        } else if (loser == GameController.Players.DEALER) {
            System.out.println("Dealer lost the round!");
            System.out.println("Score " + playerScore + ":" + dealerScore + " in your favor.");
        } else {
            System.out.println("It's a draw!");
            System.out.println("Score remains " + playerScore + ":" + dealerScore);
        }
        System.out.println("Do you want to stop playing? Enter 1 if yes, 0 if no");
    }

    /**
     * Prints error message.
     * @param error the error to display
     */
    public static void printError(GameController.Errors error) {
        System.out.println("Error: " + error.getMessage());
    }
}