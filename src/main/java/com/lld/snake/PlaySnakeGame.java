package com.lld.snake;

import java.util.Arrays;
import java.util.Scanner;

public class PlaySnakeGame {
    public static void main(String[] args) {
        Game game = new Game(40, 10, Arrays.asList(new int[][]{{0,2}, {0,1}, {0, 0}}), 2);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("Choose next Move:");
            String line = scanner.nextLine().trim();
            if(line.isEmpty()) {
                continue;
            }
            char move = Character.toUpperCase(line.charAt(0));
            game.makeMovement(move);
            System.out.printf("\n");
        }
    }
}
