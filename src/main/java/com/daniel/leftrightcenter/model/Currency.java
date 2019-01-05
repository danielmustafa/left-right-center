package com.daniel.leftrightcenter.model;

public class Currency {

    private Value value;

    public enum Value {
        ONE, FIVE, TEN, TWENTY, FIFTY, HUNDRED
    }

    public Currency(Value value) {
        this.value = value;
    }

    public Currency(int value){
        switch (value){
            case 1:     this.value = Value.ONE; break;
            case 5:     this.value = Value.FIVE; break;
            case 10:    this.value = Value.TEN; break;
            case 20:    this.value = Value.TWENTY; break;
            case 50:    this.value = Value.FIFTY; break;
            case 100:   this.value = Value.HUNDRED; break;
        }
    }

    public int getAmount(){
        switch (this.value){
            case ONE:       return 1;
            case FIVE:      return 5;
            case TEN:       return 10;
            case TWENTY:    return 20;
            case FIFTY:     return 50;
            case HUNDRED:   return 100;
            default: break;
        }
        return 0;
    }
}
