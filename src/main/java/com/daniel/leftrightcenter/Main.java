package com.daniel.leftrightcenter;

import com.daniel.leftrightcenter.model.Game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of players:");
        int numPlayers = scanner.nextInt();
        System.out.println("Enter currency value: [1, 5, 10, 20, 50, 100]");
        int currencyValue = scanner.nextInt();
        Game game = new Game(numPlayers, currencyValue);

    }
}
