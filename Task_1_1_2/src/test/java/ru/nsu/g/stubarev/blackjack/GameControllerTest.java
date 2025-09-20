package ru.nsu.g.stubarev.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {

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
    void testIsLoser_WhenHandOver21_ReturnsTrue() {
        Hand hand = new Hand();

        Deck deck = new Deck();
        for (int i = 0; i < 10; i++) {
            hand.addCardToHand(deck);
        }

        assertTrue(GameController.testIsLoser(hand));
    }

    @Test
    void testIsLoser_WhenHand21OrLess_ReturnsFalse() {
        Hand hand = new Hand();
        hand.addCardToHand(new Deck());

        assertFalse(GameController.testIsLoser(hand));
    }

    @Test
    void testStopGame_Input1_ReturnsTrue() {
        assertTrue(GameController.testStopGame("1"));
    }

    @Test
    void testStopGame_Input0_ReturnsFalse() {
        assertFalse(GameController.testStopGame("0"));
    }

    @Test
    void testStopGame_InvalidInput_ReturnsFalse() {
        assertFalse(GameController.testStopGame("2\n0\n"));
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input. Please enter 0 or 1."));
    }

    @Test
    void testPlayPlayerTurn_InvalidInputThenValidInput_ContinuesCorrectly() {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        boolean result = GameController.testPlayPlayerTurn("2\n0", deck,
                playerHand, dealerHand);

        assertFalse(result);
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input. Please enter 0 or 1."));
    }

    @Test
    void testPlayPlayerTurn_PlayerTakesOneCardAndStops_ReturnsFalse() {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        boolean result = GameController.testPlayPlayerTurn("1\n0", deck, playerHand, dealerHand);

        assertFalse(result);
        assertEquals(1, playerHand.getHand().size());
    }

    @Test
    void testDealInitialCards_DealsCorrectNumberOfCards() {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        GameController.testDealInitialCards(deck, playerHand, dealerHand);

        assertEquals(2, playerHand.getHand().size());
        assertEquals(2, dealerHand.getHand().size());
    }

    @Test
    void testPlayPlayerTurn_PlayerStops_ReturnsFalse() {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        boolean result = GameController.testPlayPlayerTurn("0", deck, playerHand, dealerHand);

        assertFalse(result);
    }

    @Test
    void testPlayPlayerTurn_PlayerTakesCardAndLoses_ReturnsTrue() {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        boolean result =
                GameController.testPlayPlayerTurn("1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n",
                        deck, playerHand, dealerHand);

        assertTrue(result);
    }

    @Test
    void testDetermineRoundWinner_PlayerWins() {
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        playerHand.addCardToHand(new Deck());

        int[] result = GameController.testDetermineRoundWinner(playerHand, dealerHand, 0, 0);

        assertEquals(1, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    void testPlayDealerTurn_DealerBusts_ReturnsTrue() {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        for (int i = 0; i < 10; i++) {
            dealerHand.addCardToHand(deck);
        }

        boolean result = GameController.testPlayDealerTurn(deck, playerHand, dealerHand);

        assertTrue(result);
    }

    @Test
    void testPlayDealerTurn_DealerRevealsHiddenCard() {
        Deck deck = new Deck();

        Hand playerHand = new Hand();
        playerHand.addCardToHand(deck);
        Hand dealerHand = new Hand();
        dealerHand.addCardToHand(deck);
        dealerHand.addCardToHand(deck, true);

        assertTrue(dealerHand.getHand().get(1).isHidden());
        GameController.testPlayDealerTurn(deck, playerHand, dealerHand);

        for (Card card : dealerHand.getHand()) {
            assertFalse(card.isHidden());
        }
    }

}