package ru.nsu.g.stubarev.blackjack;

import java.util.ArrayList;

public class Person {
    private final ArrayList<Card> hand;

    public Person() {
        this.hand = new ArrayList<>(2);
    }

    public ArrayList<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public void addCardToHand(){
        hand.add(new Card());
    }

    public int getSumPoints(){
        int summ = 0;
        for (Card card : hand){
            summ += card.getPoints();
        }
        return summ;
    }

    public void printHand(){
        System.out.print("[");
        for (Card card : hand){
            System.out.print(card.getSymbol() + ", ");
        }
        System.out.print("] => " + Integer.toString(getSumPoints()));
    }
}
