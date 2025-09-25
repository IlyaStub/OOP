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
     * Class representing game scores for player and dealer.
     * Used to track scores across multiple rounds.
     */
    protected static class Scores {
        private int playerScore;
        private int dealerScore;

        /**
         * Constructs Scores with initial values.
         *
         * @param playerScore initial player score
         * @param dealerScore initial dealer score
         */
        public Scores(int playerScore, int dealerScore) {
            this.playerScore = playerScore;
            this.dealerScore = dealerScore;
        }

        /**
         * Sets the player score.
         *
         * @param playerScore new player score value
         */
        public void setPlayerScore(int playerScore) {
            this.playerScore = playerScore;
        }

        /**
         * Sets the dealer score.
         *
         * @param dealerScore new dealer score value
         */
        public void setDealerScore(int dealerScore) {
            this.dealerScore = dealerScore;
        }

        /**
         * Gets the current player score.
         *
         * @return player score value
         */
        public int getPlayerScore() {
            return playerScore;
        }

        /**
         * Gets the current dealer score.
         *
         * @return dealer score value
         */
        public int getDealerScore() {
            return dealerScore;
        }
    }


    /**
     * Main method that starts the Blackjack game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Parser.printWelcome();
        int round = 1;
        Scores scores = new Scores(0, 0);
        Scanner scan = new Scanner(System.in);

        do {
            playGameRound(scan, round++, scores);
        } while (!stopGame(scan));
    }

    /**
     * Plays one complete round of Blackjack.
     *
     * @param scan Scanner for user input
     * @param round current round number
     * @param scores current player scores
     */
    private static void playGameRound(Scanner scan, int round, Scores scores) {
        Parser.printRound(round);

        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        dealInitialCards(deck, playerHand, dealerHand);
        Parser.printHands(playerHand, dealerHand);

        if (playPlayerTurn(scan, deck, playerHand, dealerHand)) {
            scores.setDealerScore(scores.getDealerScore() + 1);
            Parser.printLoser(Players.PLAYER, scores.getPlayerScore(), scores.getDealerScore());
            return;
        }

        if (playDealerTurn(deck, playerHand, dealerHand)) {
            scores.setPlayerScore(scores.getPlayerScore() + 1);
            Parser.printLoser(Players.DEALER, scores.getPlayerScore(), scores.getDealerScore());
            return;
        }

        determineRoundWinner(playerHand, dealerHand, scores);
    }

    private static boolean isLoser(Hand hand) {
        return hand.getSumPoints() > hand.getMaxPoint();
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
        if (isLoser(dealerHand)) {
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

    private static void determineRoundWinner(Hand playerHand, Hand dealerHand,
                                              Scores scores) {
        int playerPoints = playerHand.getSumPoints();
        int dealerPoints = dealerHand.getSumPoints();

        if (playerPoints > dealerPoints) {
            scores.setPlayerScore(scores.getPlayerScore() + 1);
            Parser.printLoser(Players.DEALER, scores.getPlayerScore(), scores.getDealerScore());
        } else if (dealerPoints > playerPoints) {
            scores.setDealerScore(scores.getDealerScore() + 1);
            Parser.printLoser(Players.PLAYER, scores.getPlayerScore(), scores.getDealerScore());
        } else {
            Parser.printLoser(null, scores.getPlayerScore(), scores.getDealerScore());
        }
    }

    /**
     * Public wrapper for testing isLoser logic.
     */
    protected static boolean testIsLoser(Hand hand) {
        return isLoser(hand);
    }

    /**
     * Public wrapper for testing stopGame logic.
     */
    protected static boolean testStopGame(String input) {
        Scanner scan = new Scanner(input);
        return stopGame(scan);
    }

    /**
     * Public wrapper for testing dealInitialCards logic.
     */
    protected static void testDealInitialCards(Deck deck, Hand playerHand, Hand dealerHand) {
        dealInitialCards(deck, playerHand, dealerHand);
    }

    /**
     * Public wrapper for testing playPlayerTurn logic.
     */
    protected static boolean testPlayPlayerTurn(String input, Deck deck,
                                             Hand playerHand, Hand dealerHand) {
        Scanner scan = new Scanner(input);
        return playPlayerTurn(scan, deck, playerHand, dealerHand);
    }

    /**
     * Public wrapper for testing playDealerTurn logic.
     */
    protected static boolean testPlayDealerTurn(Deck deck, Hand playerHand, Hand dealerHand) {
        return playDealerTurn(deck, playerHand, dealerHand);
    }

    /**
     * Public wrapper for testing determineRoundWinner logic.
     */
    protected static void testDetermineRoundWinner(Hand playerHand, Hand dealerHand,
                                                   Scores scores) {
        determineRoundWinner(playerHand, dealerHand, scores);
    }

    /**
     * Public wrapper for testing playGameRound logic.
     */
    protected static void testPlayGameRound(String input, int round,
                                          Scores scores) {
        Scanner scan = new Scanner(input);
        playGameRound(scan, round, scores);
    }
}