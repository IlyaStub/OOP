package ru.nsu.g.stubarev.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ParserTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void printWelcome() {
        Parser.printWelcome();
        String expected = "Welcome to Blackjack!" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printRound() {
        Parser.printRound(3);
        String expected = "Round 3" + System.lineSeparator() +
                "Dealer dealt the cards" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printTurn_Player() {
        Parser.printTurn(GameController.Players.PLAYER);
        String expected = "Your turn" + System.lineSeparator() +
                "-------" + System.lineSeparator() +
                "Enter '1' to take a card, and '0' to stop" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printTurn_Dealer() {
        Parser.printTurn(GameController.Players.DEALER);
        String expected = "Dealer's turn" + System.lineSeparator() +
                "-------" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printOpenCard_Player() {
        Card card = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Parser.printOpenCard(card, GameController.Players.PLAYER);
        String expected = "You revealed card " + card.toString() + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printOpenCard_Dealer() {
        Card card = new Card(Card.Rank.KING, Card.Suit.DIAMONDS);
        Parser.printOpenCard(card, GameController.Players.DEALER);
        String expected = "Dealer reveals card " + card.toString() + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printOpenHiddenCard() {
        Card card = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS);
        Parser.printOpenHiddenCard(card);
        String expected = "Dealer reveals hidden card " + card.toString() + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printLoser_PlayerLost() {
        Parser.printLoser(GameController.Players.PLAYER, 2, 5);
        String expected = "You lost the round!" + System.lineSeparator() +
                "Score 2:5 in dealer's favor." + System.lineSeparator() +
                "Do you want to stop playing? Enter 1 if yes, 0 if no" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printLoser_DealerLost() {
        Parser.printLoser(GameController.Players.DEALER, 7, 3);
        String expected = "Dealer lost the round!" + System.lineSeparator() +
                "Score 7:3 in your favor." + System.lineSeparator() +
                "Do you want to stop playing? Enter 1 if yes, 0 if no" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printLoser_Draw() {
        Parser.printLoser(null, 4, 4);
        String expected = "It's a draw!" + System.lineSeparator() +
                "Score remains 4:4" + System.lineSeparator() +
                "Do you want to stop playing? Enter 1 if yes, 0 if no" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    @Test
    void printError() {
        Parser.printError(GameController.Errors.WRONG_INPUT);
        String expected = "Error: " + GameController.Errors.WRONG_INPUT.getMessage() + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }
}