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
        for (int i = 0; i < 13; i++) {
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
    void testStopGame_InvalidInput_ReturnsTrue() {
        assertTrue(GameController.testStopGame("2\n1\n"));
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
        assertTrue(dealerHand.getHand().get(1).isHidden());
    }

    @Test
    void testPlayPlayerTurn_InvalidInputFollowedByTakeCard() {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        boolean result = GameController.testPlayPlayerTurn("5\n1\n0",
                deck, playerHand, dealerHand);

        assertFalse(result);
        assertEquals(1, playerHand.getHand().size());
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input. Please enter 0 or 1."));
    }


    @Test
    void testPlayPlayerTurn_PlayerStops_ReturnsFalse() {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        boolean result = GameController.testPlayPlayerTurn("0", deck, playerHand, dealerHand);

        assertFalse(result);
        assertEquals(0, playerHand.getHand().size());
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
        GameController.Scores scores = new GameController.Scores(0, 0);

        playerHand.addCardToHand(new Deck());

        GameController.testDetermineRoundWinner(playerHand, dealerHand, scores);

        assertEquals(1, scores.getPlayerScore());
        assertEquals(0, scores.getDealerScore());
    }

    @Test
    void testDetermineRoundWinner_DealerWins() {
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();
        GameController.Scores scores = new GameController.Scores(0, 0);

        dealerHand.addCardToHand(new Deck());

        GameController.testDetermineRoundWinner(playerHand, dealerHand, scores);

        assertEquals(0, scores.getPlayerScore());
        assertEquals(1, scores.getDealerScore());
    }

    @Test
    void testDetermineRoundWinner_Tie() {
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();
        GameController.Scores scores = new GameController.Scores(2, 3);

        GameController.testDetermineRoundWinner(playerHand, dealerHand, scores);

        assertEquals(2, scores.getPlayerScore());
        assertEquals(3, scores.getDealerScore());
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
    void testPlayDealerTurn_DealerHasMore17Points() {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        playerHand.addCardToHand(deck);
        Hand dealerHand = new Hand();
        dealerHand.addCardToHand(deck);

        int initialPoints = dealerHand.getSumPoints();
        GameController.testPlayDealerTurn(deck, playerHand, dealerHand);

        assertTrue(dealerHand.getSumPoints() >= 17);
        assertTrue(dealerHand.getSumPoints() > initialPoints);
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

    @Test
    void testErrorsEnum_GetMessage() {
        assertEquals(1, GameController.Errors.values().length);
        assertEquals("Invalid input. Please enter 0 or 1.",
                GameController.Errors.WRONG_INPUT.getMessage());
    }

    @Test
    void testPlayersEnum_Values() {
        assertEquals(2, GameController.Players.values().length);
        assertEquals("PLAYER", GameController.Players.PLAYER.name());
        assertEquals("DEALER", GameController.Players.DEALER.name());
    }

    @Test
    void testPlayGameRound_PlayerBusts() {
        String input = "1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n0";
        GameController.Scores scores = new GameController.Scores(0, 0);

        GameController.testPlayGameRound(input, 1, scores);

        assertEquals(0, scores.getPlayerScore());
        assertEquals(1, scores.getDealerScore());
    }

    @Test
    void testPlayGameRound_InvalidInputThenValid() {
        String input = "5\n2\n0\n0";
        GameController.Scores scores = new GameController.Scores(0, 0);

        GameController.testPlayGameRound(input, 1, scores);

        assertTrue(scores.getPlayerScore() >= 0);
        assertTrue(scores.getDealerScore() >= 0);
    }

    @Test
    void testPlayGameRound_PlayerTakesOneCardAndStops() {
        String input = "1\n0\n0";
        GameController.Scores scores = new GameController.Scores(0, 0);

        GameController.testPlayGameRound(input, 1, scores);

        assertTrue(scores.getPlayerScore() >= 0);
        assertTrue(scores.getDealerScore() >= 0);
    }
}