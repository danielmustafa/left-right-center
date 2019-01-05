package com.daniel.leftrightcenter.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Die die;
    private int numTurns = 0;
    private int currencyValue;
    private List<Player> players;
    private List<Currency> centerPot;

    public Game(int numPlayers, int value){
        this.die = new Die();
        numTurns = 0;
        this.players = new ArrayList<>();
        this.currencyValue = value;
        this.centerPot = new ArrayList<>();

        addPlayers(numPlayers);
        announceNewGame();
        executeGame();
    }

    private void addPlayers(int numPlayers){
        for (int i = 1; i <= numPlayers; i++) {
            addPlayer(i);
        }
    }

    private void addPlayer(int id){
        Player player = createPlayer(id);

        for (int i = 0; i < 3; i++) {
            player.addCurrencyToHand(new Currency(this.currencyValue));
        }

        this.players.add(player);
    }

    private Player createPlayer(int id){
        return new Player(id);
    }

    private void announceNewGame(){
        outputToConsole(String.format("Starting new game with %s players, currency value of %s", players.size(), currencyValue));
    }

    private String outputTurn(Player player, List<Die.Face> roll){
        return String.format("Player %s rolled: %s,  Hand size: %s", player.getId(), roll, player.getHandSize());
    }

    private String outputPlayerSkipped(Player player){
        return String.format("Player %s skipped cause they got no money", player.getId());
    }

    private String outputPlayerWins(Player player, int totalPrize) {
        StringBuffer sb = new StringBuffer();
        sb.append(addLineBreak());
        sb.append(String.format("Player %s wins!", player.getId()));
        sb.append(addNewLine());
        sb.append(String.format("Total prize: $%s", totalPrize));
        sb.append(addNewLine());
        sb.append(String.format("Number of turns: %s", numTurns));
        sb.append(addNewLine());
        sb.append(addLineBreak());
        return sb.toString();
    }

    private void outputToConsole(String message){
        System.out.println(message);
    }

    private void executeGame(){
        StringBuffer sb = new StringBuffer();
        while (!onlyOnePlayerWithCurrencyRemaining()){
            numTurns++;
            sb.append(addNewLine());
            sb.append(String.format("Round %s:", numTurns));
            sb.append(addNewLine());
            for (Player player : this.players){
                if (player.hasRemainingCurrency()){
                    int diceRoll = player.getHandSize() < 3 ? player.getHandSize() : 3;
                    List<Die.Face> rolls = die.roll(diceRoll);
                    sb.append(outputTurn(player, rolls));
                    sb.append(addNewLine());
                    evaluateTurn(player, rolls);
                } else {
                    sb.append(outputPlayerSkipped(player));
                    sb.append(addNewLine());
                }

                if (onlyOnePlayerWithCurrencyRemaining()){
                    break;
                }
            }
            sb.append(String.format("Center pot: $%s", getCenterPotAmount()));
            sb.append(addNewLine());
            outputToConsole(sb.toString());
            sb.delete(0,sb.length());
        }

        Player player = getWinner();
        int totalPrize = getPrizeAmount(player);
        outputToConsole(outputPlayerWins(player, totalPrize));
    }

    private int getPrizeAmount(Player player) {
        return player.getHandAmount() + getCenterPotAmount();
    }

    private Player getWinner() {
        return players.stream().filter(p -> p.hasRemainingCurrency()).findFirst().get();
    }

    private boolean onlyOnePlayerWithCurrencyRemaining() {
        return players.stream().filter(Player::hasRemainingCurrency).count() == 1;
    }

    private void evaluateTurn(Player player, List<Die.Face> roll){
        for (Die.Face face : roll){
            switch (face){
                case L: passCurrencyLeft(player); break;
                case R: passCurrencyRight(player); break;
                case C: passCurrencyCenter(player); break;
                default: break;
            }
        }
    }

    private void passCurrencyLeft(Player player){
        Currency currency = player.removeCurrencyFromHand();
        if (players.indexOf(player) == 0){
            players.get(players.size() - 1).addCurrencyToHand(currency);
        } else {
            players.get(players.indexOf(player) - 1).addCurrencyToHand(currency);
        }
    }

    private void passCurrencyRight(Player player){
        Currency currency = player.removeCurrencyFromHand();
        if (players.indexOf(player) == players.size() - 1){
            players.get(0).addCurrencyToHand(currency);
        } else {
            players.get(players.indexOf(player) + 1).addCurrencyToHand(currency);
        }
    }

    private int getCenterPotAmount(){
        return this.centerPot.stream().mapToInt(Currency::getAmount).sum();
    }

    private void passCurrencyCenter(Player player){
        centerPot.add(player.removeCurrencyFromHand());
    }

    private String addNewLine(){
        return "\n";
    }

    private String addLineBreak(){
        return "*************\n";
    }

}
