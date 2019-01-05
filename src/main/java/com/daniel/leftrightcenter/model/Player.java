package com.daniel.leftrightcenter.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int id;
    private List<Currency> hand;

    public Player(int id){
        this.hand = new ArrayList<>();
        this.id = id;
    }

    public void addCurrencyToHand(Currency currency){
        this.hand.add(currency);
    }

    public Currency removeCurrencyFromHand(){
        if (!this.hand.isEmpty()){
            return this.hand.remove(0);
        }
        return null;
    }

    public List<Currency> getCurrentHand(){
        return this.hand;
    }

    public int getHandSize(){
        return this.hand.size();
    }

    public boolean hasRemainingCurrency(){
        return !this.hand.isEmpty();
    }

    public int getId() {
        return id;
    }

    public int getHandAmount(){
        return this.hand.stream().mapToInt(Currency::getAmount).sum();
    }
}
