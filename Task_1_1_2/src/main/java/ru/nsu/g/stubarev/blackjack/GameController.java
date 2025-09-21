package ru.nsu.g.stubarev.blackjack;

import java.util.Scanner;

/**
 * Main game controller class that manages the Blackjack game flow.
 * Handles player and dealer turns, scoring, and game logic.
 */
public class GameController {
    /**
     * Enum representing possible error messages in the game.
     */
    public enum Errors {
        /** Invalid input error. */
        WRONG_INPUT("Invalid input. Please enter 0 or 1.");

        private final String message;

        /**
         * Constructs an error with message.
         *
         * @param message the error message
         */
        Errors(String message) {
            this.message = message;
        }

        /**
         * Gets the error message.
         *
         * @return the error message string
         */
        public String getMessage() {
            return message;
        }
    }

    /**
     * Enum representing the players in the game.
     */
    public enum Players {
        /** Player participant. */
        PLAYER,
        /** Dealer participant. */
        DEALER
    }

    /**
     * Main method that starts the Blackjack game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Parser.printWelcome();
        int round = 1;
        int playerScore = 0;
        int dealerScore = 0;
        Scanner scan = new Scanner(System.in);

        outer_loop:
        while (true) {
            Parser.printRound(round++);

            Deck deck = new Deck();
            Hand playerHand = new Hand();
            Hand dealerHand = new Hand();

            dealInitialCards(deck, playerHand, dealerHand);
            Parser.printHands(playerHand, dealerHand);

            if (playPlayerTurn(scan, deck, playerHand, dealerHand)) {
                dealerScore++;
                Parser.printLoser(Players.PLAYER, playerScore, dealerScore);
                if (stopGame(scan)) {
                    break;
                }
                continue;
            }

            if (playDealerTurn(deck, playerHand, dealerHand)) {
                playerScore++;
                Parser.printLoser(Players.DEALER, playerScore, dealerScore);
                if (stopGame(scan)) {
                    break;
                }
                continue;
            }

            int[] scores = determineRoundWinner(playerHand, dealerHand, playerScore, dealerScore);
            playerScore = scores[0];
            dealerScore = scores[1];

            if (stopGame(scan)) {
                break;
            }
        }
    }

    private static boolean isLoser(Hand hand) {
        return hand.getSumPoints() > 21;
    }

    private static boolean stopGame(Scanner scan) {
        while (true) {
            if (scan.hasNextInt()) {
                int input = scan.nextInt();
                switch (input) {
                    case 0:
                        return false;
                    case 1:
                        return true;
                    default:
                        Parser.printError(Errors.WRONG_INPUT);
                }
            } else {
                Parser.printError(Errors.WRONG_INPUT);
                scan.next();
            }
        }
    }

    private static void dealInitialCards(Deck deck, Hand playerHand, Hand dealerHand) {
        playerHand.addCardToHand(deck);
        playerHand.addCardToHand(deck);
        dealerHand.addCardToHand(deck);
        dealerHand.addCardToHand(deck, true);
    }

    private static boolean playPlayerTurn(Scanner scan, Deck deck,
                                          Hand playerHand, Hand dealerHand) {

        boolean playersTurn = true;
        while (playersTurn) {
            Parser.printTurn(Players.PLAYER);
            if (scan.hasNextInt()) {
                int takeIt = scan.nextInt();
                switch (takeIt) {
                    case 0:
                        playersTurn = false;
                        break;
                    case 1:
                        playerHand.addCardToHand(deck);
                        Parser.printOpenCard(playerHand.getLastCard(), Players.PLAYER);
                        Parser.printHands(playerHand, dealerHand);

                        if (isLoser(playerHand)) {
                            return true;
                        }
                        break;
                    default:
                        Parser.printError(Errors.WRONG_INPUT);
                }
            } else {
                Parser.printError(Errors.WRONG_INPUT);
                scan.next();
            }
        }
        return false;
    }

    private static boolean playDealerTurn(Deck deck, Hand playerHand, Hand dealerHand) {
        if (dealerHand.getSumPoints() > 21) {
            return true;
        }
        Parser.printTurn(Players.DEALER);

        dealerHand.revealHiddenCard();
        Parser.printOpenHiddenCard(dealerHand.getLastCard());

        Parser.printHands(playerHand, dealerHand);

        while (dealerHand.getSumPoints() < 17) {
            dealerHand.addCardToHand(deck);
            Card newCard = dealerHand.getLastCard();
            Parser.printOpenCard(newCard, Players.DEALER);
            Parser.printHands(playerHand, dealerHand);

            if (isLoser(dealerHand)) {
                return true;
            }
        }
        return false;
    }

    private static int[] determineRoundWinner(Hand playerHand, Hand dealerHand,
                                              int playerScore, int dealerScore) {
        int playerPoints = playerHand.getSumPoints();
        int dealerPoints = dealerHand.getSumPoints();

        if (playerPoints > dealerPoints) {
            playerScore++;
            Parser.printLoser(Players.DEALER, playerScore, dealerScore);
        } else if (dealerPoints > playerPoints) {
            dealerScore++;
            Parser.printLoser(Players.PLAYER, playerScore, dealerScore);
        } else {
            Parser.printLoser(null, playerScore, dealerScore);
        }
        return new int[]{playerScore, dealerScore};
    }

    /**
     * Public wrapper for testing isLoser logic.
     */
    public static boolean testIsLoser(Hand hand) {
        return isLoser(hand);
    }

    /**
     * Public wrapper for testing stopGame logic.
     */
    public static boolean testStopGame(String input) {
        Scanner scan = new Scanner(input);
        return stopGame(scan);
    }

    /**
     * Public wrapper for testing dealInitialCards logic.
     */
    public static void testDealInitialCards(Deck deck, Hand playerHand, Hand dealerHand) {
        dealInitialCards(deck, playerHand, dealerHand);
    }

    /**
     * Public wrapper for testing playPlayerTurn logic.
     */
    public static boolean testPlayPlayerTurn(String input, Deck deck,
                                             Hand playerHand, Hand dealerHand) {
        Scanner scan = new Scanner(input);
        return playPlayerTurn(scan, deck, playerHand, dealerHand);
    }

    /**
     * Public wrapper for testing playDealerTurn logic.
     */
    public static boolean testPlayDealerTurn(Deck deck, Hand playerHand, Hand dealerHand) {
        return playDealerTurn(deck, playerHand, dealerHand);
    }

    /**
     * Public wrapper for testing determineRoundWinner logic.
     */
    public static int[] testDetermineRoundWinner(Hand playerHand, Hand dealerHand,
                                                 int playerScore, int dealerScore) {
        return determineRoundWinner(playerHand, dealerHand, playerScore, dealerScore);
    }
}